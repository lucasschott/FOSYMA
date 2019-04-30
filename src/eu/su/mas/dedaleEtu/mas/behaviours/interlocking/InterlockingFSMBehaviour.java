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
		
		this.registerFirstState(new StartInterlockingBehaviour(myagent), "START-INTERLOCKING");
		this.registerState(new CheckInterlockingTypeBehaviour(myagent), "CHECK-INTERLOCKING-TYPE");
		this.registerState(new RandomInterlockingStrategyBehaviour(myagent), "RANDOM-INTERLOCKING");
		this.registerLastState(new EndInterlockingBehaviour(myagent), "END-INTERLOCKING");
		
		this.registerTransition("START-INTERLOCKING", "CHECK-INTERLOCKING-TYPE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("CHECK-INTERLOCKING-TYPE", "RANDOM-INTERLOCKING", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RANDOM-INTERLOCKING", "END-INTERLOCKING", FSMCodes.Events.SUCESS.ordinal());
	}
	
	public int onEnd() {
		this.resetChildren();
		this.reset();
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
