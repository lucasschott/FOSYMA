package eu.su.mas.dedaleEtu.mas.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.utils.Agent;
import eu.su.mas.dedaleEtu.mas.utils.Mission;
import jade.core.AID;
import jade.core.behaviours.Behaviour;

public class TankMultiAgent extends AbstractMultiAgent {

		private static final long serialVersionUID = -6431752665590433727L;
		
		private ArrayList<Agent> availableAgents = new ArrayList<Agent>();
		private ArrayList<Mission> onGoingMissions = new ArrayList<Mission>();
		private ArrayList<Mission> pendingMissions = new ArrayList<Mission>();
		
		/**
		 * This method is automatically called when "agent".start() is executed.
		 * Consider that Agent is launched for the first time. 
		 * 			1) set the agent attributes 
		 *	 		2) add the behaviours
		 *          
		 */
		
		public void addAvailableAgent(Agent newAgent)
		{
			if (this.availableAgents.contains(newAgent))
				return;
			this.availableAgents.add(newAgent);
		}
		
		public void addPendingMission(Mission mission)
		{
			if (this.pendingMissions.contains(mission))
				return;
			
			this.pendingMissions.add(mission);
		}
		
		public void setMissionOnGoing(Mission mission)
		{
			
		}
		
		public TankMultiAgent() {
			super(AbstractMultiAgent.AgentType.TANK);
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
}
