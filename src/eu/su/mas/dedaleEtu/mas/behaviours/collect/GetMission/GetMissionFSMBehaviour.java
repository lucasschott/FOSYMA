package eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.FSMBehaviour;

public class GetMissionFSMBehaviour extends FSMBehaviour {

	private static final long serialVersionUID = 2280666738092655685L;
	private CollectMultiAgent _myAgent;

	public GetMissionFSMBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		
		this.registerFirstState(new SendMissionRequestBehaviour(myagent), "REQUEST-MISSION");
		this.registerState(new ReceiveMissionAssignementBehaviour(myagent), "RECEIVE-MISSION");
		this.registerLastState(new EndGetMissionBehaviour(myagent), "END-GET-MISSION");
		
		this.registerTransition("REQUEST-MISSION", "RECEIVE-MISSION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION", "END-GET-MISSION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION", "REQUEST-MISSION", FSMCodes.Events.FAILURE.ordinal());
	}
	
	public int onEnd() {
		if (this._myAgent.getCurrentMission() != null)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
