package eu.su.mas.dedaleEtu.mas.agents;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDOT;

import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;

public class MapHandler implements java.io.Serializable
{
	private static final long serialVersionUID = -6431752665590433727L;
	
	private MapRepresentation myMap;

	/**
	 * Nodes known but not yet visited
	 */
	private List<String> openNodes;
	
	/**
	 * Visited nodes
	 */
	private Set<String> closedNodes;
	
	public MapHandler ()
	{
		this.myMap = new MapRepresentation();
		this.openNodes=new ArrayList<String>();
		this.closedNodes=new HashSet<String>();
	}
	
	public MapRepresentation getMap()
	{
		return this.myMap;
	}
	
	public List<String> getOpenNodes()
	{
		return this.openNodes;
	}
	
	public void computeCentroids() {
		this.myMap.computeCentroids();
	}
	
	public ArrayList<String> getCentroids() {
		return this.myMap.getCentroids();
	}
	
	public String getOpenNodesString()
	{
		try (ByteArrayOutputStream bo = new ByteArrayOutputStream();
				ObjectOutputStream so = new ObjectOutputStream(bo)) 
		{
	          so.writeObject(this.openNodes);
	          so.flush();
	          
	          return Base64.getEncoder().encodeToString(bo.toByteArray());
	    }
	    catch (IOException e) 
		{
	    	
		}
	        return null;
	}
	
	public void shuffleOpenNodes() {
		Collections.shuffle(this.openNodes);
	}
	
	public Set<String> getClosedNodes()
	{
		return this.closedNodes;
	}
	
	public boolean isClosed(String nodeId)
	{
		return this.closedNodes.contains(nodeId);
	}
	
	public boolean isOpen(String nodeId)
	{
		return this.openNodes.contains(nodeId);
	}
	
	public void addNode(String nodeId)
	{
		this.myMap.addNode(nodeId);
	}
	
	public void addNode(String nodeId, MapAttribute att) {
		this.myMap.addNode(nodeId,  att);
	}
	
	public void addEdge(String source, String destination)
	{
		this.myMap.addEdge(source, destination);
	}
	
	public void removeNode(String nodeId)
	{
	}
	
	public boolean addClosed(String nodeId)
	{
		return this.closedNodes.add(nodeId);
	}
	
	public boolean removeClosed(String nodeId)
	{
		return this.closedNodes.remove(nodeId);
	}
	
	public boolean addOpen(String nodeId)
	{
		return this.openNodes.add(nodeId); 
	}
	
	public boolean removeOpen(String nodeId)
	{
		return this.openNodes.remove(nodeId);
	}
	
	public void beforeMove(String path)
	{
		this.myMap.prepareMigration();
	}
	
	public boolean isInGraph(String nodeId)
	{
		return this.myMap.isInGraph(nodeId);
	}
	
	public int getNodeDegree(String nodeId) {
		return this.myMap.getNodeDegree(nodeId);
	}
	
	public void AfterMove(String path)
	{
		this.myMap.loadSavedData();
	}
}
