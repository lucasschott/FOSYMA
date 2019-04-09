package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class InitiateTreeBehaviour extends OneShotBehaviour{
	private static final long serialVersionUID = 7295849474602290463L;
	private AbstractMultiAgent _myAgent;
	private String treeId;
		
	public InitiateTreeBehaviour(AbstractMultiAgent myagent, String treeId) {
		super(myagent);
		this._myAgent = myagent;
		this.treeId = treeId;
	}

	@Override
	public void action() {
		if (_myAgent.treeExist(this.treeId) == false) {
			System.out.println("CREATE TREEE");
			_myAgent.addTree(this.treeId, null, true);
		}
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
