package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class EndExplorationBehaviour extends OneShotBehaviour {

	public EndExplorationBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
	}

	public void action() {
		try {
			DFService.deregister(this.myAgent, ((ExploreMultiAgent)(this.myAgent)).desc);
		} 
		catch (FIPAException fe) {
			fe.printStackTrace(); 
		}
		
	}
}
