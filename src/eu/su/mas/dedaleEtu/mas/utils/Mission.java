package eu.su.mas.dedaleEtu.mas.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Mission implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4299906189626924043L;
	
	private Agent leader;
	private boolean pending;
	private String uuid;
	private HashMap<String, Object> description;
	
	public Mission(Agent leader, HashMap<String, Object> description) {
		this.uuid = UUID.randomUUID().toString();
		this.description = description;
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
	
	public HashMap<String, Object> getDescription() {
		return this.description;
	}
	
	public boolean equals(Mission other) {
		return this.uuid.equals(other.getUUID());
	}
}
