package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import java.io.Serializable;
import java.util.HashMap;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;

public class MergeObservationsBehaviour extends BottomUpTreeOperations {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1785072674693056520L;
	
	public MergeObservationsBehaviour(AbstractMultiAgent myagent, String treeId) 
	{
		super(myagent, treeId);
		this.treeType = "MERGE-OBSERVATIONS";
	}
	
	@Override
	protected boolean endConditionStrategy() {
		return true;
	}

	@Override
	protected Serializable sendParentStrategy() {
		return this._myAgent.getTreasureMap();
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
		this.pending = this._myAgent.getTree(this.treeId).getChildCount();
	}

}
