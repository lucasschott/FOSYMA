package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class OpenChestBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6929992818522892381L;
	private CollectMultiAgent _myAgent;
	private boolean opened = false;

	public OpenChestBehaviour(CollectMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		
		Observation observation = this.getCurrentObservation();
		
		if (observation != null)
			opened = this._myAgent.openLock(observation);
	}

	public Observation getCurrentObservation()
	{
		Couple<String,List<Couple<Observation,Integer>>> c_obs=((AbstractDedaleAgent)this.myAgent).observe().get(0);
		
		for (Couple<Observation, Integer> obs: c_obs.getRight())
		{
			if (obs.getLeft() == this._myAgent.getCurrentMission().getType())
				return obs.getLeft();
		}
		
		return null;
	}
	
	@Override
	public int onEnd() {
		if (this.opened)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
