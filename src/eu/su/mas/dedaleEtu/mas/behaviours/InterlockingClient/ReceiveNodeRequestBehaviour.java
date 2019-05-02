package eu.su.mas.dedaleEtu.mas.behaviours.InterlockingClient;

import java.util.Random;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.utils.Conflict;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveNodeRequestBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4018781828966619839L;
	private AbstractMultiAgent _myAgent;
	private boolean received = false;

	public ReceiveNodeRequestBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this.received = false;
		
		ACLMessage message = receiveMessage();
		
	
		if (message != null && isMessageValid(message)) 
		{
				try 
				{
					this.received = true;
					this._myAgent.PushConflict((Conflict) message.getContentObject());
				} 
				catch (UnreadableException e) 
				{
					this.received = false;
				}
			}
	}
	
	public ACLMessage receiveMessage()
	{
		ACLMessage message = null;
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("REQUEST-NODE"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
	
		message = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		System.out.println("RECEIVE-NODE-REQUEST : " + message);
		return message;
	}
	
	public boolean isMessageValid(ACLMessage message)
	{
		try 
		{
			Conflict conflict = (Conflict) message.getContentObject();
			
			if (conflict.getConflictingNode().equals(this._myAgent.getCurrentPosition()))
			{
				Random r= new Random();
				
				//if (conflict.getPriority() >= r.nextInt(this._myAgent.getPriority()))
				//{
					System.out.println("RECEIVE VALID NODE REQUEST");
					return true;
				//}
			}
		}
		
		catch (UnreadableException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	public int onEnd()
	{
		System.out.println("RECEIVE NOT REQUEST : " + this.received);
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}

}
