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
	}

	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
