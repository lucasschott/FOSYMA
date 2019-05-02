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
	private boolean moved = false;

	public RandomInterlockingStrategyBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		String node = this._myAgent.getDestinationId();
		this.moved = false;
		randomMove(node);
	}
	
	public int onEnd()
	{
		if (this.moved)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}
	
	public void randomMove(String node)
	{
		if (this._myAgent.getMoveAllowed() == false)
			return;
		
		List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();
		
		Random r= new Random();
		int moveId=1+r.nextInt(lobs.size()-1);
		this.moved = this._myAgent.moveTo(lobs.get(moveId).getLeft());
		
		return;
	}
}
