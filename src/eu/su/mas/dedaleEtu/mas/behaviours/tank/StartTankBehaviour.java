package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class StartTankBehaviour extends OneShotBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4094788259802184113L;
	
	AbstractMultiAgent _myAgent;
	
	public StartTankBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this._myAgent.registerService("TANK");
	}

	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
