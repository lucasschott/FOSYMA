package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import jade.domain.AMSService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.DFService;

/**
 * This behaviour is used during the exploration phase:
 * Every exploration agent is running it in order to inform nearby agents that informations can be requested.
 * When the broadcast is received , the ExploreMultiAgent takes the communication over
 *
 */
public class ExploBroadcastBehaviour extends TickerBehaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2058134622078521998L;
	private DFAgentDescription dfd;

	/**
	 * An agent tries to contact its friend and to give him its current position
	 * @param myagent the agent who posses the behaviour
	 * @throws FIPAException 
	 *  
	 */
	public ExploBroadcastBehaviour (final Agent myagent)
	{
		super(myagent, 3000);
		
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("EXPLORATION"); // name of the service
		dfd.addServices(sd);
	}

	@Override
	public void onTick() {
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		DFAgentDescription[] result = {};
		
		try {
			result = DFService.search(this.myAgent, this.dfd);
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
}