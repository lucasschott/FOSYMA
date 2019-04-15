package eu.su.mas.dedaleEtu.mas.behaviours.collect.LookForTank;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndLookForTankBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5329129728295067973L;

	public EndLookForTankBehaviour(CollectMultiAgent myagent) {
		super(myagent);
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub

	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
