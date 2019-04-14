package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.EndCollectBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.ReceiveMissionAssignementBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.SendMissionRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.StartCollectBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class CollectFSMBehaviour  extends FSMBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5790881203932328791L;

	public CollectFSMBehaviour(CollectMultiAgent myagent) {
		super(myagent);
	
		this.registerFirstState(new StartCollectBehaviour(myagent), "START-COLLECT");
		this.registerState(new SendMissionRequestBehaviour(myagent),"SEND-MISSION-REQUEST");
		this.registerState(new ReceiveMissionAssignementBehaviour(myagent), "RECEIVE-MISSION-ASSIGNEMENT");
		this.registerLastState(new EndCollectBehaviour(myagent), "END-COLLECT");
		
		//definition des transaction
		this.registerTransition("START-COLLECT", "SEND-MISSION-REQUEST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("SEND-MISSION-REQUEST", "RECEIVE-MISSION-ASSIGNEMENT", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-ASSIGNEMENT", "END-COLLECT", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-ASSIGNEMENT","SEND-MISSION-REQUEST", FSMCodes.Events.SUCESS.ordinal());
	}
}
