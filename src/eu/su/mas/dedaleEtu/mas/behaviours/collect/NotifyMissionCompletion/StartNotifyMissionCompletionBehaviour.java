package eu.su.mas.dedaleEtu.mas.behaviours.collect.NotifyMissionCompletion;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class StartNotifyMissionCompletionBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3373776844919296743L;
	private CollectMultiAgent _myAgent;
	
	public StartNotifyMissionCompletionBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

 	public void action() {
	}

	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
