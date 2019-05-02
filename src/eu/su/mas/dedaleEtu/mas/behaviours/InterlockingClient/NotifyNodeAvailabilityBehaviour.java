package eu.su.mas.dedaleEtu.mas.behaviours.InterlockingClient;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class NotifyNodeAvailabilityBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8839583964815684816L;
	private AbstractMultiAgent _myAgent;
	
	
	public NotifyNodeAvailabilityBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		System.out.println("NOTIFY NODE AVAILABLE TEST");
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.setSender(this._myAgent.getAID());
		message.setProtocol("INFORM-NODE-AVAILABILITY");
		message.setConversationId(this._myAgent.peekConflict().getUid());
		
		this._myAgent.sendMessage(message);
	}
	
	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
