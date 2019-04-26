package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class ResetKnowledgeBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1749605328699214554L;
	private ExploreMultiAgent _myAgent;

	public ResetKnowledgeBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		this._myAgent.clearTreasureMap();
	}

	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
