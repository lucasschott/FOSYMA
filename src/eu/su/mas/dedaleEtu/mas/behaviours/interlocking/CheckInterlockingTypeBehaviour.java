package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class CheckInterlockingTypeBehaviour extends OneShotBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8020596227535247137L;
	private AbstractMultiAgent _myAgent;
	private boolean wumpus = false;
	
	public CheckInterlockingTypeBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action()
	{
		String node = getRequiredNode();
		List<Couple<Observation, Integer>> lobs = getNodeObservation(node);
		
		wumpus = false;
		
		if (node == null)
			return;
		
		for (Couple<Observation, Integer> obs: lobs)
		{
			if (obs.getLeft() == Observation.WIND)
				wumpus = true;
		}
		
	}
	
	public String getRequiredNode()
	{
		List<String> path = this._myAgent.map.getMap().getShortestPath(this._myAgent.getCurrentPosition(), this._myAgent.getDestinationId());
		if (path.size() >= 1)
			return path.get(0);
		return null;
	}
	
	public List<Couple<Observation, Integer>> getNodeObservation(String node) 
	{
		List<Couple<String, List<Couple<Observation, Integer>>>> lobs = this._myAgent.observe();
		
		for (Couple<String, List<Couple<Observation, Integer>>> obs: lobs)
		{
			if (obs.getLeft().equals(node))
				return obs.getRight();
		}
		
		return null;
	}
	
	public boolean isWumpusResponsible(List<Couple<Observation, Integer>> obs)
	{		
		return false;
	}

	public int onEnd() 
	{
		if (this.wumpus)
			return FSMCodes.Events.WUMPUS.ordinal();
		return FSMCodes.Events.CONFLICT.ordinal();
	}
}
