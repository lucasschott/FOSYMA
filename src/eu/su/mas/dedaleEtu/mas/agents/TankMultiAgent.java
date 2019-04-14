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
		private HashMap<String, Mission> onGoingMissions = new HashMap<String, Mission>();
		private HashMap<String, Mission> pendingMissions = new HashMap<String, Mission>();
		
		/**
		 * This method is automatically called when "agent".start() is executed.
		 * Consider that Agent is launched for the first time. 
		 * 			1) set the agent attributes 
		 *	 		2) add the behaviours
		 *          
		 */
		
		public ArrayList<Agent> getAvailableAgents()
		{
			return this.availableAgents;
		}
		
		public Agent popAvailable()
		{
			return this.availableAgents.remove(0);
		}
		
		public Integer getAvailableCount()
		{
			return this.availableAgents.size();
		}
		
		public  HashMap<String, Mission> getPendingMissions()
		{
			return this.pendingMissions;
		}
		
		public  HashMap<String, Mission> getOnGoingMissions()
		{
			return this.onGoingMissions;
		}
	
		public boolean isInMission(Agent newAgent) {
			
			System.out.println("LOOKING FOR : " + newAgent.getAID().getLocalName());
			
			for (Mission mission: this.pendingMissions.values()) {
				System.out.println("LEADER : " + mission.getLeader().toString());
				if (mission.getLeader().equals(newAgent)) {
					System.out.println(newAgent + " in pending missions");
					return true;
				}
			}
			
			for (Mission mission: this.onGoingMissions.values()) {
				System.out.println("LEADER : " + mission.getLeader().getAID().getLocalName().toString());
				if (mission.getLeader().equals(newAgent)) {
					System.out.println(newAgent + " in ongoing missions");
					return true;
				}
			}
			
			System.out.println(newAgent + " not in missions");
			return false;
		}
		public void addAvailableAgent(Agent newAgent)
		{
			if (this.availableAgents.contains(newAgent) || this.isInMission(newAgent))
				return;
			
			System.out.println("CURRENT PENDING MISSIONS : " + this.pendingMissions);
			System.out.println("CURRENT ONGOING MISSIONS : " + this.onGoingMissions);
			System.out.println("CURRENT AVAILABLE LIST : " + this.availableAgents);
			System.out.println("ADDING : " + newAgent);
			
			this.availableAgents.add(newAgent);
		}
		
		public void addPendingMission(Mission mission)
		{
			if (this.pendingMissions.keySet().contains(mission.getUUID()))
				return;
			
			this.pendingMissions.put(mission.getUUID(), mission);
		}
		
		public void setMissionOnGoing(String uuid)
		{
			if (!this.pendingMissions.keySet().contains(uuid))
				return;
			
			Mission mission = this.pendingMissions.remove(uuid);
			this.onGoingMissions.put(mission.getUUID(), mission);
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
