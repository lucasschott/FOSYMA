package eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndGetMissionBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2352609891646885428L;
	private CollectMultiAgent _myAgent;
	
	public EndGetMissionBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		_myAgent = myagent;
	}
	
	@Override
	public void action() {
		if (this._myAgent.getCurrentMission() != null)
		{
			System.out.println("ON MISSION : GOING TO : " + this._myAgent.getCurrentMission().getDestination());
			System.out.println("Currently on : " + this._myAgent.getCurrentPosition());	
		}
	}

	@Override
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
