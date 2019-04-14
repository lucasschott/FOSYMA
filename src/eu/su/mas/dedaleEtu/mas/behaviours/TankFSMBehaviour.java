package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.EndTankBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.tank.StartTankBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class TankFSMBehaviour  extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8540175418322936323L;

	public TankFSMBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		
		this.registerFirstState(new StartTankBehaviour(myagent), "START-TANK");
		this.registerLastState(new EndTankBehaviour(myagent), "END-TANK");
		
		this.registerTransition("START-TANK", "END-TANK", FSMCodes.Events.SUCESS.ordinal());
	}
}
