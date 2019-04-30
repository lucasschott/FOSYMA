package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class SendKnowledgeRequestBehaviour extends OneShotBehaviour 
{
	private static final long serialVersionUID = -8661644242063291028L;
	private CollectMultiAgent _myAgent;

	public SendKnowledgeRequestBehaviour(CollectMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.setSender(this._myAgent.getAID());
		message.setProtocol("KNOWLEDGE-REQUEST");
		
		for (DFAgentDescription dsc: this._myAgent.getMatchingAgents("EXPLORE"))
			message.addReceiver(dsc.getName());
		
		this._myAgent.sendMessage(message);
	}

	@Override
	public int onEnd() {
		System.out.println("SEND KNOWLEDGE REQUEST");
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
