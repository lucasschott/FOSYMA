package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendTreeTearDownBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4089508053328052949L;
	private String treeId;
	private ExploreMultiAgent _myAgent;
	
	public SendTreeTearDownBehaviour(ExploreMultiAgent myagent, String treeId) {
		super(myagent);
		this.treeId = treeId;
		this._myAgent = myagent;
	}

	@Override
	public void action() {
		
		if (this._myAgent.treeExist(this.treeId) == false)
			return;
		
		System.out.println(this._myAgent.getLocalName() + " Sending tree tear down !");
		
		for (AID child: this._myAgent.getTree(this.treeId).getChildren())
			this.sendTearDown(child);
		
		this._myAgent.removeTree(this.treeId);
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
	
	public void sendTearDown(AID child)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		
		msg.setSender(this.myAgent.getAID());
		msg.addReceiver(child);
		msg.setProtocol("TREE-TEARDOWN");
		msg.setConversationId(this.treeId);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}

}
