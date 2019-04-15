package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class HandleConflictBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = -6969057364841117291L;

	public HandleConflictBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		((AbstractMultiAgent)this.myAgent).map.shuffleOpenNodes();
	}

	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
