package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SendBroadcastBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9116585529533332376L;

	private DFAgentDescription dfd = new DFAgentDescription();
	private ServiceDescription sd = new ServiceDescription();
	
	public SendBroadcastBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
		sd.setType("EXPLORATION");
		dfd.addServices(sd);
	}

	@Override
	public void action() {
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		DFAgentDescription[] result = {};
		
		System.out.println(this.getClass().getName());
		result = this.getExplorationAgents();
		
		ACLMessage msg= this.buildBroadcastMessage(result);
		
		if (myPosition!=""){
			msg.setContent(myPosition);
			((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
		}
	}
	
	private ACLMessage buildBroadcastMessage(DFAgentDescription[] result) {
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("BROADCAST-EXPLORATION");
		
		for (DFAgentDescription dsc : result)
		{
			if (this.myAgent.getAID().toString() != dsc.getName().toString())
				msg.addReceiver(dsc.getName());
		}
		
		return msg;
	}
	
	private DFAgentDescription[] getExplorationAgents() {
		DFAgentDescription[] result = {};
		try {
			result = DFService.search(this.myAgent, this.dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int onEnd() {
		return ExploMultiFSMBehaviour.Events.SUCESS.ordinal();
	}

}
