package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes.Events;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveKnowledgeRequestBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8244504004357597152L;
	private ExploreMultiAgent _myAgent;
	private boolean received = false;
	
	public ReceiveKnowledgeRequestBehaviour(ExploreMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		this.received = false;
		ACLMessage message = this.checkMessage();
		
		if (message != null)
			this.received = true;
	}
	
	public ACLMessage checkMessage()
	{
		ACLMessage message = null;
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("KNOWLEDGE-REQUEST"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		message = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		return message;
	}
	
	public int onEnd()
	{
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}

}
