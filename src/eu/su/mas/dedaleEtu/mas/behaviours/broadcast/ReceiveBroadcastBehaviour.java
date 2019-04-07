package eu.su.mas.dedaleEtu.mas.behaviours.broadcast;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveBroadcastBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 7295849474602290463L;
	private boolean received = false;
	private String match = null;
	
	public ReceiveBroadcastBehaviour(ExploreMultiAgent myagent, String match) {
		super(myagent);
		this.match = match;
	}

	@Override
	public void action() {
		this.received = false;
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol(match), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null) {
			this.received = true;
			System.out.println("Received broadcast " + this.match);
			
		}
	}
	
	public int onEnd() {
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
