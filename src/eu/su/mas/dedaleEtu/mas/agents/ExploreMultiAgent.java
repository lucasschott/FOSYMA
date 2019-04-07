package eu.su.mas.dedaleEtu.mas.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.TreeNode;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import eu.su.mas.dedaleEtu.mas.agents.MapHandler;

public class ExploreMultiAgent extends AbstractDedaleAgent {

	private static final long serialVersionUID = -6431752665590433727L;
	public MapHandler map = new MapHandler();
	private DFAgentDescription desc = null;
	
	private HashMap<String, ServiceDescription> services = new HashMap<String, ServiceDescription>();
	private HashMap<String, TreeNode> trees = new HashMap<String, TreeNode>();
	
	private ArrayList<String> path = null;
	private String destinationId = null;
	
	/**
	 * This method is automatically called when "agent".start() is executed.
	 * Consider that Agent is launched for the first time. 
	 * 			1) set the agent attributes 
	 *	 		2) add the behaviours
	 *          
	 */
	
	protected void setup(){
		super.setup();

		desc = new DFAgentDescription();
		desc.setName(this.getAID());
		
		try {
			DFService.register(this, desc);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public boolean registerService(String service) {
		if (this.services.containsKey(service))
			return false;
		
		ServiceDescription sd = new ServiceDescription();
		
		sd.setType(service);
		sd.setName(this.getLocalName());
		
		this.desc.addServices(sd);
		
		this.services.put(service, sd);
		
		try {
			DFService.modify(this, desc);
		} 
		
		catch (FIPAException e) {
			//e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean deregisterService(String service) {
		if (!this.services.containsKey(service))
			return false;
		
		this.desc.removeServices(this.services.get(service));
		this.services.remove(service);
		
		try {
			DFService.modify(this, desc);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean addTree(String treeId, AID parent, boolean root) {
		if (this.treeExist(treeId) == true) {
			return false;
		}

		this.trees.put(treeId, new TreeNode(treeId, parent, root));
		return true;
	}
	
	public void removeTree(String treeId) {
		this.trees.remove(treeId);
	}
	
	public boolean addChildToTree(String treeId, AID child) {
		if (this.treeExist(treeId) == false) {
			return false;
		}
		
		this.trees.get(treeId).addChild(child);
		
		return true;
	}
	
	public boolean isTreeLocked(String treeId) {
		return this.trees.get(treeId).getLocked();
	}
	
	public void setTreeLocked(String treeId, boolean lock)
	{
		this.trees.get(treeId).setLocked(lock);
	}
	
	public boolean removeChildFromTree(String treeId, AID child) {
		if (this.treeExist(treeId) == false) {
			return false;
		}
		
		this.trees.get(treeId).removeChild(child);
		return true;
	}
	
	public boolean treeExist(String treeId) {
		return this.trees.containsKey(treeId);
	}
	
	public TreeNode getTree(String treeId) {
		return this.trees.get(treeId);
	}
	
	protected void afterMove()
	{
		this.map.AfterMove(this.getLocalName());
	}
	
	protected void beforeMove()
	{
		this.map.beforeMove(this.getLocalName());
	}

	public void setDestinationId(String destinationId)
	{
		this.destinationId = destinationId;
	}
	
	public void setPath(ArrayList<String> path)
	{
		this.path = path;
	}
	
	public String getDestinationId()
	{
		return this.destinationId;
	}
	
	public ArrayList<String> getPath()
	{
		return this.path;
	}
}
