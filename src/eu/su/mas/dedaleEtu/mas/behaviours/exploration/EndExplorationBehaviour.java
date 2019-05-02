package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.RandomWalkBehaviourOLD;
import eu.su.mas.dedaleEtu.mas.behaviours.RendezVousFSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class EndExplorationBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -626168446823784736L;

	public EndExplorationBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
	}

	public void action() {
	}
	
	public int onEnd() {
		this.myAgent.addBehaviour(new RendezVousFSMBehaviour((AbstractMultiAgent)this.myAgent));
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
