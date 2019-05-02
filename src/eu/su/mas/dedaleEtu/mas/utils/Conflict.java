package eu.su.mas.dedaleEtu.mas.utils;

import java.io.Serializable;
import java.util.UUID;

public class Conflict implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5021886245281490215L;
	private String currentNode;
	private String conflictingNode;
	private String destinationNode;
	private int priority;
	private String uuid;
	
	public Conflict(String current, String conflict, String destination)
	{
		this.setCurrentNode(current);
		this.setConflictingNode(conflict);
		this.setDestinationNode(destination);
		this.setUid(UUID.randomUUID().toString());
	}

	public String getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	public String getConflictingNode() {
		return conflictingNode;
	}

	public void setConflictingNode(String conflictingNode) {
		this.conflictingNode = conflictingNode;
	}

	public String getDestinationNode() {
		return destinationNode;
	}

	public void setDestinationNode(String destinationNode) {
		this.destinationNode = destinationNode;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getUid() {
		return uuid;
	}

	public void setUid(String uuid) {
		this.uuid = uuid;
	}
}
