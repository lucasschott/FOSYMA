package eu.su.mas.dedaleEtu.mas.utils;

import java.io.Serializable;

import eu.su.mas.dedale.env.Observation;
import jade.core.AID;

public class Agent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6079740120170476972L;
	
	AID id;
	Integer capacity;
	Integer lockPick;
	Integer strength;
	Observation treasureType;
	
	public Agent() {
	}
	
	public void setAID(AID id) {
		this.id = id;
	}
	
	public AID getAID() {
		return this.id;
	}
	
	public void setBackPackCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	public Integer getBackPackCapacity() {
		return this.capacity;
	}
	
	public void setLockPick(Integer lockPick) {
		this.lockPick = lockPick;
	}
	
	public Integer getLockPick() {
		return this.lockPick;
	}
	
	public void setStrength(Integer strength) {
		this.strength = strength;
	}
	
	public Integer getStrength() {
		return this.strength;
	}
	
	public boolean equals(Agent other)
	{
		return this.id.getLocalName().toString().equals(other.id.getLocalName().toString());
	}
	
	public void setTreasureType(Observation type)
	{
		this.treasureType = type;
	}
	
	public Observation getTreasureType()
	{
		return this.treasureType;
	}
}
