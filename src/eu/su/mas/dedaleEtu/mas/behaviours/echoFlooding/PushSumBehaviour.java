package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PushSumBehaviour extends TreeOperations{

	public enum Mode {
		TREE_SIZE, CAPACITY, LOCK_PICK
	}
	
	enum State {
		IDLE, WAIT_PARENT, WAIT_CHILDREN, REQUEST_CHILDREN, SEND_PARENT
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
		return (this.value + 1) >= this.treshold;
	}

	@Override
	protected Serializable sendParentStrategy() {
		return Integer.valueOf(this.value + 1);
	}

	@Override
	protected Serializable sendChildStrategy() {
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
	}

	@Override
	protected void resetStrategy() {
		this.value = 0;
	}
}
