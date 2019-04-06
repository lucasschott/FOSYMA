package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class StartExplorationBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = -1560211412262637077L;

	public StartExplorationBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		ExploreMultiAgent agent = (ExploreMultiAgent) this.myAgent;
		agent.registerService("EXPLORE_AGENTS");
		agent.registerService("EXPLORATION");
		System.out.println(this.getClass().getName());
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
