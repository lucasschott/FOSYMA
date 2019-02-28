package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import java.util.ArrayList;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.utils.Deserializer;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMapBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = -6852053045294451837L;
	
	ExploreMultiAgent _myAgent;
	boolean received = false;
	
	public ReceiveMapBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		
		// Keep an ExploreMultiAgent reference to avoid long cast in action method
		this._myAgent = myagent;
	}

	@Override
	public void action() {
		System.out.println(this.getClass().getName());
		ACLMessage msg = this.receiveMap();
		
		if (msg != null) {
			 ArrayList<ArrayList<String>> map = this.unpackRemoteMatchNodes(msg);
			 this.mergeRemoteMatchNodes(map);
			 this.received = true;
		}
	}
	
	public int onEnd() {
		if (this.received == true)
			return ExploMultiFSMBehaviour.Events.SUCESS.ordinal();
		return ExploMultiFSMBehaviour.Events.SUCESS.ordinal();
	}
	
	private void mergeRemoteMatchNodes(ArrayList<ArrayList<String>> match)
	{
		System.out.println("Update : " + this._myAgent.getLocalName());
		System.out.println(this._myAgent.getLocalName() + " " + this._myAgent.map.getClosedNodes().size());
		
		for(ArrayList<String> desc: match) {
			String nodeId = desc.get(0);
			
			/* No need to merge an already closed node */
			if (this._myAgent.map.isClosed(nodeId)) {
				continue;
			}
			
			/* Remove from open as it is now closed thanks to remote informations */
			else if (this._myAgent.map.isOpen(nodeId)) {
				this._myAgent.map.removeOpen(nodeId);
				this._myAgent.map.addClosed(nodeId);
			}
			
			/* Node is neither open or closed , should not happen but.. */
			else {
				this._myAgent.map.addClosed(nodeId);
			}
			
			this._myAgent.map.addNode(nodeId);
			
			for(int i = 1; i < desc.size(); i++) {
				String newNode = desc.get(i);
				
				/* Add new node / edge : robust again duplicates */
				if (!this._myAgent.map.isOpen(newNode) && !this._myAgent.map.isClosed(newNode)) {
					this._myAgent.map.addNode(newNode);
					this._myAgent.map.addOpen(newNode);
				}
				
				this._myAgent.map.addEdge(nodeId, newNode);
			}
		}
		
		/* Cleanup Open nodes if the associated closed one was received after */
		for (String nodeId: this._myAgent.map.getOpenNodes()) {
			if (this._myAgent.map.isClosed(nodeId))
				this._myAgent.map.removeOpen(nodeId);
		}
		
		System.out.println(this._myAgent.getLocalName() + " " + this._myAgent.map.getClosedNodes().size());
	}
	
	private ArrayList<ArrayList<String>> unpackRemoteMatchNodes(ACLMessage msg)
	{
		ArrayList<ArrayList<String>> match;
		
		match = Deserializer.deserialize(msg.getContent());
		
		return match;
	}
	
	private ACLMessage receiveMap()
	{
		MessageTemplate pattern = MessageTemplate.MatchProtocol("INFORM-CLOSED-NODES");
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this._myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this._myAgent).receive(pattern);
		
		if (msg != null)
			return msg;
		
		return null;
	}

}
