package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.broadcast.SendBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.interlocking.InterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.GoToBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.EndTankBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.ReceiveMissionAssignementACKBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.ReceiveMissionCompletionNotification;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.ReceiveMissionRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.ReceiveUpdateTankerKnowledgeBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.SendMissionAssignementBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.StartTankBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.UpdatePendingMissionsTTLBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class TankFSMBehaviour  extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8540175418322936323L;

	public TankFSMBehaviour(TankMultiAgent myagent) 
	{
		super(myagent);
		
		this.registerFirstState(new StartTankBehaviour(myagent), "START-TANK");
		this.registerState(new SendBroadcastBehaviour(myagent, "ALL_AGENTS", "TANK-PING"), "BROADCAST-TANK");
		this.registerState(new ReceiveMissionRequestBehaviour(myagent), "RECEIVE-MISSION-REQUEST");
		this.registerState(new SendMissionAssignementBehaviour(myagent), "SEND-MISSION-ASSIGNEMENT");
		this.registerState(new ReceiveMissionAssignementACKBehaviour(myagent), "RECEIVE-MISSION-ASSIGNEMENT-ACK");
		this.registerState(new ReceiveMissionCompletionNotification(myagent), "RECEIVE-MISSION-COMPLETION-NOTIFICATION");
		this.registerState(new UpdatePendingMissionsTTLBehaviour(myagent), "UPDATE-PENDING-MISSIONS-TTL");
		this.registerState(new ReceiveUpdateTankerKnowledgeBehaviour(myagent, "UPDATE-TANKER-KNOWLEDGE"), "RECEIVE-UPDATE-TANKER-KNOWLEDGE");
		this.registerState(new InterlockingFSMBehaviour(myagent), "INTERLOCKING");
		this.registerState(new GoToBehaviour(myagent), "GO-TO");
		this.registerLastState(new EndTankBehaviour(myagent), "END-TANK");
		
		this.registerTransition("START-TANK", "BROADCAST-TANK", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("BROADCAST-TANK", "RECEIVE-MISSION-REQUEST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-REQUEST", "SEND-MISSION-ASSIGNEMENT", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-REQUEST", "RECEIVE-MISSION-ASSIGNEMENT-ACK", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("SEND-MISSION-ASSIGNEMENT", "RECEIVE-MISSION-ASSIGNEMENT-ACK", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-ASSIGNEMENT-ACK", "RECEIVE-MISSION-COMPLETION-NOTIFICATION", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-MISSION-ASSIGNEMENT-ACK", "RECEIVE-MISSION-COMPLETION-NOTIFICATION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-COMPLETION-NOTIFICATION", "RECEIVE-UPDATE-TANKER-KNOWLEDGE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-COMPLETION-NOTIFICATION", "RECEIVE-UPDATE-TANKER-KNOWLEDGE", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-UPDATE-TANKER-KNOWLEDGE", "GO-TO", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GO-TO", "BROADCAST-TANK", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GO-TO", "INTERLOCKING", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("GO-TO", "BROADCAST-TANK", FSMCodes.Events.END.ordinal());
		this.registerTransition("INTERLOCKING", "BROADCAST-TANK", FSMCodes.Events.SUCESS.ordinal());
		
	}
}
