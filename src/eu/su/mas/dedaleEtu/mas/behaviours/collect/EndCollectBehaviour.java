package eu.su.mas.dedaleEtu.mas.behaviours.collect;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndCollectBehaviour  extends OneShotBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4094788259802184113L;
	
	CollectMultiAgent _myAgent;
	
	public EndCollectBehaviour(CollectMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this._myAgent.deregisterService("COLLECT");
		System.out.println(this._myAgent.getLocalName() + " On mission : " + this._myAgent.getCurrentMission());
		System.out.println(this._myAgent.getLocalName() + " Going to : " + this._myAgent.getDestinationId());
	}

	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
