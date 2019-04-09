package eu.su.mas.dedaleEtu.mas.behaviours;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import eu.su.mas.dedaleEtu.mas.utils.Deserializer;
import eu.su.mas.dedaleEtu.mas.utils.Serializer;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import eu.su.mas.dedaleEtu.mas.agents.MapHandler;


/**
 * This behaviour allows an agent to explore the environment and learn the associated topological map.
 * The algorithm is a pseudo - DFS computationally consuming because its not optimised at all.</br>
 * 
 * When all the nodes around him are visited, the agent randomly select an open node and go there to restart its dfs.</br> 
 * This (non optimal) behaviour is done until all nodes are explored. </br> 
 * 
 * Warning, this behaviour does not save the content of visited nodes, only the topology.</br> 
 * Warning, this behaviour is a solo exploration and does not take into account the presence of other agents (or well) and indefinitely tries to reach its target node
 * @author hc
 *
 */
public class ExploMultiBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 8567689731496787661L;
	private boolean finished = false;
	private DFAgentDescription desc = null;
	private ExploreMultiAgent _myAgent;
	private MapHandler map;
	private ConcurrentHashMap<AID, Integer> timeoutTable = new ConcurrentHashMap<AID, Integer>();

	public ExploMultiBehaviour(final ExploreMultiAgent myagent, MapHandler map) 
	{
		super(myagent);
		
		this.map = map;
		
		desc = new DFAgentDescription();
		desc.setName(myAgent.getAID());
		
		ServiceDescription sd = new ServiceDescription();
		sd.setType("EXPLORATION");
		sd.setName(myAgent.getLocalName());
		
		try {
			DFService.register(this.myAgent, desc);
		} 
		catch (FIPAException fe) {
			fe . printStackTrace(); 
		}
	}

	@Override
	public void action() {
		AID other;
		ACLMessage msg;
		
		System.out.println(this.myAgent.getLocalName() + " got : " + this.map.getClosedNodes().size());
		
		this.updateTimeoutTable();
		
		while ((other = this.receivedBroadcast()) != null)
			this.sendClosedNodes(other);
		
		while ((msg = this.receiveClosedNodes()) != null) {	
			ArrayList<ArrayList<String>> remoteClosed = this.unpackRemoteMatchNodes(msg);
			this.mergeRemoteMatchNodes(remoteClosed);
		}
		
		//0) Retrieve the current position
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
	
		if (myPosition!=null){
			
			//List of observable from the agent's current position
			List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition

			/**
			 * Just added here to let you see what the agent is doing, otherwise he will be too quick
			 */
			try {
				this.myAgent.doWait(500);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//1) remove the current node from openlist and add it to closedNodes.
			map.addClosed(myPosition);
			map.removeOpen(myPosition);
			map.addNode(myPosition);
			
			//2) get the surrounding nodes and, if not in closedNodes, add them to open nodes.
			Iterator<Couple<String, List<Couple<Observation, Integer>>>> iter=lobs.iterator();
			
			String nextNode = null;
			
			while(iter.hasNext())
			{
				String nodeId=iter.next().getLeft();
				if (!map.isClosed(nodeId))
				{
					if (!map.isOpen(nodeId))
					{
						map.addOpen(nodeId);
						map.addNode(nodeId);
						map.addEdge(myPosition, nodeId);
					}
					else
					{
						//the node exist, but not necessarily the edge
						map.addEdge(myPosition, nodeId);
					}
					if (nextNode==null)
						nextNode=nodeId;
				}
			}

			//3) while openNodes is not empty, continues.
			if (map.getOpenNodes().isEmpty()){
				//Explo finished
				finished=true;
				System.out.println("Exploration successufully done, behaviour removed.");
				System.out.println(this.map.getClosedNodes().size());
			}
			else{
				//4) select next move.
				//4.1 If there exist one open node directly reachable, go for it,
				//	 otherwise choose one from the openNode list, compute the shortestPath and go for it
				if (nextNode==null) {
					//no directly accessible openNode
					//chose one, compute the path and take the first step.
					
					try {
						List<String> shortestPath = map.getMap().getShortestPath(myPosition, map.getOpenNodes().get(0));
						
						if (shortestPath == null) {
							nextNode = null;
						}

						else
							nextNode=map.getMap().getShortestPath(myPosition, map.getOpenNodes().get(0)).get(0);	
						
					}
					catch (NoSuchElementException ex) {
						System.out.println("catched");
					}
					
				}
				
				/* If unable to move to desired location : shuffle open nodes */
				if (nextNode == null || ((AbstractDedaleAgent)this.myAgent).moveTo(nextNode) == false) {
					this.map.shuffleOpenNodes();
				}
			}

		}
	}
	
	private AID receivedBroadcast()
	{
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("BROADCAST-EXPLORATION"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null && this.isTimedOut(msg.getSender())) {
			this.addToTimeoutTable(msg.getSender());
			return msg.getSender();
		}
		
		return null;
	}
	
	private void updateTimeoutTable()
	{
		Set<AID> keys = this.timeoutTable.keySet();
	
		for (AID key: keys) {
			Integer timeout = this.timeoutTable.get(key) - 1;
			
			if (timeout <= 0 )
				this.timeoutTable.remove(key);
			else
				this.timeoutTable.put(key, timeout);
		}
	}
	
	private boolean isTimedOut(AID other) {
		return this.timeoutTable.get(other) == null;
	}
	
	private void addToTimeoutTable(AID other) {
		this.timeoutTable.put(other, 3);
	}
	
	@Deprecated
	private void sendOpenNodes(AID receiver)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		String content;
		
		msg.setProtocol("REQUEST-REMOTE-OPEN-NODES");
		msg.addReceiver(receiver);
		msg.setSender(this.myAgent.getAID());
		
		content = Serializer.serialize(this.map.getOpenNodes());
		msg.setContent(content);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	private void sendClosedNodes(AID receiver)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		String content;
		
		msg.setProtocol("INFORM-CLOSED-NODES");
		msg.addReceiver(receiver);
		msg.setSender(this.myAgent.getAID());
		
		content = Serializer.serialize(this.matchOpenedNodes(null));
		msg.setContent(content);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	private ACLMessage receiveClosedNodes()
	{
		MessageTemplate pattern = MessageTemplate.MatchProtocol("INFORM-CLOSED-NODES");
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null)
			return msg;
		
		return null;
	}
	
	@Deprecated
	private ACLMessage receiveOpenNodes()
	{
		MessageTemplate pattern = MessageTemplate.MatchProtocol("REQUEST-REMOTE-OPEN-NODES");
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		pattern = MessageTemplate.and(pattern, MessageTemplate.not(MessageTemplate.MatchSender(this.myAgent.getAID())));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null)
			return msg;
		
		return null;
	}
	
	private ArrayList<String> unpackRemoteOpenNodes(ACLMessage msg)
	{
		ArrayList<String> openNodes;
		
		openNodes = Deserializer.deserialize(msg.getContent());
		return openNodes;
	}
	
	private ArrayList<ArrayList<String>> matchOpenedNodes(ArrayList<String> remoteOpen)
	{
		ArrayList<ArrayList<String>> match = new ArrayList<>();
		
			for(String node: this.map.getClosedNodes()) {
				ArrayList<String> description = new ArrayList<String>();
				description.add(node);
				description.addAll(this.map.getMap().getNeighbours(node));
				match.add(description);
		}
		
		return match;
	}
	
	@Deprecated
	private void sendMatchNodes(AID receiver, ArrayList<ArrayList<String>> match)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		String content;
		
		msg.setProtocol("RESPONSE-REMOTE-OPEN-NODES");
		msg.addReceiver(receiver);
		msg.setSender(this.myAgent.getAID());
		
		content = Serializer.serialize(match);
		msg.setContent(content);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	@Deprecated
	private ACLMessage receiveMatchNodes() {
		MessageTemplate pattern = MessageTemplate.MatchProtocol("RESPONSE-REMOTE-OPEN-NODES");
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null)
			return msg;
		
		return null;
	}
	
	private ArrayList<ArrayList<String>> unpackRemoteMatchNodes(ACLMessage msg)
	{
		ArrayList<ArrayList<String>> match;
		
		match = Deserializer.deserialize(msg.getContent());
		
		return match;
	}
	
	private void mergeRemoteMatchNodes(ArrayList<ArrayList<String>> match)
	{
		System.out.println("Update : " + this.myAgent.getLocalName());
		
		for(ArrayList<String> desc: match) {
			String nodeId = desc.get(0);
			
			/* No need to merge an already closed node */
			if (this.map.isClosed(nodeId)) {
				continue;
			}
			
			/* Remove from open as it is now closed thanks to remote informations */
			else if (this.map.isOpen(nodeId)) {
				this.map.removeOpen(nodeId);
				this.map.addClosed(nodeId);
			}
			
			/* Node is neither open or closed , should not happen but.. */
			else {
				this.map.addClosed(nodeId);
			}
			
			this.map.addNode(nodeId);
			
			for(int i = 1; i < desc.size(); i++) {
				String newNode = desc.get(i);
				
				/* Add new node / edge : robust again duplicates */
				if (!this.map.isOpen(newNode) && !this.map.isClosed(newNode)) {
					this.map.addNode(newNode);
					this.map.addOpen(newNode);
				}
				
				this.map.addEdge(nodeId, newNode);
			}
		}
		
		/* Cleanup Open nodes if the associated closed one was received after */
		for (String nodeId: this.map.getOpenNodes()) {
			if (this.map.isClosed(nodeId))
				this.map.removeOpen(nodeId);
		}
	}

	@Override
	public boolean done() {
		
		if (finished == true) 
		{
			try {
				DFService.deregister(this.myAgent, desc);
			} 
			catch (FIPAException fe) {
				fe.printStackTrace(); 
			}
			
			this.myAgent.addBehaviour(new RandomWalkBehaviour((AbstractDedaleAgent)this.myAgent));
		}
		
		return finished;
	}
}
