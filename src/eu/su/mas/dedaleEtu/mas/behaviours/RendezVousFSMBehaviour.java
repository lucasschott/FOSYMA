package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.broadcast.ReceiveBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.broadcast.SendBroadcastBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.InitiateTreeBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.MergeObservationsBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.PushSumBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.ReceiveTreeRequestAckBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.ReceiveTreeRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.ReceiveTreeTearDownBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.SendTreeRequestBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.SendTreeTearDownBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ExploreBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.HandleConflictBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.ReceiveMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploration.SendMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.FollowPathBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.movements.GoToBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.rendezVous.ComputeRendezVousPoint;
import eu.su.mas.dedaleEtu.mas.behaviours.rendezVous.EndRendezVousBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class RendezVousFSMBehaviour extends FSMBehaviour 
{
	private static final long serialVersionUID = -509109268524710516L;
	private DFAgentDescription dfd = new DFAgentDescription();
	private ServiceDescription sd = new ServiceDescription();
	
	public RendezVousFSMBehaviour(final AbstractMultiAgent myagent) {
		//definiton des etats
		
		super(myagent);
		sd.setType("ALL_AGENTS");
		dfd.addServices(sd);
		
		this.registerFirstState(new ComputeRendezVousPoint(myagent), "COMPUTE-RENDEZ-VOUS-POINT");
		this.registerState(new GoToBehaviour(myagent), "GO-TO-RENDEZ-VOUS");
		this.registerState(new SendBroadcastBehaviour(myagent, "RENDEZ-VOUS", "RENDEZ-VOUS"),"SEND-BROADCAST-RENDEZ-VOUS");
		this.registerState(new InitiateTreeBehaviour(myagent, "RENDEZ-VOUS-TREE"), "INITIATE-TREE-RENDEZ-VOUS");
		this.registerState(new ReceiveBroadcastBehaviour(myagent, "RENDEZ-VOUS", "RENDEZ-VOUS"),"RECEIVE-BROADCAST-RENDEZ-VOUS");
		this.registerState(new ReceiveBroadcastBehaviour(myagent, "EXPLORATION","EXPLORATION"),"RECEIVE-BROADCAST-EXPLORATION");
		this.registerState(new SendMapBehaviour(myagent), "SEND-MAP");
		this.registerState(new SendTreeRequestBehaviour(myagent, "RENDEZ-VOUS-TREE", "RENDEZ-VOUS"), "SEND-TREE-REQUEST");
		this.registerState(new ReceiveTreeRequestBehaviour(myagent), "RECEIVE-TREE-REQUEST");
		this.registerState(new ReceiveTreeRequestAckBehaviour(myagent), "RECEIVE-TREE-REQUEST-ACK");
		this.registerState(new PushSumBehaviour(myagent, "RENDEZ-VOUS-TREE", this.getMatchingAgents().length), "PUSH-SUM");
		this.registerLastState(new EndRendezVousBehaviour(myagent) , "END-RENDEZ-VOUS");
		
		// COMPUTE RENDEZ VOUS POINT -> GO TO RENDEZ VOUS POINT
		this.registerTransition("COMPUTE-RENDEZ-VOUS-POINT", "GO-TO-RENDEZ-VOUS", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("GO-TO-RENDEZ-VOUS", "INITIATE-TREE-RENDEZ-VOUS", FSMCodes.Events.END.ordinal());
		this.registerTransition("GO-TO-RENDEZ-VOUS", "SEND-BROADCAST-RENDEZ-VOUS", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("GO-TO-RENDEZ-VOUS", "SEND-BROADCAST-RENDEZ-VOUS", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("INITIATE-TREE-RENDEZ-VOUS", "SEND-BROADCAST-RENDEZ-VOUS", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("SEND-BROADCAST-RENDEZ-VOUS","RECEIVE-BROADCAST-RENDEZ-VOUS", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-BROADCAST-RENDEZ-VOUS","SEND-TREE-REQUEST", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("SEND-TREE-REQUEST", "RECEIVE-TREE-REQUEST-ACK", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("SEND-TREE-REQUEST", "RECEIVE-TREE-REQUEST", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-TREE-REQUEST", "RECEIVE-BROADCAST-RENDEZ-VOUS", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-TREE-REQUEST", "RECEIVE-BROADCAST-RENDEZ-VOUS", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-TREE-REQUEST-ACK", "RECEIVE-BROADCAST-EXPLORATION", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-TREE-REQUEST-ACK", "RECEIVE-BROADCAST-EXPLORATION", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-BROADCAST-RENDEZ-VOUS","RECEIVE-BROADCAST-EXPLORATION", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("RECEIVE-BROADCAST-EXPLORATION","SEND-MAP", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("RECEIVE-BROADCAST-EXPLORATION", "PUSH-SUM", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("SEND-MAP", "PUSH-SUM", FSMCodes.Events.SUCESS.ordinal());
		this.registerTransition("PUSH-SUM", "GO-TO-RENDEZ-VOUS", FSMCodes.Events.FAILURE.ordinal());
		this.registerTransition("PUSH-SUM", "END-RENDEZ-VOUS", FSMCodes.Events.SUCESS_CHILD.ordinal());
		this.registerTransition("PUSH-SUM", "END-RENDEZ-VOUS", FSMCodes.Events.SUCESS_PARENT.ordinal());
	}
	
	private DFAgentDescription[] getMatchingAgents() {
		DFAgentDescription[] result = {};
		try {
			result = DFService.search(this.myAgent, this.dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		return result;
	}
}