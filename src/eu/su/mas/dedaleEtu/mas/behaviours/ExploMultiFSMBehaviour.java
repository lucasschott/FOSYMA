package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.MapHandler;
import eu.su.mas.dedaleEtu.mas.behaviours.broadcast.ReceiveBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.broadcast.SendBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.EndExplorationBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ExploreBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.HandleConflictBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ReceiveMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.SendMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.StartExplorationBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class ExploMultiFSMBehaviour extends FSMBehaviour 
{
	
	private static final long serialVersionUID = -509109268524710516L;
	
	public ExploMultiFSMBehaviour(final AbstractMultiAgent myagent) {
		//definiton des etats
		
		super(myagent);
		
		this.registerFirstState(new StartExplorationBehaviour(myagent), "START-EXPLORATION");
		this.registerState	(new ExploreBehaviour(myagent),"EXPLORE");
		this.registerState(new SendBroadcastBehaviour(myagent, "EXPLORATION", "EXPLORATION"),"SEND-BROADCAST");
		this.registerState(new HandleConflictBehaviour(myagent), "HANDLE-CONFLICT");
		this.registerState(new ReceiveBroadcastBehaviour(myagent, "EXPLORATION", "EXPLORATION"),"RECEIVE-BROADCAST");
		this.registerState(new SendMapBehaviour(myagent), "SEND-MAP");
		this.registerState(new ReceiveMapBehaviour(myagent),"RECEIVE-MAP");
		this.registerLastState(new EndExplorationBehaviour(myagent) , "END-EXPLORATION");
		
		//definition des transaction
		this.registerTransition("START-EXPLORATION", "EXPLORE", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("EXPLORE","SEND-BROADCAST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("EXPLORE", "HANDLE-CONFLICT", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("EXPLORE", "END-EXPLORATION", FSMCodes.Events.END.ordinal());
		
		this.registerTransition("HANDLE-CONFLICT","SEND-BROADCAST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("SEND-BROADCAST","RECEIVE-BROADCAST",FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-BROADCAST","SEND-MAP",FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-BROADCAST", "RECEIVE-MAP", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("SEND-MAP", "RECEIVE-MAP", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-MAP", "EXPLORE", FSMCodes.Events.SUCESS.ordinal());
	}
}
