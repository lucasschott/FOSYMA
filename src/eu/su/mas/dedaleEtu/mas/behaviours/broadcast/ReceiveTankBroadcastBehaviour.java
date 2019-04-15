package eu.su.mas.dedaleEtu.mas.behaviours.broadcast;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import jade.lang.acl.ACLMessage;

public class ReceiveTankBroadcastBehaviour extends ReceiveBroadcastBehaviour 
{
	private static final long serialVersionUID = 7295849474602290463L;
	private CollectMultiAgent _myAgent;
	
	public ReceiveTankBroadcastBehaviour(CollectMultiAgent myagent) 
	{
		super(myagent, "TANK", "TANK-PING");
		this._myAgent = myagent;
	}
	
	public void handleMessage(ACLMessage msg) {
		this._myAgent.setTankPosition(msg.getContent());
		System.out.println("Updated tank position !");
	}
}
