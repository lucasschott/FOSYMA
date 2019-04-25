package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.FollowPathBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class DoMissionFSMBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3990378395122410840L;
	private CollectMultiAgent _myAgent;
	
	public DoMissionFSMBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		
		this.registerFirstState(new StartDoMissionBehaviour(this._myAgent), "START-MISSION");
		this.registerState(new FollowPathBehaviour(this._myAgent), "FOLLOW_PATH");
		this.registerState(new PickUpObjectiveBehaviour(this._myAgent), "PICK-UP");
		this.registerLastState(new EndDoMissionBehaviour(this._myAgent), "END-MISSION");
		
		this.registerTransition("START-MISSION", "FOLLOW_PATH", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("FOLLOW_PATH", "FOLLOW_PATH", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("FOLLOW_PATH", "PICK-UP", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("PICK-UP", "END-MISSION", FSMCodes.Events.SUCESS.ordinal());
	}
	
	public int onEnd() {
		this.resetChildren();
		this.reset();
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
