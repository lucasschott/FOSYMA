package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveTreeRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 7295849474602290463L;
	private boolean received = false;
	private ExploreMultiAgent _myAgent;
		
	public ReceiveTreeRequestBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() {
		this.received = false;
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("TREE-REQUEST"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null) {
			this.received = true;
			System.out.println(this.myAgent.getLocalName().toString() + " Received Tree Request from " + msg.getSender().toString());
			System.out.println("ON tree : " + msg.getContent());
			
			String treeId = msg.getContent();
			
			if (!this._myAgent.treeExist(treeId)) {
				System.out.println("TEST");
				this._myAgent.addTree(treeId, msg.getSender(), false);
				this.sendAck(msg.getSender(), treeId);
			}
		}
	}
	
	public void sendAck(AID sendTo, String treeId) {
		ACLMessage msg=new ACLMessage(ACLMessage.AGREE);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("TREE-REQUEST-ACK");
		msg.addReceiver(sendTo);
		msg.setContent(treeId);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
		
		System.out.println(this._myAgent.getLocalName() + " send tree request ACK for : " + treeId + " to " + sendTo.getLocalName());
	}
	
	public int onEnd() {
		if (this.received)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
