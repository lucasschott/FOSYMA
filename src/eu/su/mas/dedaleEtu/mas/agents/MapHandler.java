package eu.su.mas.dedaleEtu.mas.agents;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;

public class MapHandler implements java.io.Serializable
{
	private static final long serialVersionUID = -6431752665590433727L;
	
	private MapRepresentation myMap;
	
	private byte[] _save;

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
		this._save = this.myMap.saveState(path);
	}
	
	public void AfterMove(String path)
	{
		try (FileOutputStream stream = new FileOutputStream(path))
		{
			stream.write(this._save);
			stream.close();
			
		    this.myMap = new MapRepresentation();
			this.myMap.restoreState(path);
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
