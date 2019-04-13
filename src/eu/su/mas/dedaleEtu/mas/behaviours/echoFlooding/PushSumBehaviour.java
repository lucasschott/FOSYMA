package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import java.io.Serializable;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;

public class PushSumBehaviour extends TopDownTreeOperations{

	public enum Mode {
		TREE_SIZE, CAPACITY, LOCK_PICK
	}

	private static final long serialVersionUID = 7295849474602290463L;
	
	private int treshold;
	private int value = 0;
	
	public PushSumBehaviour(AbstractMultiAgent myagent, String treeId, int treshold) {
		super(myagent, treeId);
		
		this.treshold = treshold;
		this.value = 0;
		this.treeType = "PUSH-SUM";
	}

	@Override
	protected boolean endConditionStrategy() {
		System.out.println("Current value TOP DOWN : " + (this.value + 1));
		return (this.value + 1) >= this.treshold;
	}

	@Override
	protected Serializable sendParentStrategy() {
		return Integer.valueOf(this.value + 1);
	}

	@Override
	protected Serializable sendChildStrategy() {
		System.out.println("SEND CHILD STRATEGY");
		return null;
	}

	@Override
	protected void receiveParentStrategy(Serializable s) 
	{
	}

	@Override
	protected void receiveChildStrategy(Serializable s) 
	{
		Integer childValue = (Integer) s;
		
		this.value = this.value + childValue;
		
		System.out.println("RECEIVE : " + this._myAgent.getLocalName() + " (" + this._myAgent.getTree(this.treeId).getIsRoot() + ") count : " + this.value);
	}

	@Override
	protected void resetStrategy() {
		System.out.println("RESET STRATEGY");
		this.value = 0;
	}
}
