package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import jade.domain.AMSService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.DFService;


public class SendOpenNodesBehaviour extends TickerBehaviour{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2058134622078521998L;
	private DFAgentDescription dfd;

	/**
	 * An agent tries to contact its friend and to give him its current position
	 * @param myagent the agent who posses the behaviour
	 * @throws FIPAException 
	 *  
	 */
	public SendOpenNodesBehaviour (final Agent myagent){
		super(myagent, 3000);
		//super(myagent);
		
	}

	@Override
	public void onTick() {
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		DFAgentDescription[] result = {};
		
	}
}