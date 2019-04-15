package eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndGetMissionBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2352609891646885428L;

	public EndGetMissionBehaviour(CollectMultiAgent myagent) {
		super(myagent);
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
