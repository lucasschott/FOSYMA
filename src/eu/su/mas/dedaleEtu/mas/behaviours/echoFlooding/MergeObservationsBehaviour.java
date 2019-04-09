package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.PushSumBehaviour.Mode;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.PushSumBehaviour.State;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MergeObservationsBehaviour extends TreeOperations {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1785072674693056520L;
	
	public MergeObservationsBehaviour(AbstractMultiAgent myagent, String treeId) 
	{
		super(myagent, treeId);
	}
	
	@Override
	protected boolean endConditionStrategy() {
		return true;
	}

	@Override
	protected Serializable sendParentStrategy() {
		return null;
	}

	@Override
	protected Serializable sendChildStrategy() {
		return this._myAgent.getTreasureMap();
	}

	@Override
	protected void receiveParentStrategy(Serializable s) {
		this._myAgent.mergeTreasureMap((HashMap<String, Couple<Observation, Integer>>)(s));
	}

	@Override
	protected void receiveChildStrategy(Serializable s) {
		this.receiveParentStrategy(s);
	}

	@Override
	protected void resetStrategy() 
	{
	}

}
