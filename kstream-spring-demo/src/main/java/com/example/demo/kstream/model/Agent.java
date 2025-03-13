package com.example.demo.kstream.model;

public class Agent {
	private String agentId;
	private String agentName;
	private String region;

	public Agent(String agentId, String agentName, String region) {
		super();
		this.agentId = agentId;
		this.agentName = agentName;
		this.region = region;
	}

	public Agent() {
		super();
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
