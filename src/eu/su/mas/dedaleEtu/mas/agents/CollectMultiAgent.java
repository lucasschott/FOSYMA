package eu.su.mas.dedaleEtu.mas.agents;

import java.util.ArrayList;
import java.util.List;

import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.utils.Mission;
import jade.core.behaviours.Behaviour;

public class CollectMultiAgent  extends AbstractMultiAgent {

	private static final long serialVersionUID = -6431752665590433727L;
	private Mission currentMission = null;
	private String tankPosition = null;
	
	/**
	 * This method is automatically called when "agent".start() is executed.
	 * Consider that Agent is launched for the first time. 
	 * 			1) set the agent attributes 
	 *	 		2) add the behaviours
	 *          
	 */
	
	public CollectMultiAgent() {
		super(AbstractMultiAgent.AgentType.COLLECT);
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
	
	public Mission getCurrentMission()
	{
		return this.currentMission;
	}
	
	public void setCurrentMission(Mission mission)
	{
		this.currentMission = mission;
	}

	public String getTankPosition() {
		return tankPosition;
	}

	public void setTankPosition(String tankerPosition) {
		this.tankPosition = tankerPosition;
	}
}