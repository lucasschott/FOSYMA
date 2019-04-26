package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import jade.core.behaviours.OneShotBehaviour;

public class RandomWalkBehaviour extends OneShotBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -357919544019754344L;
	private ExploreMultiAgent _myAgent;
	
	public RandomWalkBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() 
	{
		updateTreasureMap();
		randomWalk();
	}

	public void updateTreasureMap()
	{
		//List of observable from the agent's current position
		List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();
		Iterator<Couple<String, List<Couple<Observation, Integer>>>> iter=lobs.iterator();
		
		// Update treasures map
		for (int index = 0; index < lobs.size(); index++)
		{
			String node = lobs.get(index).getLeft();
			
			for (Couple<Observation, Integer> obs: lobs.get(index).getRight()) {
				switch(obs.getLeft()) {
				
				case DIAMOND:
				case GOLD:
					this._myAgent.updateTreasuresMap(node, obs.getLeft(), obs.getRight());
				default:
					break;
				}
			}
		}
	}
	
	public void randomWalk()
	{
		String currentPosition = this._myAgent.getCurrentPosition();
		List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();
		Random r= new Random();
		int moveId=1+r.nextInt(lobs.size()-1);//removing the current position from the list of target, not necessary as to stay is an action but allow quicker random move
		
		if (lobs.size() - 1 > 1)
		{
			while (lobs.get(moveId).getLeft().equals(this._myAgent.getPreviousNode()))
				moveId=1+r.nextInt(lobs.size()-1);
		}
		
		this._myAgent.moveTo(lobs.get(moveId).getLeft());
		this._myAgent.setPreviousNode(currentPosition);
	}
}
