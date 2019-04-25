package eu.su.mas.dedaleEtu.mas.behaviours.collect.TransferSilo;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class TransferSiloBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5142332781220198127L;
	private CollectMultiAgent _myAgent;

	public TransferSiloBehaviour(CollectMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() {
		System.out.println("Backpack before : " + this._myAgent.getBackPackFreeSpace());
		System.out.println(this.myAgent.getLocalName()+" - The agent tries to transfer is load into the Silo (if reachable); succes ? : " + ((AbstractDedaleAgent)this.myAgent).emptyMyBackPack("Tanker1"));
		System.out.println("Backpack after : " + this._myAgent.getBackPackFreeSpace());
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
