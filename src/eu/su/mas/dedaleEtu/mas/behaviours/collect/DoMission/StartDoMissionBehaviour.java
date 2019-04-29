package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import java.util.ArrayList;
import java.util.Collections;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import jade.core.behaviours.OneShotBehaviour;

public class StartDoMissionBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3892217000563456294L;
	private CollectMultiAgent _myAgent;

	public StartDoMissionBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		this._myAgent.clearTreasureMap();
	}
	
	@Override
	public void action() 
	{
		String position = this._myAgent.getCurrentPosition();
		String destination = this._myAgent.getCurrentMission().getDestination();
		ArrayList<String> shortestPath = (ArrayList<String>) this._myAgent.map.getMap().getShortestPath(position, destination);
		
		this._myAgent.setDestinationId(destination);
		this._myAgent.setPath(shortestPath);
	}

}
