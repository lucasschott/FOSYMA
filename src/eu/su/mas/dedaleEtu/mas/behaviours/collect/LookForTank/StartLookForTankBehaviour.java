package eu.su.mas.dedaleEtu.mas.behaviours.collect.LookForTank;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class StartLookForTankBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private CollectMultiAgent _myAgent;
	
	public StartLookForTankBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() 
	{
		this._myAgent.setTankPosition(this._myAgent.getCentroid());
		this._myAgent.setDestinationId(this._myAgent.getCentroid());
		this.clearMessageQueue();
	}
	
	public int onEnd() {
		System.out.println("START LOOK FOR TANK !");
		return FSMCodes.Events.SUCESS.ordinal();
	}
	
	public void clearMessageQueue() {
		while (this._myAgent.receive() != null) {}
	}

}
