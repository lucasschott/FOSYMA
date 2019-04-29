package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class StartOpenChestBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4371321609361836986L;
	private CollectMultiAgent _myAgent;

	public StartOpenChestBehaviour(CollectMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
	}

	@Override
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
