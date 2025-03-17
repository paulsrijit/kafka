package com.lic.mongoloader.model;

public class MessageKey {
	private String collectionName;
	private String primaryKey;	
	

	public MessageKey(String collectionName, String primaryKey) {
		super();
		this.collectionName = collectionName;
		this.primaryKey = primaryKey;
	}

	public MessageKey() {
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
}