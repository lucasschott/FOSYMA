package eu.su.mas.dedaleEtu.mas.behaviours.collect.NotifyMissionCompletion;

import java.io.IOException;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class SendMissionCompletionNotificationBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1920828366376853886L;
	private CollectMultiAgent _myAgent;
	
	public SendMissionCompletionNotificationBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() 
	{
		System.out.println("SENDING MISSION COMPLETION NOTIFICATION");
		
		DFAgentDescription[] result = this._myAgent.getMatchingAgents("TANK");
		ACLMessage msg = buildMessage(result);
		
		try {
			msg.setContentObject(this._myAgent.getCurrentMission());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this._myAgent.sendMessage(msg);
	}

	private ACLMessage buildMessage(DFAgentDescription[] result) {
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("NOTIFY-MISSION-COMPLETION");
		
		for (DFAgentDescription dsc : result)
		{
			if (!this.myAgent.getAID().toString().equals(dsc.getName().toString()))
				msg.addReceiver(dsc.getName());
		}
		
		return msg;
	}
	
	@Override
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
