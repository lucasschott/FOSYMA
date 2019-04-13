package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.MergeObservationsBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.ReceiveTreeTearDownBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.SendTreeTearDownBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.mergeObservations.EndMergeObservationsBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class MergeObservationsFSMBehaviour  extends FSMBehaviour  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5215327251763992388L;

	public MergeObservationsFSMBehaviour(final AbstractMultiAgent myagent) {
		//definiton des etats
		
	super(myagent);
		
	this.registerFirstState(new MergeObservationsBehaviour(myagent, "RENDEZ-VOUS-TREE"), "MERGE-OBSERVATIONS");
	this.registerState(new SendTreeTearDownBehaviour(myagent, "RENDEZ-VOUS-TREE"), "SEND-TREE-TEAR-DOWN");
	this.registerState(new ReceiveTreeTearDownBehaviour(myagent, "RENDEZ-VOUS-TREE"), "RECEIVE-TREE-TEAR-DOWN");
	this.registerLastState(new EndMergeObservationsBehaviour(myagent) , "END-MERGE-OBSERVATIONS");
	
	// COMPUTE RENDEZ VOUS POINT -> GO TO RENDEZ VOUS POINT
	this.registerTransition("MERGE-OBSERVATIONS", "MERGE-OBSERVATIONS", FSMCodes.Events.FAILURE.ordinal());
	this.registerTransition("MERGE-OBSERVATIONS", "SEND-TREE-TEAR-DOWN", FSMCodes.Events.SUCESS_PARENT.ordinal());
	this.registerTransition("MERGE-OBSERVATIONS", "RECEIVE-TREE-TEAR-DOWN", FSMCodes.Events.SUCESS_CHILD.ordinal());
	this.registerTransition("SEND-TREE-TEAR-DOWN", "END-MERGE-OBSERVATIONS", FSMCodes.Events.SUCESS.ordinal());
	this.registerTransition("RECEIVE-TREE-TEAR-DOWN", "RECEIVE-TREE-TEAR-DOWN", FSMCodes.Events.FAILURE.ordinal());
	this.registerTransition("RECEIVE-TREE-TEAR-DOWN", "SEND-TREE-TEAR-DOWN", FSMCodes.Events.SUCESS.ordinal());
	}
}