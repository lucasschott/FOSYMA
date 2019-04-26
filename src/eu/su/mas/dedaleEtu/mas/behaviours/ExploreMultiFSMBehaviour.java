package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.EndExploreBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.ReceiveAssistRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.ResetKnowledgeBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.explore.StartExploreBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class ExploreMultiFSMBehaviour extends FSMBehaviour 
{
	private static final long serialVersionUID = 5631327286239419149L;
	
	public ExploreMultiFSMBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		
		this.registerFirstState(new StartExploreBehaviour(myagent), "START-EXPLORE");
		this.registerState(new ResetKnowledgeBehaviour(myagent), "RESET-KNOWLEDGE");
		this.registerState(new ReceiveAssistRequestBehaviour(myagent), "RECEIVE-ASSIST-REQUEST");
		this.registerState(new RandomWalkBehaviour(myagent), "RANDOM-WALK");
		this.registerLastState(new EndExploreBehaviour(myagent), "END-EXPLORE");
		
		this.registerTransition("START-EXPLORE","RESET-KNOWLEDGE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RESET-KNOWLEDGE", "RECEIVE-ASSIST-REQUEST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-ASSIST-REQUEST", "RANDOM-WALK", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-ASSIST-REQUEST", "ASSIST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RANDOM-WALK", "RECEIVE-ASSIST-REQUEST", FSMCodes.Events.SUCESS.ordinal());
	}

}
