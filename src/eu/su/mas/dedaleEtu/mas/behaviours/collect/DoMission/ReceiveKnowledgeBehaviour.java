package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import java.util.HashMap;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveKnowledgeBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -434251506059846646L;
	private CollectMultiAgent _myAgent;
	
	public ReceiveKnowledgeBehaviour(CollectMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() 
	{
		ACLMessage message = null;
		
		System.out.println("RECEIVE KNOWLEDGE");
		
		while ((message = this.receiveKnowledge()) != null)
		{
			try 
			{
				System.out.println("MERGED KNOWLEDGE FROM : " + message.getSender().getLocalName());
				this._myAgent.mergeTreasureMap((HashMap<String, Couple<Observation, Integer>>)message.getContentObject());
			} 
			catch (UnreadableException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public ACLMessage receiveKnowledge()
	{
		ACLMessage message = null;
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("SEND-KNOWLEDGE"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		message = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		return message;
	}

	@Override
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
