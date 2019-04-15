package eu.su.mas.dedaleEtu.mas.behaviours.collect.GetMission;

import java.io.IOException;
import java.util.HashMap;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.utils.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SendMissionRequestBehaviour extends OneShotBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2612918684506244537L;
	
	private DFAgentDescription dfd = new DFAgentDescription();
	private ServiceDescription sd = new ServiceDescription();
	private AbstractMultiAgent _myAgent;
	
	public SendMissionRequestBehaviour(AbstractMultiAgent myagent) {
		super(myagent);
		
		this._myAgent = myagent;
		sd.setType("TANK");
		dfd.addServices(sd);
	}

	@Override
	public void action() {
		DFAgentDescription[] result = {};
		
		result = this._myAgent.getMatchingAgents("TANK");
		
		Agent payload = new Agent();
		
		payload.setAID(this._myAgent.getAID());
		payload.setBackPackCapacity(this._myAgent.getBackPackFreeSpace());
		
		ACLMessage msg = this.buildMessage(result);
		
		try {
			msg.setContentObject(payload);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	private ACLMessage buildMessage(DFAgentDescription[] result) {
		ACLMessage msg=new ACLMessage(ACLMessage.REQUEST);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("REQUEST-MISSION");
		
		for (DFAgentDescription dsc : result)
		{
			if (!this.myAgent.getAID().toString().equals(dsc.getName().toString()))
				msg.addReceiver(dsc.getName());
			System.out.println("SENDING REQUEST TO : " + dsc.getName());
		}
		
		return msg;
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
