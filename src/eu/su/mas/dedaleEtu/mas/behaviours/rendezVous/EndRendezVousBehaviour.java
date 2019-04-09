package eu.su.mas.dedaleEtu.mas.behaviours.rendezVous;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import jade.core.behaviours.OneShotBehaviour;

public class EndRendezVousBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1279452839140119908L;
	AbstractMultiAgent _myAgent;
	
	public EndRendezVousBehaviour(AbstractMultiAgent myAgent) {
		super(myAgent);
		this._myAgent = myAgent;
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		this._myAgent.deregisterService("EXPLORATION");
		System.out.println(this.myAgent.getLocalName() + " FINISHED RENDEZ VOUS !");

	}

}
