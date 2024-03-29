package eu.su.mas.dedaleEtu.mas.behaviours.collect.LookForTank;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.InterlockingClient.ClientInterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.broadcast.ReceiveTankBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.interlocking.InterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.GoToBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class LookForTankFSMBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6015772291657735809L;

	public LookForTankFSMBehaviour(CollectMultiAgent myagent) {
		
	super(myagent);
	
	this.registerFirstState(new StartLookForTankBehaviour(myagent), "START-LOOK-FOR-TANK");
	this.registerState(new ReceiveTankBroadcastBehaviour(myagent), "RECEIVE-TANK-BROADCAST");
	this.registerState(new InterlockingFSMBehaviour(myagent), "INTERLOCKING");
	this.registerState(new ClientInterlockingFSMBehaviour(myagent), "INTERLOCKING-CLIENT");
	this.registerState(new GoToBehaviour(myagent), "GO-TO");
	this.registerLastState(new EndLookForTankBehaviour(myagent), "END-LOOK-FOR-TANK");
	// handle conflict
	
	this.registerTransition("START-LOOK-FOR-TANK", "INTERLOCKING-CLIENT", FSMCodes.Events.SUCESS.ordinal());
	this.registerTransition("INTERLOCKING-CLIENT", "RECEIVE-TANK-BROADCAST", FSMCodes.Events.SUCESS.ordinal());
	this.registerTransition("RECEIVE-TANK-BROADCAST", "GO-TO", FSMCodes.Events.FAILURE.ordinal());
	this.registerTransition("RECEIVE-TANK-BROADCAST", "END-LOOK-FOR-TANK", FSMCodes.Events.SUCESS.ordinal());
	this.registerTransition("GO-TO", "INTERLOCKING-CLIENT", FSMCodes.Events.SUCESS.ordinal());
	this.registerTransition("GO-TO", "INTERLOCKING", FSMCodes.Events.FAILURE.ordinal());
	this.registerTransition("GO-TO", "INTERLOCKING-CLIENT", FSMCodes.Events.END.ordinal());
	this.registerTransition("INTERLOCKING", "INTERLOCKING-CLIENT", FSMCodes.Events.SUCESS.ordinal());

	}
	
	public int onEnd() {
		System.out.println("FINISHED LOOK FOR TANK");
		this.reset();
		this.resetChildren();
		return FSMCodes.Events.SUCESS.ordinal();
	}
	
}
