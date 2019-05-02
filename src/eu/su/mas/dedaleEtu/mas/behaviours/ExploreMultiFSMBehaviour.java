package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.InterlockingClient.ClientInterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.broadcast.SendBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.AssistFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.EndExploreBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.RandomWalkBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.ReceiveAssistRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.ReceiveKnowledgeRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.ResetKnowledgeBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.SendKnowledgeBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.StartExploreBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.interlocking.InterlockingFSMBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class ExploreMultiFSMBehaviour extends FSMBehaviour 
{
	private static final long serialVersionUID = 5631327286239419149L;
	
	public ExploreMultiFSMBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		
		this.registerFirstState(new StartExploreBehaviour(myagent), "START-EXPLORE");
		this.registerState(new ResetKnowledgeBehaviour(myagent), "RESET-KNOWLEDGE");
		this.registerState(new SendBroadcastBehaviour(myagent, "COLLECT", "EXPLORE-BROADCAST"), "EXPLORE-BROADCAST");
		this.registerState(new ReceiveKnowledgeRequestBehaviour(myagent), "RECEIVE-KNOWLEDGE-REQUEST");
		this.registerState(new SendKnowledgeBehaviour(myagent), "SEND-KNOWLEDGE");
		this.registerState(new ReceiveAssistRequestBehaviour(myagent), "RECEIVE-ASSIST-REQUEST");
		this.registerState(new RandomWalkBehaviour(myagent), "RANDOM-WALK");
		this.registerState(new ClientInterlockingFSMBehaviour(myagent), "CLIENT-INTERLOCKING");
		this.registerState(new AssistFSMBehaviour(myagent), "ASSIST");
		this.registerLastState(new EndExploreBehaviour(myagent), "END-EXPLORE");
		
		this.registerTransition("START-EXPLORE","RESET-KNOWLEDGE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RESET-KNOWLEDGE", "CLIENT-INTERLOCKING", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("CLIENT-INTERLOCKING", "EXPLORE-BROADCAST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("EXPLORE-BROADCAST", "RECEIVE-KNOWLEDGE-REQUEST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-KNOWLEDGE-REQUEST", "SEND-KNOWLEDGE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-KNOWLEDGE-REQUEST", "RECEIVE-ASSIST-REQUEST", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("SEND-KNOWLEDGE", "RESET-KNOWLEDGE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-ASSIST-REQUEST", "ASSIST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-ASSIST-REQUEST", "RANDOM-WALK", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("ASSIST", "RESET-KNOWLEDGE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RANDOM-WALK", "CLIENT-INTERLOCKING", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RANDOM-WALK", "CLIENT-INTERLOCKING", FSMCodes.Events.FAILURE.ordinal());
	}

}
