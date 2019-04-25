package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.utils.Mission;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveMissionCompletionNotification extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -859561566796847466L;
	private boolean received = false;
	private TankMultiAgent _myAgent;

	
	public ReceiveMissionCompletionNotification(TankMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		this.received = checkMessage();
	}
	
	
	public boolean checkMessage()
	{
		this.received = false;
		
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("NOTIFY-MISSION-COMPLETION"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null) 
		{
			System.out.println("RECEIVED MISSION NOTIFICATION");
			try 
			{
				Mission mission = (Mission) msg.getContentObject();
				this._myAgent.removeMission(mission);
				sendAck(msg.getSender());
				System.out.println("SENT MISSION NOTIFICATION ACK");
				this.received = true;
			} 
			catch (UnreadableException e) 
			{
				e.printStackTrace();
			}
		}
		
		return this.received;
	}
	
	public void sendAck(AID to)
	{
		ACLMessage msg=new ACLMessage(ACLMessage.CONFIRM);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("NOTIFY-MISSION-COMPLETION-ACK");
		msg.addReceiver(to);
		this._myAgent.sendMessage(msg);
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
