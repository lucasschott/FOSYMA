package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class PickUpObjectiveBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5247344913281722518L;
	private CollectMultiAgent _myAgent;

	public PickUpObjectiveBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		System.out.println("Current position : " + this._myAgent.getCurrentPosition());
		System.out.println("Current backpack : " + this._myAgent.getBackPackFreeSpace());
		
		System.out.println(this._myAgent.getLocalName() + " PICKED : " + this._myAgent.pick());
		System.out.println("EMPTY : " + this._myAgent.isCurrentPositionEmpty());
		
		this._myAgent.updateTreasureMap();
		this._myAgent.getCurrentMission().setEmptyObjective(this._myAgent.isCurrentPositionEmpty());
		
		if (this._myAgent.getCurrentMission().isEmptyObjective())
			this._myAgent.removeFromTreasureMap(this._myAgent.getCurrentMission().getDestination());
		
		System.out.println("Current treasure map : " + this._myAgent.getTreasureMap());
	}

	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
