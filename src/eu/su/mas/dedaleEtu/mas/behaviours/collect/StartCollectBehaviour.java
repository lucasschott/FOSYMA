package eu.su.mas.dedaleEtu.mas.behaviours.collect;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class StartCollectBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4094788259802184113L;
	
	AbstractMultiAgent _myAgent;
	
	public StartCollectBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this._myAgent.registerService("COLLECT-FREE");
	}

	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
