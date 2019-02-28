package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class HandleConflictBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = -6969057364841117291L;

	public HandleConflictBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getName());
		((ExploreMultiAgent)this.myAgent).map.shuffleOpenNodes();
	}

	public int onEnd() {
		return ExploMultiFSMBehaviour.Events.SUCESS.ordinal();
	}

}
