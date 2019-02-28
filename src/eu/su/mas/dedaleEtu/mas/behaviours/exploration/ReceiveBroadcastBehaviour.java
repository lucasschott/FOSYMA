package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveBroadcastBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 7295849474602290463L;
	private boolean received = false;
	
	public ReceiveBroadcastBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("BROADCAST-EXPLORATION"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		System.out.println(this.getClass().getName());
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null) {
			System.out.println("Received broadcast");
			this.received = true;
		}
	}
	
	public int onEnd() {
		if (this.received)
			return ExploMultiFSMBehaviour.Events.SUCESS.ordinal();
		return ExploMultiFSMBehaviour.Events.FAILURE.ordinal();
	}
}
