package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import java.io.IOException;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class RequestSupportBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = -3670454550654233243L;
	private CollectMultiAgent _myAgent;
	
	public RequestSupportBehaviour(CollectMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		DFAgentDescription[] result = this._myAgent.getMatchingAgents("EXPLORE");
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		
		message.setProtocol("REQUEST-SUPPORT");
		message.setSender(this._myAgent.getAID());
		for (DFAgentDescription dsc: result)
			message.addReceiver(dsc.getName());
		
		try {
			message.setContentObject(this._myAgent.getCurrentMission());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this._myAgent.sendMessage(message);
	}

	@Override
	public int onEnd() 
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
