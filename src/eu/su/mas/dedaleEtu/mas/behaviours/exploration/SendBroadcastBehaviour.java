package eu.su.mas.dedaleEtu.mas.behaviours.exploration;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class SendBroadcastBehaviour extends OneShotBehaviour {

	public SendBroadcastBehaviour(ExploreMultiAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		DFAgentDescription[] result = {};
		
		try {
			result = DFService.search(this.myAgent, ((ExploreMultiAgent)this.myAgent).desc);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		//A message is defined by : a performative, a sender, a set of receivers, (a protocol),(a content (and/or contentOBject))
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("BROADCAST-EXPLORATION");
			
		for (DFAgentDescription dsc : result)
		{
			if (this.myAgent.getAID().toString() != dsc.getName().toString())
				msg.addReceiver(dsc.getName());
		}
		
		
		if (myPosition!=""){
			msg.setContent("Broadcasting from : " + myPosition);

			//Mandatory to use this method (it takes into account the environment to decide if someone is reachable or not)
			((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
		}
	}
	
	public int onEnd() {
		return ExploMultiFSMBehaviour.Events.SUCESS.ordinal();
	}

}
