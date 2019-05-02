package eu.su.mas.dedaleEtu.mas.behaviours.InterlockingClient;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission.CheckTimeOutBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.interlocking.InterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.interlocking.RandomInterlockingStrategyBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class ClientInterlockingFSMBehaviour extends FSMBehaviour 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7319796445574512377L;
	private AbstractMultiAgent _myAgent;

	public ClientInterlockingFSMBehaviour(AbstractMultiAgent myagent)
	{
		this.registerFirstState(new ReceiveNodeRequestBehaviour(myagent), "RECEIVE-NODE-REQUEST");
		this.registerLastState(new EndClientInterlockingBehaviour(myagent), "END-CLIENT-INTERLOCKING");
		this.registerState(new PopConflictBehaviour(myagent), "POP-CONFLICT");
		this.registerState(new InterlockingFSMBehaviour(myagent), "INTERLOCKING");
		this.registerState(new CheckTimeOutBehaviour(myagent, 30), "WAIT");
		this.registerState(new NotifyNodeAvailabilityBehaviour(myagent), "NOTIFY-NODE-AVAILABILITY");
		this.registerState(new RandomInterlockingStrategyBehaviour(myagent), "RANDOM-INTERLOCKING-STRATEGY");
		
		this.registerTransition("RECEIVE-NODE-REQUEST", "END-CLIENT-INTERLOCKING", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-NODE-REQUEST", "RANDOM-INTERLOCKING-STRATEGY", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RANDOM-INTERLOCKING-STRATEGY", "NOTIFY-NODE-AVAILABILITY", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("NOTIFY-NODE-AVAILABILITY", "WAIT", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("WAIT", "POP-CONFLICT", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("WAIT", "WAIT", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RANDOM-INTERLOCKING-STRATEGY", "RANDOM-INTERLOCKING-STRATEGY", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("INTERLOCKING", "POP-CONFLICT", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("POP-CONFLICT", "END-CLIENT-INTERLOCKING", FSMCodes.Events.SUCESS.ordinal());
	}
	
	public int onEnd()
	{
		this.resetChildren();
		this.reset();
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
