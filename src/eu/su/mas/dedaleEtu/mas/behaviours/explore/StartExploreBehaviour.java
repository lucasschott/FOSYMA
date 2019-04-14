package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class StartExploreBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4094788259802184113L;
	
	AbstractMultiAgent _myAgent;
	
	public StartExploreBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this._myAgent.registerService("EXPLORE-FREE");
	}

	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
