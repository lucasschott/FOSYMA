package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import jade.core.behaviours.OneShotBehaviour;

public class EndDoMissionBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2868138179700584237L;
	private CollectMultiAgent _myAgent;
	
	public EndDoMissionBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
	}

}
