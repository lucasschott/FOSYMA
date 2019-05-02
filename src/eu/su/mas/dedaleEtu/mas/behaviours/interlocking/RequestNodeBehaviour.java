package eu.su.mas.dedaleEtu.mas.behaviours.interlocking;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.utils.Conflict;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class RequestNodeBehaviour extends OneShotBehaviour 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5484316876625643208L;
	private AbstractMultiAgent _myAgent;

	public RequestNodeBehaviour(AbstractMultiAgent myagent)
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		String requiredNode = this.getRequiredNode();
		String currentNode = this._myAgent.getCurrentPosition();
		String destinationNode = this._myAgent.getDestinationId();
		
		if (requiredNode == null)
			return;
		
		message.setSender(this._myAgent.getAID());
		
		for (DFAgentDescription dsc: this._myAgent.getMatchingAgents("ALL_AGENTS"))
		{
			if (!dsc.getName().toString().equals(this._myAgent.getAID().toString()))
				message.addReceiver(dsc.getName());
		}
		
		message.setProtocol("REQUEST-NODE");
		
		try 
		{
			Random r = new Random();
			
			Conflict conflict = new Conflict(currentNode, requiredNode, destinationNode);
			conflict.setPriority(r.nextInt(this._myAgent.getPriority()));
			this._myAgent.PushConflict(conflict);
			message.setContentObject(conflict);
			this._myAgent.sendMessage(message);
			System.out.println("REQUESTED-NODE");
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getRequiredNode()
	{
		List<String> path = this._myAgent.map.getMap().getShortestPath(this._myAgent.getCurrentPosition(), this._myAgent.getDestinationId());
		if (path.size() >= 1)
			return path.get(0);
		return null;
	}

}
