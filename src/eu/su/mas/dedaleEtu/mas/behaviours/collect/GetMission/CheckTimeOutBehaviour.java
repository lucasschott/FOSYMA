package eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class CheckTimeOutBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6489435413854606896L;
	private Integer threshold;
	private AbstractMultiAgent _myAgent;
	
	public CheckTimeOutBehaviour(AbstractMultiAgent myagent, Integer threshold)
	{
		super(myagent);
		this.threshold = threshold;
		this._myAgent = myagent;
		this._myAgent.setTickCount(0);
	}
	
	@Override
	public void action() 
	{
		this._myAgent.incrementTickCount();
	}

	@Override
	public int onEnd() 
	{
		if (this._myAgent.getTickCount() >= this.threshold)
			return FSMCodes.Events.FAILURE.ordinal();
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
