package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.MapHandler;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.EndExplorationBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ExploreBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.HandleConflictBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ReceiveBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ReceiveMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.SendBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.SendMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.StartExplorationBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.rendezVous.ComputeRendezVousPoint;
import eu.su.mas.dedaleEtu.mas.behaviours.rendezVous.EndRendezVousBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class RendezVousFSMBehaviour extends FSMBehaviour 
{
	
	private static final long serialVersionUID = -509109268524710516L;
	
	public enum Events{
		SUCESS, FAILURE, END;
	}
	
	public RendezVousFSMBehaviour(final ExploreMultiAgent myagent) {
		//definiton des etats
		
		super(myagent);
		
		this.registerFirstState(new ComputeRendezVousPoint(myagent), "COMPUTE-RENDEZ-VOUS-POINT");
		this.registerLastState(new EndRendezVousBehaviour(myagent) , "END-RENDEZ-VOUS");
		
		//definition des transaction
		this.registerTransition("COMPUTE-RENDEZ-VOUS-POINT", "END-RENDEZ-VOUS", Events.SUCESS.ordinal());
		
	}
}