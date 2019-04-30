package eu.su.mas.dedaleEtu.mas.behaviours.movements;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class GoToBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 7295849474602290463L;
	private boolean moved = false;
	private AbstractMultiAgent _myagent;
	
	public GoToBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		this._myagent = myagent;
	}

	@Override
	public void action() {
		
		this.moved = false;
		
		if (this._myagent.getMoveAllowed() == false)
			return;
		
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		
		this._myagent.doWait(500);
		
		System.out.println("GOTO " + _myagent.getLocalName());
		
		if (myPosition!=null && _myagent.getDestinationId() != null &&  !myPosition.equals(_myagent.getDestinationId()))
		{	
			List<String> shortestPath = _myagent.map.getMap().getShortestPath(myPosition, _myagent.getDestinationId());
					
			if (shortestPath != null && shortestPath.size() > 0) {
				moved = _myagent.moveTo(shortestPath.get(0));
			}
						
		}
		
		this._myagent.updateTreasureMap();
	}
	
	public int onEnd() 
	{
		if (this._myagent.getCurrentPosition().equals(this._myagent.getDestinationId()))
			return FSMCodes.Events.END.ordinal();
		
		if (this.moved)
			return FSMCodes.Events.SUCESS.ordinal();
		
		return FSMCodes.Events.FAILURE.ordinal();
	}
}