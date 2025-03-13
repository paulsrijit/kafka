package com.example.demo.kstream.model;

public class PolicyMaster {
	private String agentId;
	private String agentName;
	private String region;
	private String policyId;
	private String policyType;
	private Double premiumAmount;

	public PolicyMaster(String agentId, String agentName, String region, String policyId, String policyType,
			Double premiumAmount) {
		super();
		this.agentId = agentId;
		this.agentName = agentName;
		this.region = region;
		this.policyId = policyId;
		this.policyType = policyType;
		this.premiumAmount = premiumAmount;
	}

	public PolicyMaster() {
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

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
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

	@Override
	public String toString() {
		return "PolicyMaster [agentId=" + agentId + ", agentName=" + agentName + ", region=" + region + ", policyId="
				+ policyId + ", policyType=" + policyType + ", premiumAmount=" + premiumAmount + "]";
	}

}
