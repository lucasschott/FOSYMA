package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class ExploreBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 8703772007577156629L;
	private AbstractMultiAgent _myAgent;
	private boolean finished = false;
	private boolean moved = false;

	public ExploreBehaviour(final AbstractMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	public void setup() {
	}
	
	@Override
	public void action() {
		//0) Retrieve the current position
		System.out.println(this.getClass().getName() + " " + this._myAgent.getLocalName());
		System.out.println(this._myAgent.map.getClosedNodes());
		System.out.println(this._myAgent.map.getOpenNodes().size());
		
				String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
			
				if (myPosition!=null){
					
					//List of observable from the agent's current position
					List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition

					/**
					 * Just added here to let you see what the agent is doing, otherwise he will be too quick
					 */
					try {
						this.myAgent.doWait(200);
					} catch (Exception e) {
						e.printStackTrace();
					}

					//1) remove the current node from openlist and add it to closedNodes.
					this._myAgent.map.addClosed(myPosition);
					this._myAgent.map.removeOpen(myPosition);
					this._myAgent.map.addNode(myPosition);
					
					//2) get the surrounding nodes and, if not in closedNodes, add them to open nodes.
					Iterator<Couple<String, List<Couple<Observation, Integer>>>> iter=lobs.iterator();
					
					String nextNode = null;
					
					while(iter.hasNext())
					{
						String nodeId=iter.next().getLeft();
						if (!this._myAgent.map.isClosed(nodeId))
						{
							if (!this._myAgent.map.isOpen(nodeId))
							{
								this._myAgent.map.addOpen(nodeId);
								this._myAgent.map.addNode(nodeId);
								this._myAgent.map.addEdge(myPosition, nodeId);
							}
							else
							{
								//the node exist, but not necessarily the edge
								this._myAgent.map.addEdge(myPosition, nodeId);
							}
							if (nextNode==null)
								nextNode=nodeId;
						}
					}

					//3) while openNodes is not empty, continues.
					if (this._myAgent.map.getOpenNodes().isEmpty()){
						//Explo finished
						finished=true;
						System.out.println("Exploration successufully done, behaviour removed.");
					}
					
					else{
						//4) select next move.
						//4.1 If there exist one open node directly reachable, go for it,
						//	 otherwise choose one from the openNode list, compute the shortestPath and go for it
						if (nextNode==null){
							//no directly accessible openNode
							//chose one, compute the path and take the first step.
							try {
								List<String> shortestPath = this._myAgent.map.getMap().getShortestPath(myPosition, this._myAgent.map.getOpenNodes().get(0));
								
								if (shortestPath == null) {
									nextNode = null;
								}

								else
									nextNode=this._myAgent.map.getMap().getShortestPath(myPosition, this._myAgent.map.getOpenNodes().get(0)).get(0);	
								
							}
							catch (NoSuchElementException ex) {
								System.out.println("catched");
							}
							
						}
						
						if ((this.moved = ((AbstractDedaleAgent)this.myAgent).moveTo(nextNode)) == false) {
							//Random move from the current position
							Random r = new Random();
							int moveId=1+r.nextInt(lobs.size()-1);//removing the current position from the list of target, not necessary as to stay is an action but allow quicker random move
							//The move action (if any) should be the last action of your behaviour
							((AbstractDedaleAgent)this.myAgent).moveTo(lobs.get(moveId).getLeft());
							
					}

				}
	}
	}
	
	public int onEnd() {
		
		if (this.finished)
			return FSMCodes.Events.END.ordinal();
		
		if (this.moved)
			return FSMCodes.Events.SUCESS.ordinal();
		
		return FSMCodes.Events.FAILURE.ordinal();
	}	
}
