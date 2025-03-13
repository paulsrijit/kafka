package com.example.demo.kstream.model;

public class Policy {
	private String policyId;
	private String agentId;
	private String policyType;
	private Double premiumAmount;

	public Policy(String policyId, String agentId, String policyType, Double premiumAmount) {
		super();
		this.policyId = policyId;
		this.agentId = agentId;
		this.policyType = policyType;
		this.premiumAmount = premiumAmount;
	}

	@Override
	public String toString() {
		return "Policy []";
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public Double getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}
}
