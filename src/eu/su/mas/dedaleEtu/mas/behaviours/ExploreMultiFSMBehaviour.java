package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import jade.core.behaviours.FSMBehaviour;

public class ExploreMultiFSMBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5631327286239419149L;
	
	public ExploreMultiFSMBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		
		this.registerFirstState(new RandomWalkBehaviour(myagent), "RANDOM");
		
		this.registerTransition("RANDOM","RANDOM", FSMCodes.Events.SUCESS.ordinal());
	}

}
