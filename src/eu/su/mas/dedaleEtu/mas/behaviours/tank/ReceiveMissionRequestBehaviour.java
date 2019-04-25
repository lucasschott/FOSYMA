package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.utils.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveMissionRequestBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7181433716622840145L;
	private boolean received = false;
	private TankMultiAgent _myAgent;

	public ReceiveMissionRequestBehaviour(TankMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		this.received = false;
		
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("REQUEST-MISSION"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		while ((msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern)) != null) {
			try {
				Agent agent = (Agent) msg.getContentObject();
				
				if (this._myAgent.isInMission(agent) ||  this._myAgent.isAlreadyAvailable(agent))
					continue;
				
				System.out.println("Adding : " + agent.getAID().toString());
				
				this.received = true;
				
				this._myAgent.addAvailableAgent((Agent)msg.getContentObject());
				System.out.println("Received Mission request from : " + msg.getSender());
			} 
			catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int onEnd()
	{
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}

}
