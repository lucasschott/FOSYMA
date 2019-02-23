package eu.su.mas.dedaleEtu.mas.behaviours;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
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

	public ExploMultiBehaviour(final ExploreMultiAgent myagent, MapHandler map) 
	{
		super(myagent);
		
		this.map = map;
		
		desc = new DFAgentDescription();
		desc.setName(myAgent.getAID());
		
		ServiceDescription sd = new ServiceDescription();
		sd.setType("EXPLORATION");
		sd.setName(myAgent.getLocalName());
		
		desc.addServices(sd);
		try {
			DFService.register(this.myAgent, desc);
		} 
		catch (FIPAException fe) {
			fe . printStackTrace(); 
		}
	}

	@Override
	public void action() {
		
		System.out.println(((ExploreMultiAgent)this.myAgent).map);
		AID other;
		
		other = this.receivedBroadcast();
		
		if (other != null)
			this.sendOpenNodes(other);
		
		
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
			String nextNode=null;
			Iterator<Couple<String, List<Couple<Observation, Integer>>>> iter=lobs.iterator();
			
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
			}else{
				//4) select next move.
				//4.1 If there exist one open node directly reachable, go for it,
				//	 otherwise choose one from the openNode list, compute the shortestPath and go for it
				if (nextNode==null){
					//no directly accessible openNode
					//chose one, compute the path and take the first step.
					nextNode=map.getMap().getShortestPath(myPosition, map.getOpenNodes().get(0)).get(0);
				}
				((AbstractDedaleAgent)this.myAgent).moveTo(nextNode);
			}

		}
	}
	
	private AID receivedBroadcast()
	{
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("BROADCAST-EXPLORATION"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null)
			return msg.getSender();
		
		return null;
	}
	
	private void sendOpenNodes(AID receiver)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setProtocol("REQUEST-REMOTE-OPEN-NODES");
		msg.setContent(this.map.getOpenNodesString());
		msg.addReceiver(receiver);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	private ArrayList<String> receiveOpenNodes()
	{
		MessageTemplate pattern = MessageTemplate.MatchProtocol("REQUEST-REMOTE-OPEN-NODES");
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null)
		{
		}
		
		return null;
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
		}
		
		return finished;
	}
}
