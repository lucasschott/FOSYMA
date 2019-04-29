package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import java.util.HashMap;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveUpdateTankerKnowledgeBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1142740046798619362L;
	private AbstractMultiAgent _myAgent;
	private String protocol;
	
	public ReceiveUpdateTankerKnowledgeBehaviour(AbstractMultiAgent myagent, String protocol)
	{
		super(myagent);
		this.protocol = protocol;
		this._myAgent = myagent;
	}

	@Override
	public void action() 
	{
		ACLMessage message = checkMessage();
		
		if (message != null)
		{
			System.out.println("UPDATE TANKER KNOWLEDGE !");
			try 
			{
				HashMap<String, Couple<Observation, Integer>> toMerge = (HashMap<String, Couple<Observation, Integer>>) message.getContentObject();
				
				System.out.println(toMerge);
				
				for (String key: toMerge.keySet())
				{
					if (this._myAgent.getTreasureMap().containsKey(key) == false)
						this._myAgent.getTreasureMap().put(key, toMerge.get(key));
					
					else if (this._myAgent.getTreasureMap().get(key).getRight() > toMerge.get(key).getRight())
						this._myAgent.getTreasureMap().put(key, toMerge.get(key));
				}
			}
			
			catch (UnreadableException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
	
	ACLMessage checkMessage()
	{
		MessageTemplate pattern = MessageTemplate.MatchProtocol(this.protocol);
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this._myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this._myAgent).receive(pattern);
		
		return msg;
	}
}
