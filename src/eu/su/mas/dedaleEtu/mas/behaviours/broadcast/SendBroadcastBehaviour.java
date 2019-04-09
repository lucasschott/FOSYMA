package eu.su.mas.dedaleEtu.mas.behaviours.broadcast;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
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
	private String service;
	
	public SendBroadcastBehaviour(AbstractMultiAgent myagent, String service) {
		super(myagent);
		sd.setType(service);
		dfd.addServices(sd);
		this.service = service;
	}

	@Override
	public void action() {
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		DFAgentDescription[] result = {};
		
		result = this.getMatchingAgents();
		
		System.out.println("Send broadcast : " + this.service);
		
		ACLMessage msg= this.buildBroadcastMessage(result);
		
		if (myPosition!=""){
			msg.setContent(myPosition);
			((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
		}
	}
	
	private ACLMessage buildBroadcastMessage(DFAgentDescription[] result) {
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol(service);
		
		System.out.println("Result size : " + result.length);
		
		for (DFAgentDescription dsc : result)
		{
			if (!this.myAgent.getAID().toString().equals(dsc.getName().toString()))
				msg.addReceiver(dsc.getName());
				System.out.println(dsc.getName().toString());
		}
		
		return msg;
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
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}

}
