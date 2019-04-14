package eu.su.mas.dedaleEtu.mas.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
		public boolean isInMission(Agent newAgent) 
		{
			for (Mission mission: this.pendingMissions.values()) {
				if (mission.getLeader().equals(newAgent)) {
					return true;
				}
			}
			
			for (Mission mission: this.onGoingMissions.values()) {
				if (mission.getLeader().equals(newAgent)) {
					return true;
				}
			}
			
			return false;
		}
		
		public void addAvailableAgent(Agent newAgent)
		{
			if (this.availableAgents.contains(newAgent) || this.isInMission(newAgent))
				return;
			
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
		
		public void updatePendingMissionsTTL() {
			  Iterator<Map.Entry<String, Mission>> iterator = this.pendingMissions.entrySet().iterator();
			  while (iterator.hasNext()) {
		            Map.Entry<String, Mission> entry  = iterator.next();
		            
		            if (entry.getValue().decreaseTTL() <= 0) {
		            	iterator.remove();
		            	this.addAvailableAgent(entry.getValue().getLeader());
		            }
			  }
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
