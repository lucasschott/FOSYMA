package eu.su.mas.dedaleEtu.mas.behaviours.collect.NotifyMissionCompletion;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.FSMBehaviour;

public class NotifyMissionCompletionFSMBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4274135992335616378L;
	private CollectMultiAgent _myAgent;
	
	public NotifyMissionCompletionFSMBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		
		this.registerFirstState(new StartNotifyMissionCompletionBehaviour(this._myAgent), "START-NOTIFY-COMPLETION");
		this.registerState(new SendMissionCompletionNotificationBehaviour(this._myAgent), "SEND-NOTIFY-COMPLETION");
		this.registerState(new ReceiveMissionCompletionNotificationACKBehaviour(this._myAgent), "RECEIVE-NOTIFY-COMPLETION-ACK");
		this.registerLastState(new EndNotifyMissionCompletionBehaviour(this._myAgent), "END-NOTIFY-COMPLETION");
	}

	public int onEnd() {
		this.resetChildren();
		this.reset();
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
