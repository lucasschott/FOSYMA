package eu.su.mas.dedaleEtu.mas.behaviours.explore;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.interlocking.InterlockingFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.GoToBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class AssistFSMBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2978555694584966099L;
	private ExploreMultiAgent _myAgent;

	public AssistFSMBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		
		this.registerFirstState(new GoToBehaviour(myagent), "GO-TO");
		this.registerState(new ReceiveAssistDisbandBehaviour(myagent), "RECEIVE-DISBAND");
		this.registerState(new InterlockingFSMBehaviour(myagent), "INTERLOCKING");
		this.registerState(new EndAssistBehaviour(myagent), "END-ASSIST");
		
		this.registerTransition("GO-TO", "RECEIVE-DISBAND", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GO-TO", "INTERLOCKING", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("GO-TO", "RECEIVE-DISBAND", FSMCodes.Events.END.ordinal());
		this.registerTransition("RECEIVE-DISBAND", "GO-TO", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-DISBAND", "END-ASSIST", FSMCodes.Events.SUCESS.ordinal());
	}
	
	public int onEnd()
	{
		this.resetChildren();
		this.reset();
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
