package eu.su.mas.dedaleEtu.mas.behaviours.collect.NotifyMissionCompletion;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMissionCompletionNotificationACKBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4773810728252094527L;
	private CollectMultiAgent _myAgent;
	private boolean received = false;

	public ReceiveMissionCompletionNotificationACKBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this.received = checkMessage();
	}

	public boolean checkMessage()
	{
		this.received = false;
		
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("NOTIFY-MISSION-COMPLETION-ACK"), MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchSender(this._myAgent.getMatchingAgents("TANK")[0].getName()));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null) 
			this.received = true;
		
		return this.received;
	}
	
	@Override
	public int onEnd() {
		if (this.received) {
			System.out.println("RECEIVED MISSION NOTIFICATION ACK");
			return FSMCodes.Events.SUCESS.ordinal();
		}
		return FSMCodes.Events.FAILURE.ordinal();
	}

}
