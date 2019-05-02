package eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.InterlockingClient.ClientInterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.interlocking.InterlockingFSMBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class GetMissionFSMBehaviour extends FSMBehaviour {

	private static final long serialVersionUID = 2280666738092655685L;
	private CollectMultiAgent _myAgent;

	public GetMissionFSMBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		
		this.registerFirstState(new SendMissionRequestBehaviour(myagent), "REQUEST-MISSION");
		this.registerState(new ReceiveMissionAssignementBehaviour(myagent), "RECEIVE-MISSION");
		this.registerState(new CheckTimeOutBehaviour(myagent, 5), "CHECK-TIMEOUT");
		this.registerState(new ClientInterlockingFSMBehaviour(myagent), "CLIENT-INTERLOCKING");
		this.registerLastState(new EndGetMissionBehaviour(myagent), "END-GET-MISSION");
		
		this.registerTransition("CHECK-TIMEOUT", "CLIENT-INTERLOCKING", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("CHECK-TIMEOUT", "END-GET-MISSION", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("CLIENT-INTERLOCKING", "REQUEST-MISSION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("REQUEST-MISSION", "RECEIVE-MISSION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION", "END-GET-MISSION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION", "CHECK-TIMEOUT", FSMCodes.Events.FAILURE.ordinal());
	}
	
	public int onEnd() 
	{
		this.resetChildren();
		this.reset();
		
		System.out.println("END GET MISSION");
		if (this._myAgent.getCurrentMission() != null) {
			System.out.println("GOT MY MISSION");
			return FSMCodes.Events.SUCESS.ordinal();
		}
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
