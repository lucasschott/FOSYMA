package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import java.io.IOException;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes.Events;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class SendKnowledgeBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4154237676593073629L;
	private AbstractMultiAgent _myAgent;

	public SendKnowledgeBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		ACLMessage message = this.buildMessage(this._myAgent.getMatchingAgents("COLLECT"));
		try {
			message.setContentObject(this._myAgent.getTreasureMap());
			this._myAgent.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ACLMessage buildMessage(DFAgentDescription[] result) 
	{
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("SEND-KNOWLEDGE");
		
		for (DFAgentDescription dsc : result)
		{
			if (!this.myAgent.getAID().toString().equals(dsc.getName().toString()))
				msg.addReceiver(dsc.getName());
		}
		
		return msg;
	}
	
	public int onEnd() 
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
