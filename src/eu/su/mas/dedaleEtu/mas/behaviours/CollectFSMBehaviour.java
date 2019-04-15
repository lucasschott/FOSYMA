package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.EndCollectBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.StartCollectBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission.GetMissionFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.LookForTank.LookForTankFSMBehaviour;
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
		this.registerState(new LookForTankFSMBehaviour(myagent),"LOOK-FOR-TANK");
		this.registerState(new GetMissionFSMBehaviour(myagent), "GET-MISSION");
		this.registerLastState(new EndCollectBehaviour(myagent), "END-COLLECT");
		
		this.registerTransition("START-COLLECT", "LOOK-FOR-TANK", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("LOOK-FOR-TANK", "GET-MISSION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GET-MISSION", "END-COLLECT", FSMCodes.Events.SUCESS.ordinal());
	}
}
