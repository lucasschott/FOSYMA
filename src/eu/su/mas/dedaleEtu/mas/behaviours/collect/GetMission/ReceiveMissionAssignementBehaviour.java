package eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.utils.Mission;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveMissionAssignementBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 888258779764232311L;
	private boolean received = false;
	private CollectMultiAgent _myAgent;

	public ReceiveMissionAssignementBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		this.received = this.checkMessage();
	}
	
	public boolean checkMessage()
	{
		this.received = false;
		
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("ASSIGN-MISSION"), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null) 
		{
			try {
				this._myAgent.setCurrentMission((Mission)msg.getContentObject());
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("RECEIVE MISSION ASSIGNEMENT");
			DFAgentDescription[] result = this._myAgent.getMatchingAgents("TANK");
			this.sendAck(result, this._myAgent.getCurrentMission().getUUID());
			this._myAgent.setDestinationId(this._myAgent.getCurrentMission().getDestination());
			this.received = true;
		}
		
		return this.received;
	}
	
	public void sendAck(DFAgentDescription[] result, String uuid)
	{
		ACLMessage msg=new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("ACCEPT-MISSION");
		
		for (DFAgentDescription dsc : result)
		{
			if (!this.myAgent.getAID().toString().equals(dsc.getName().toString()))
				msg.addReceiver(dsc.getName());
			System.out.println("SENDING MISSION ACK TO : " + dsc.getName());
		}
		
		msg.setContent(uuid);
		
		this._myAgent.sendMessage(msg);
	}
	
	public int onEnd()
	{
		if (this.received == true)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}

}
