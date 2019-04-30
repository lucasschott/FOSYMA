package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndInterlockingBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 684833259104351411L;
	private AbstractMultiAgent _myAgent;

	public EndInterlockingBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		this._myAgent.setDestinationId(this._myAgent.getSavedDestinationId());
		this._myAgent.setPath(this._myAgent.getSavedPath());
	}

	@Override
	public int onEnd()
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
