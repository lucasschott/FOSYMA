package eu.su.mas.dedaleEtu.mas.behaviours.InterlockingClient;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndClientInterlockingBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2328041753622437646L;

	public EndClientInterlockingBehaviour(AbstractMultiAgent _myAgent)
	{
		
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public int onEnd() 
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
