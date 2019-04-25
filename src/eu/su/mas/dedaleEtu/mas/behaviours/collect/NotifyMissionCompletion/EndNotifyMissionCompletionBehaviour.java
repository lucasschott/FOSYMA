package eu.su.mas.dedaleEtu.mas.behaviours.collect.NotifyMissionCompletion;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndNotifyMissionCompletionBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 811115560270540374L;
	private CollectMultiAgent _myAgent;
	
	public EndNotifyMissionCompletionBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	@Override
	public void action() {
		this._myAgent.setCurrentMission(null);
	}

	@Override
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
