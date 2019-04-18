package eu.su.mas.dedaleEtu.mas.behaviours.collect.TransferSilo;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class StartTransferSiloBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5387246213016085650L;

	public StartTransferSiloBehaviour(CollectMultiAgent myagent) {
		super(myagent);
	}
	
	@Override
	public void action() {
	}

	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
