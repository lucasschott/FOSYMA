package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.InterlockingClient.ClientInterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.broadcast.ReceiveBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.interlocking.InterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.FollowPathBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.GoToBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class DoMissionFSMBehaviour extends FSMBehaviour 
{
	private static final long serialVersionUID = 3990378395122410840L;
	private CollectMultiAgent _myAgent;
	
	public DoMissionFSMBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		
		this.registerFirstState(new StartDoMissionBehaviour(this._myAgent), "START-MISSION");
		this.registerState(new ReceiveBroadcastBehaviour(this._myAgent, "EXPLORE", "EXPLORE-BROADCAST"), "RECEIVE-EXPLORE-BROADCAST");
		this.registerState(new SendKnowledgeRequestBehaviour(this._myAgent), "SEND-KNOWLEDGE-REQUEST");
		this.registerState(new ReceiveKnowledgeBehaviour(this._myAgent), "RECEIVE-KNOWLEDGE");
		this.registerState(new GoToBehaviour(this._myAgent), "GO-TO");
		this.registerState(new InterlockingFSMBehaviour(this._myAgent), "INTERLOCKING");
		this.registerState(new ClientInterlockingFSMBehaviour(this._myAgent), "CLIENT-INTERLOCKING");
		this.registerState(new OpenChestFSMBehaviour(this._myAgent), "OPEN-CHEST");
		this.registerState(new PickUpObjectiveBehaviour(this._myAgent), "PICK-UP");
		this.registerLastState(new EndDoMissionBehaviour(this._myAgent), "END-MISSION");
		
		this.registerTransition("START-MISSION", "CLIENT-INTERLOCKING", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("CLIENT-INTERLOCKING", "RECEIVE-EXPLORE-BROADCAST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-EXPLORE-BROADCAST", "SEND-KNOWLEDGE-REQUEST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-EXPLORE-BROADCAST", "RECEIVE-KNOWLEDGE", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("SEND-KNOWLEDGE-REQUEST", "RECEIVE-KNOWLEDGE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-KNOWLEDGE", "GO-TO", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GO-TO", "RECEIVE-EXPLORE-BROADCAST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GO-TO", "INTERLOCKING", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("GO-TO", "OPEN-CHEST", FSMCodes.Events.END.ordinal());
		this.registerTransition("INTERLOCKING", "CLIENT-INTERLOCKING", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("OPEN-CHEST", "PICK-UP", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("PICK-UP", "END-MISSION", FSMCodes.Events.SUCESS.ordinal());
	}
	
	public int onEnd() 
	{
		this.resetChildren();
		this.reset();
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
