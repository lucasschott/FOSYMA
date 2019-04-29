package eu.su.mas.dedaleEtu.mas.behaviours.movements;

import java.util.ArrayList;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class FollowPathBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 7295849474602290463L;
	private boolean finished = false;
	private AbstractMultiAgent _myAgent;
	
	public FollowPathBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
	}

	@Override
	public void action() {
	
		finished = false;
		
		if (this._myAgent.getMoveAllowed() == false)
			return;
		
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		ArrayList<String> path = this._myAgent.getPath();
		boolean moved;
		
		this._myAgent.doWait(500);
		
		if (path.size() > 0) 
		{
			moved = this._myAgent.moveTo(path.get(0));
			
			if (moved) {
				path.remove(0);
				this._myAgent.setPath(path);
			}
		}
		
		if (myPosition.equals(_myAgent.getDestinationId())) {
			this.finished = true;
		}
		
		this._myAgent.updateTreasureMap();
	}
	
	public int onEnd() {
		if (this.finished) {
			return FSMCodes.Events.SUCESS.ordinal();
		}
		return FSMCodes.Events.FAILURE.ordinal();
	}
}