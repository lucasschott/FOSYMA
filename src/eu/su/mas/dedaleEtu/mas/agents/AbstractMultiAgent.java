package eu.su.mas.dedaleEtu.mas.agents;

import java.util.ArrayList;
import java.util.HashMap;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding.TreeNode;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import eu.su.mas.dedale.env.Observation;

public class AbstractMultiAgent extends AbstractDedaleAgent {

	public enum AgentType {
		EXPLORATION, COLLECT, TANK
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 866869490272891433L;
	
	public MapHandler map = new MapHandler();
	private DFAgentDescription desc = null;
	private HashMap<String, ServiceDescription> services = new HashMap<String, ServiceDescription>();
	private HashMap<String, TreeNode> trees = new HashMap<String, TreeNode>();
	private ArrayList<String> path = null;
	private String destinationId = null;
	private HashMap<String, Couple<Observation, Integer>> treasureMap = new HashMap<String, Couple<Observation, Integer>>();
	private DFAgentDescription dfd;
	private ServiceDescription sd;
	private Integer clock;
	
	private AgentType type;

	public AbstractMultiAgent(AgentType type) {
		this.type = type;
		this.clock = 0;
	}
	
	public AgentType getAgentType()
	{
		return this.type;
	}
	
	protected void setup()
	{
		super.setup();

		desc = new DFAgentDescription();
		desc.setName(this.getAID());
		
		try {
			DFService.register(this, desc);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DFAgentDescription[] getMatchingAgents(String service) {
		
		sd = new ServiceDescription();
		dfd = new DFAgentDescription();
		
		sd.setType("TANK");
		dfd.addServices(sd);
		
		DFAgentDescription[] result = {};
		try {
			result = DFService.search(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void updateTreasuresMap(String node, Observation obs, Integer quantity)
	{
		Couple<Observation, Integer> couple = new Couple<Observation, Integer>(obs, quantity);
		this.treasureMap.put(node, couple);
	}
	
	public void clearTreasureMap()
	{
		this.treasureMap.clear();
	}
	
	public HashMap<String, Couple<Observation, Integer>> getTreasureMap()
	{
		return this.treasureMap;
	}
	
	public void mergeTreasureMap(HashMap<String, Couple<Observation, Integer>> newMap)
	{
		this.treasureMap.putAll(newMap);
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
		System.out.println(this.getLocalName() + " AFTER MOVE");
		super.afterMove();
		this.map.AfterMove(this.getLocalName());
	}
	
	protected void beforeMove()
	{

		System.out.println(this.getLocalName() + " BEFORE MOVE");
		super.beforeMove();
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
	
	public AbstractMultiAgent.AgentType getType()
	{
		return this.type;
	}
}
