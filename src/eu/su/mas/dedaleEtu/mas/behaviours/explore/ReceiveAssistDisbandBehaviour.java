package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.utils.Mission;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveAssistDisbandBehaviour extends OneShotBehaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3722398474602125015L;
	private ExploreMultiAgent _myAgent;
	private boolean received = false;

	public ReceiveAssistDisbandBehaviour(ExploreMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		// Necessary for reset on every run 
		this.received = false;
		
		this.received = checkMessage(this._myAgent.getCurrentAssist());
	}

	public int onEnd()
	{
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}
	
	public boolean checkMessage(Mission currentAssist)
	{
		ACLMessage message = null;
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("DISBAND-SUPPORT"), MessageTemplate.MatchPerformative(ACLMessage.CANCEL));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchSender(this._myAgent.getCurrentAssist().getLeader().getAID()));
	
		message = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		return message != null;
	}
}
