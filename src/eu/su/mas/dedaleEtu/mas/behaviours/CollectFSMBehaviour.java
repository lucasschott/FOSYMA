package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.EndCollectBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.StartCollectBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission.DoMissionFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission.GetMissionFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.LookForTank.LookForTankFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.NotifyMissionCompletion.NotifyMissionCompletionFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.TransferSilo.TransferSiloFSMBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class CollectFSMBehaviour  extends FSMBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5790881203932328791L;

	public CollectFSMBehaviour(CollectMultiAgent myagent) {
		super(myagent);
	
		this.registerFirstState(new StartCollectBehaviour(myagent), "START-COLLECT");
		this.registerState(new LookForTankFSMBehaviour(myagent),"LOOK-FOR-TANK-START");
		this.registerState(new LookForTankFSMBehaviour(myagent),"LOOK-FOR-TANK-END");
		this.registerState(new GetMissionFSMBehaviour(myagent), "GET-MISSION");
		this.registerState(new DoMissionFSMBehaviour(myagent), "DO-MISSION");
		this.registerState(new TransferSiloFSMBehaviour(myagent), "TRANSFER-SILO");
		this.registerState(new NotifyMissionCompletionFSMBehaviour(myagent), "NOTIFY-MISSION-COMPLETION");
		this.registerLastState(new EndCollectBehaviour(myagent), "END-COLLECT");
		
		this.registerTransition("START-COLLECT", "LOOK-FOR-TANK-START", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("LOOK-FOR-TANK-START", "GET-MISSION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GET-MISSION", "DO-MISSION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GET-MISSION", "LOOK-FOR-TANK-START", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("DO-MISSION", "LOOK-FOR-TANK-END", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("LOOK-FOR-TANK-END", "TRANSFER-SILO", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("TRANSFER-SILO", "LOOK-FOR-TANK-END", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("TRANSFER-SILO", "NOTIFY-MISSION-COMPLETION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("NOTIFY-MISSION-COMPLETION", "LOOK-FOR-TANK-START",FSMCodes.Events.SUCESS.ordinal());
	}
	
	public int onEnd() {
		this.reset();
		this.resetChildren();
		return FSMCodes.Events.SUCESS.ordinal();
	}
	
}
