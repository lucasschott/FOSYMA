package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class StartExplorationBehaviour extends OneShotBehaviour {

	public StartExplorationBehaviour(ExploreMultiAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent);
	}

	@Override
	public void action() {		
		ServiceDescription sd = new ServiceDescription();
		sd.setType("EXPLORATION");
		sd.setName(myAgent.getLocalName());
		
		((ExploreMultiAgent)(this.myAgent)).desc.addServices(sd);
		try {
			DFService.register(this.myAgent, ((ExploreMultiAgent)(this.myAgent)).desc);
		} 
		catch (FIPAException fe) {
			fe . printStackTrace(); 
		}
	}
	
	public int onEnd() {
		return ExploMultiFSMBehaviour.Events.SUCESS.ordinal();
	}
}
