package com.lic.mongoloader.config.consumer;

import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.mongoloader.model.MessageKey;

@Service
public class KafkaConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

	private final MongoTemplate mongoTemplate;
	private final ObjectMapper objectMapper;

	public KafkaConsumerService(MongoTemplate mongoTemplate, ObjectMapper objectMapper) {
		this.mongoTemplate = mongoTemplate;
		this.objectMapper = objectMapper;
	}

	@KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
            )
	public void consume(ConsumerRecord<String, String> record) {
		try {			

            String key = record.key();
            String value = record.value();
            
			logger.info("Received message with key: {} and value: {}", key, value);

			// Parse the message key to get collection name and primary key
			MessageKey messageKey = objectMapper.readValue(key, MessageKey.class);
			String collectionName = messageKey.getCollectionName();
			String primaryKey = messageKey.getPrimaryKey();

			// Parse the message value
			Document document = Document.parse(value);
			Object timestampObj = document.getLong("timestamp");

		
			Date messageTimestamp;
			if (timestampObj instanceof Long) {
				messageTimestamp = new Date((Long) timestampObj);
			} else {
				throw new IllegalArgumentException("Invalid timestamp format in message");
			}

			// Check if a record with this ID already exists
			Query query = new Query(Criteria.where("_id").is(primaryKey));
			Document existingRecord = mongoTemplate.findOne(query, Document.class, collectionName);

			if (existingRecord == null) {
				// No existing record, insert new one
				logger.info("Inserting new record into collection: {}", collectionName);
				document.put("_id", primaryKey);
				mongoTemplate.insert(document, collectionName);
			} else {
				// Compare timestamps
				if (messageTimestamp.after(new Date(existingRecord.getLong("timestamp")))) {
					// New message timestamp is older or equal, update the record
					logger.info("Updating existing record in collection: {}", collectionName);
					document.put("_id", primaryKey);
					mongoTemplate.save(document, collectionName);
				} else {
					// New message timestamp is newer, discard the message
					logger.info("Discarding message as it has newer timestamp than existing record");
				}
			}
		} catch (Exception e) {
			logger.error("Error processing message: {}", e.getMessage(), e);
		}
	}
}