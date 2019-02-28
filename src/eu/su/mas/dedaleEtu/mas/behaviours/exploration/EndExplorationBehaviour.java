package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.RandomWalkBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class EndExplorationBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -626168446823784736L;

	public EndExplorationBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
	}

	public void action() {
		System.out.println(this.getClass().getName());
		
		ExploreMultiAgent agent = (ExploreMultiAgent) this.myAgent;
		agent.deregisterService("EXPLORATION");
	}
	
	public int onEnd() {
		this.myAgent.addBehaviour(new RandomWalkBehaviour((AbstractDedaleAgent)this.myAgent));
		return 0;
	}
}
