package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndTankBehaviour extends OneShotBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4094788259802184113L;
	
	TankMultiAgent _myAgent;
	
	public EndTankBehaviour(TankMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this._myAgent.deregisterService("TANK");
		System.out.println("AVAILABLE : " + this._myAgent.getAvailableAgents());
		System.out.println("PENDING : " + this._myAgent.getPendingMissions());
		System.out.println("ONGOING : " + this._myAgent.getOnGoingMissions());
	}

	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
