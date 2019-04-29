package eu.su.mas.dedaleEtu.mas.behaviours.collect.NotifyMissionCompletion;

import java.io.IOException;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class SendUpdateTankerKnowledgeBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3679471786980272403L;
	private CollectMultiAgent _myAgent;
	
	public SendUpdateTankerKnowledgeBehaviour(CollectMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() 
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		DFAgentDescription[] result = this._myAgent.getMatchingAgents("TANK");
		
		msg.setSender(this._myAgent.getAID());
		msg.setProtocol("UPDATE-TANKER-KNOWLEDGE");
		
		for (DFAgentDescription dsc: result)
			msg.addReceiver(dsc.getName());
		
		try 
		{
			msg.setContentObject(this._myAgent.getTreasureMap());
			this._myAgent.sendMessage(msg);
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
