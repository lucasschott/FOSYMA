package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitNodeAvailabilityBehaviour extends OneShotBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1287994768522584863L;
	private AbstractMultiAgent _myAgent;
	private boolean received;

	public WaitNodeAvailabilityBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this.received = checkMessage();
	}

	private boolean checkMessage()
	{
		System.out.println(this._myAgent.getLocalName() + " Conflicts count : " + this._myAgent.getConflictCount());
		ACLMessage message = null;
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("INFORM-NODE-AVAILABILITY"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchConversationId(this._myAgent.peekConflict().getUid()));
	
		message = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		return message != null;
	}
	
	@Override
	public int onEnd() 
	{
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}

}
