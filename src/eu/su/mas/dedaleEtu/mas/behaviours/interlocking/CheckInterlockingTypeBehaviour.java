package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class CheckInterlockingTypeBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8020596227535247137L;
	private AbstractMultiAgent _myAgent;
	
	public CheckInterlockingTypeBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action()
	{
		String node = this.getRequiredNode();
	}
	
	public String getRequiredNode()
	{
		if (this._myAgent.getPath() != null) {
			return this._myAgent.getPath().get(0);
		}
		
		return this._myAgent.getDestinationId();
	}
	
	public void getNodeObservation(String node) 
	{
	}
	

	public int onEnd() 
	{
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
