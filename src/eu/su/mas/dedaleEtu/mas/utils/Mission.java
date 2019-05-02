package eu.su.mas.dedaleEtu.mas.utils;

import java.io.Serializable;
import java.util.UUID;

import eu.su.mas.dedale.env.Observation;

public class Mission implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4299906189626924043L;
	
	private Agent leader;
	private boolean pending;
	private boolean emptyObjective = false;
	private String uuid;
	private Integer TTL = 5;
	private String destination;
	private Observation type;
	private Integer quantity;
	
	public Mission(Agent leader, String destination, Observation type, Integer quantity) {
		this.uuid = UUID.randomUUID().toString();
		this.setDestination(destination);
		this.setType(type);
		this.setQuantity(quantity);
		this.leader = leader;
		this.pending = true;
	}
	
	public boolean getPending() {
		return this.pending;
	}
	
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	
	public String getUUID()
	{
		return this.uuid;
	}
	
	public Agent getLeader()
	{
		return this.leader;
	}
	
	public boolean equals(Mission other) {
		return this.uuid.equals(other.getUUID());
	}
	
	public Integer decreaseTTL()
	{
		this.TTL = this.TTL - 1;
		return this.TTL;
	}
	
	public boolean isExpired()
	{
		return this.TTL <= 0;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Observation getType() {
		return type;
	}

	public void setType(Observation type) {
		this.type = type;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isEmptyObjective() {
		return emptyObjective;
	}

	public void setEmptyObjective(boolean emptyObjective) {
		this.emptyObjective = emptyObjective;
	}
}
