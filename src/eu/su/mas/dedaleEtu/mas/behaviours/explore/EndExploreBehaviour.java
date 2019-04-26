package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndExploreBehaviour extends OneShotBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4957012324573374822L;
	private ExploreMultiAgent _myAgent;

	public EndExploreBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		this._myAgent.deregisterService("EXPLORE");
	}

	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
