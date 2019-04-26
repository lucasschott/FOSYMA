package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.FSMBehaviour;

public class InterlockingFSMBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 522392132862014027L;
	private AbstractMultiAgent _myAgent;
	
	public InterlockingFSMBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	public int onEnd() {
		this.resetChildren();
		this.reset();
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
