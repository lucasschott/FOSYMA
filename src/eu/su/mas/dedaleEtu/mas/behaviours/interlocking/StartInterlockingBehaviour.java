package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class StartInterlockingBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8641525653996525290L;
	private AbstractMultiAgent _myAgent;

	public StartInterlockingBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this._myAgent.setSavedDestinationId(this._myAgent.getDestinationId());
		this._myAgent.setSavedPath(this._myAgent.getPath());
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
