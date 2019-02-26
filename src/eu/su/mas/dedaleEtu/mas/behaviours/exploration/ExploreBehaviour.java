package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class ExploreBehaviour extends OneShotBehaviour {

	public ExploreBehaviour(final ExploreMultiAgent myagent) {
		super(myagent);
	}
	
	public void setup() {
		
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub

	}
	
	public int onEnd() {
		return ExploMultiFSMBehaviour.Events.SUCESS.ordinal();
	}	
}
