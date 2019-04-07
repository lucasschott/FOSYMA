package eu.su.mas.dedaleEtu.mas.behaviours.rendezVous;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.RendezVousFSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class CheckExplorationEndConditionBehaviour extends OneShotBehaviour{
	/**
	 * 
	 */
	private static final long serialVersionUID = -626168446823784736L;
	private boolean finished = false;
	private ExploreMultiAgent _myAgent;
	private String treeId;

	public CheckExplorationEndConditionBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		this.treeId = "RENDEZ-VOUS-TREE";
	}

	public void action() 
	{
		if (this._myAgent.treeExist(this.treeId) && this._myAgent.getTree(treeId).getIsRoot() == true) 
		{
		
		}
	}
	
	public int onEnd() {
		if (finished == true)
			return FSMCodes.Events.SUCESS.ordinal();
		
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
