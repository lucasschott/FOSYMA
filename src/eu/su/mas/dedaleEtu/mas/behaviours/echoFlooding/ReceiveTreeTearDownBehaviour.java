package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveTreeTearDownBehaviour  extends OneShotBehaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 124111300274661179L;
	
	boolean received = false;
	AbstractMultiAgent _myAgent;
	String treeId;
	
	public ReceiveTreeTearDownBehaviour(AbstractMultiAgent myagent, String treeId) {
		super(myagent);
		this._myAgent = myagent;
		this.treeId = treeId;
	}

	@Override
	public void action() {
		if (this._myAgent.treeExist(this.treeId) == false)
			return;
		
		this.received = this.receivedTearDown();
	}
	
	public int onEnd() {
		
		if (this.received) {
			System.out.println(this._myAgent.getLocalName() + " Received tear down order !");
			return FSMCodes.Events.SUCESS.ordinal();	
		}
		
		return FSMCodes.Events.FAILURE.ordinal();
	}
	
	public boolean receivedTearDown() {
		ACLMessage msg;
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("TREE-TEARDOWN"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchConversationId(this.treeId));
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		return msg != null;
	}

}
