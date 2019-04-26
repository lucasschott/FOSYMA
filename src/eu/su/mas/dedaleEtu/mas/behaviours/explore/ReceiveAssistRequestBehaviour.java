package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveAssistRequestBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6811866771506621533L;
	private boolean received = false;
	private ExploreMultiAgent _myAgent;
	
	public ReceiveAssistRequestBehaviour(ExploreMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() 
	{
		this.received = false;
		
		ACLMessage message = checkMessage();
		
		if (message == null)
			return;
		
		this.received = true;
		this._myAgent.setDestinationId(message.getContent());
	}

	public ACLMessage checkMessage()
	{
		MessageTemplate pattern = MessageTemplate.MatchProtocol("REQUEST-CHEST-ASSIST");
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this._myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this._myAgent).receive(pattern);
		
		if (msg != null)
			return msg;
		
		return null;
	}
	
	public int onEnd()
	{
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
