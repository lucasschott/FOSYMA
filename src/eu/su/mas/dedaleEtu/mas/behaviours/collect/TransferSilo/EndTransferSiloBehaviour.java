package eu.su.mas.dedaleEtu.mas.behaviours.collect.TransferSilo;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndTransferSiloBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4946609952041175779L;
	
	public EndTransferSiloBehaviour(CollectMultiAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
