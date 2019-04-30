package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class RandomInterlockingStrategyBehaviour extends OneShotBehaviour 
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3862582056157912733L;
	private AbstractMultiAgent _myAgent;

	public RandomInterlockingStrategyBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		String node = this._myAgent.getDestinationId();
		randomMove(node);
	}
	
	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
	
	public void randomMove(String node)
	{
		List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();
		Random r= new Random();
		int moveId=1+r.nextInt(lobs.size()-1);//removing the current position from the list of target, not necessary as to stay is an action but allow quicker random move
		
		this._myAgent.moveTo(lobs.get(moveId).getLeft());
	}
}
