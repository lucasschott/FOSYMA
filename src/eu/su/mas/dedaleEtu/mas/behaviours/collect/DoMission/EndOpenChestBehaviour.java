package eu.su.mas.dedaleEtu.mas.behaviours.collect.DoMission;

import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class EndOpenChestBehaviour extends OneShotBehaviour 
{

	private static final long serialVersionUID = 3422325813735148617L;

	public EndOpenChestBehaviour(CollectMultiAgent myagent)
	{
		super(myagent);
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
