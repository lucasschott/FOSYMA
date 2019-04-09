package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import java.util.ArrayList;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.utils.Serializer;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SendMapBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1076955317152012381L;
	private AbstractMultiAgent _myAgent;
	private DFAgentDescription dfd = new DFAgentDescription();
	private ServiceDescription sd = new ServiceDescription();
	
	public SendMapBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		this._myAgent = myagent;
		sd.setType("EXPLORATION");
		dfd.addServices(sd);
	}

	@Override
	public void action() {
		System.out.println(this.getClass().getName());
		ArrayList<ArrayList<String>> closedNodes = this.getClosedNodes();
		this.sendClosedNodes(closedNodes);
		System.out.println("SEND MAP : " + this._myAgent.getLocalName() );
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
	
	private ArrayList<ArrayList<String>> getClosedNodes()
	{
		ArrayList<ArrayList<String>> closedNodes = new ArrayList<>();
		
			for(String node: this._myAgent.map.getClosedNodes()) {
				ArrayList<String> nodeDescription = new ArrayList<String>();
				nodeDescription.add(node);
				nodeDescription.addAll(this._myAgent.map.getMap().getNeighbours(node));
				closedNodes.add(nodeDescription);
		}
		
		return closedNodes;
	}
	
	private void sendClosedNodes(ArrayList<ArrayList<String>> closedNodes)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		String content;
		DFAgentDescription[] result = {};
		
		result = this.getExplorationAgents();
		
		msg.setProtocol("INFORM-CLOSED-NODES");
	
		for (DFAgentDescription dsc : result)
		{
			if (!this.myAgent.getAID().toString().equals(dsc.getName().toString()))
				msg.addReceiver(dsc.getName());
		}
		
		msg.setSender(this.myAgent.getAID());
		content = Serializer.serialize(closedNodes);
		msg.setContent(content);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	private DFAgentDescription[] getExplorationAgents() {
		DFAgentDescription[] result = {};
		try {
			result = DFService.search(this.myAgent, this.dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		System.out.println("CURRENTLY : " + result.length + " EXPLORATION AGENTS FOUND");
		return result;
	}
}
