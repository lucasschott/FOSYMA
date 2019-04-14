package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.EndTankBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.ReceiveMissionAssignementACKBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.ReceiveMissionRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.SendMissionAssignementBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.StartTankBehaviour;
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
		this.registerState(new ReceiveMissionRequestBehaviour(myagent), "RECEIVE-MISSION-REQUEST");
		this.registerState(new SendMissionAssignementBehaviour(myagent), "SEND-MISSION-ASSIGNEMENT");
		this.registerState(new ReceiveMissionAssignementACKBehaviour(myagent), "RECEIVE-MISSION-ASSIGNEMENT-ACK");
		this.registerLastState(new EndTankBehaviour(myagent), "END-TANK");
		
		this.registerTransition("START-TANK", "RECEIVE-MISSION-REQUEST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-REQUEST", "SEND-MISSION-ASSIGNEMENT", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-REQUEST", "RECEIVE-MISSION-ASSIGNEMENT-ACK", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("SEND-MISSION-ASSIGNEMENT", "RECEIVE-MISSION-ASSIGNEMENT-ACK", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MISSION-ASSIGNEMENT-ACK", "RECEIVE-MISSION-REQUEST", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-MISSION-ASSIGNEMENT-ACK", "END-TANK", FSMCodes.Events.SUCESS.ordinal());
	}
}
