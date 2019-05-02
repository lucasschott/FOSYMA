package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class GetNodeBehaviour extends OneShotBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6163824638432823342L;
	private AbstractMultiAgent _myAgent;
	private boolean moved = false;
	
	public GetNodeBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		String node = this._myAgent.getCurrentConflict().getConflictingNode();
		moved = this._myAgent.moveTo(node);
	}
	
	public int onEnd()
	{
		if (this.moved)
			return FSMCodes.Events.SUCESS.ordinal();
		return FSMCodes.Events.FAILURE.ordinal();
	}

}
