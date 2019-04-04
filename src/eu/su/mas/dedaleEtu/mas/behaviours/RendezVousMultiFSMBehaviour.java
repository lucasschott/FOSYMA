package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.MapHandler;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.HandleTreeRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.SendTreeRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.WaitTreeRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.EndExplorationBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ExploreBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.HandleConflictBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ReceiveBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ReceiveMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.SendBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.SendMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.StartExplorationBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.GoToBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.rendezVous.ComputeRendezVousPoint;
import eu.su.mas.dedaleEtu.mas.behaviours.rendezVous.GoToRendezVous;
import jade.core.behaviours.FSMBehaviour;

public class RendezVousMultiFSMBehaviour extends FSMBehaviour 
{
	
	private static final long serialVersionUID = -509109268524710516L;
	
	public enum Events{
		SUCESS, FAILURE, END;
	}
	
	public RendezVousMultiFSMBehaviour(final ExploreMultiAgent myagent) {
		//definiton des etats
		
		super(myagent);
		
		this.registerFirstState(new ComputeRendezVousPoint(myagent), "COMPUTE-RENDEZ-VOUS");
		this.registerState(new ReceiveBroadcastBehaviour(myagent),"RECEIVE-BROADCAST");
		this.registerState(new SendMapBehaviour(myagent), "SEND-MAP");
		this.registerState(new GoToBehaviour(myagent), "GO-TO-RENDEZ-VOUS-POINT");
		this.registerState(new WaitTreeRequestBehaviour(myagent), "");
		this.registerState(new SendTreeRequestBehaviour(myagent));
		this.registerState(new HandleTreeRequestBehaviour(myagent));
		
		//definition des transaction
		this.registerTransition("START-EXPLORATION", "EXPLORE", Events.SUCESS.ordinal());
		this.registerTransition("EXPLORE","SEND-BROADCAST", Events.SUCESS.ordinal());
		this.registerTransition("EXPLORE", "HANDLE-CONFLICT", Events.FAILURE.ordinal());
		this.registerTransition("EXPLORE", "END-EXPLORATION", Events.END.ordinal());
		
		this.registerTransition("HANDLE-CONFLICT","SEND-BROADCAST", Events.SUCESS.ordinal());
		this.registerTransition("SEND-BROADCAST","RECEIVE-BROADCAST",Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-BROADCAST","SEND-MAP",Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-BROADCAST", "RECEIVE-MAP", Events.FAILURE.ordinal());
		this.registerTransition("SEND-MAP", "RECEIVE-MAP", Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MAP", "EXPLORE", Events.SUCESS.ordinal());

	}
}