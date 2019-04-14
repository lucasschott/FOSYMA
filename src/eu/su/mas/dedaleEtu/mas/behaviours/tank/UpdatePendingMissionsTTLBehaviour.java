package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import jade.core.behaviours.OneShotBehaviour;

public class UpdatePendingMissionsTTLBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 705399278217237335L;
	TankMultiAgent _myAgent;
	
	public UpdatePendingMissionsTTLBehaviour(TankMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() {
		this._myAgent.updatePendingMissionsTTL();
	}
	

}
