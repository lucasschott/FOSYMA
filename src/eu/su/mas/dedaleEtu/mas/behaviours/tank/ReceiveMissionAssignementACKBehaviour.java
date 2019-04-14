package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMissionAssignementACKBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5124156471440331676L;
	private boolean received = false;
	private TankMultiAgent _myAgent;

	public ReceiveMissionAssignementACKBehaviour(TankMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this.received = false;
		
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("ACCEPT-MISSION"), MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null) 
		{
			this.received = true;
			
			String uuid = msg.getContent();
			this._myAgent.setMissionOnGoing(uuid);
			
			System.out.println("Received mission ACK FROM : " + msg.getSender());
		}
	}
	
	public int onEnd()
	{
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}

}
