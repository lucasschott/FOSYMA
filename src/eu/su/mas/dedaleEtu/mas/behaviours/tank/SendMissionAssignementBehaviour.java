package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import jade.core.behaviours.OneShotBehaviour;

public class SendMissionAssignementBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2925644300003259511L;
	private TankMultiAgent _myAgent;

	public SendMissionAssignementBehaviour(TankMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		
	}
}
