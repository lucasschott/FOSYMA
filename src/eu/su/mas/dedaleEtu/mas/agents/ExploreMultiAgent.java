package eu.su.mas.dedaleEtu.mas.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.TreeNode;
import eu.su.mas.dedaleEtu.mas.utils.Mission;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import eu.su.mas.dedaleEtu.mas.agents.MapHandler;

public class ExploreMultiAgent extends AbstractMultiAgent {

	private static final long serialVersionUID = -6431752665590433727L;
	private String previousNode = null;
	private Mission currentAssist;
	
	/**
	 * This method is automatically called when "agent".start() is executed.
	 * Consider that Agent is launched for the first time. 
	 * 			1) set the agent attributes 
	 *	 		2) add the behaviours
	 *          
	 */
	
	public ExploreMultiAgent() {
		super(AbstractMultiAgent.AgentType.EXPLORATION);
	}
	
	protected void setup(){
		super.setup();
		
		List<Behaviour> lb=new ArrayList<Behaviour>();
		
		/************************************************
		 * 
		 * ADD the behaviours of the Dummy Moving Agent
		 * 
		 ************************************************/
		
		lb.add(new ExploMultiFSMBehaviour(this));
		
		/***
		 * MANDATORY TO ALLOW YOUR AGENT TO BE DEPLOYED CORRECTLY
		 */
		addBehaviour(new startMyBehaviours(this,lb));
		
		System.out.println("the  agent " + this.getLocalName() + " is started");
	}
	
	public void setPreviousNode(String node)
	{
		this.previousNode = node;
	}
	
	public String getPreviousNode()
	{
		return this.previousNode;
	}

	public Mission getCurrentAssist() {
		return currentAssist;
	}

	public void setCurrentAssist(Mission currentAssist) {
		this.currentAssist = currentAssist;
	}
}
