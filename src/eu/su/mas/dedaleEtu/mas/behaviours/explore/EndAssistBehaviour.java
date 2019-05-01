package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndAssistBehaviour extends OneShotBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4105561273336648864L;
	private ExploreMultiAgent _myAgent;
	
	public EndAssistBehaviour(ExploreMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() 
	{
		this._myAgent.setCurrentAssist(null);
		
		// Flush message queue
		while (this._myAgent.receive() != null)
		{
			
		}
	}

	@Override
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
