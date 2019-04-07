package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SendTreeRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 7295849474602290463L;
	/**
	 * 
	 */
	
	private DFAgentDescription dfd = new DFAgentDescription();
	private ServiceDescription sd = new ServiceDescription();
	private String tree;
	private ExploreMultiAgent _myagent = null;
	
	public SendTreeRequestBehaviour(ExploreMultiAgent myagent, String tree, String service) {
		super(myagent);
		this._myagent = myagent;
		this.tree = tree;
		sd.setType(service);
		dfd.addServices(sd);
	}

	@Override
	public void action() 
	{	
		if (this._myagent.treeExist(tree) == false)
			return;
		
		DFAgentDescription[] result = {};
		
		result = this.getMatchingAgents();
		
		ACLMessage msg = this.buildBroadcastMessage(result);
		
		msg.setContent(this.tree);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	private ACLMessage buildBroadcastMessage(DFAgentDescription[] result) {
		ACLMessage msg=new ACLMessage(ACLMessage.REQUEST);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("TREE-REQUEST");
		msg.setContent(this.tree);
		
		for (DFAgentDescription dsc : result)
		{
			if (!this.myAgent.getAID().toString().equals(dsc.getName().toString())) {
				msg.addReceiver(dsc.getName());
			}
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
		if (this._myagent.treeExist(tree) == false)
			return FSMCodes.Events.FAILURE.ordinal();
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
