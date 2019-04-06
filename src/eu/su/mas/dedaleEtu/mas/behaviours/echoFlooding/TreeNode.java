package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import java.util.ArrayList;

import jade.core.AID;

public class TreeNode {

	boolean isRoot;
	
	ArrayList<AID> childrens = new ArrayList<AID>();
	AID parent;
	String treeId;
	
	public TreeNode(String treeId, AID parent, boolean root) {
		this.parent = parent;
		this.treeId = treeId;
	}
	
	public void setParent(AID parent) {
		this.parent = parent;
	}
	
	public boolean getIsRoot() {
		return this.isRoot;
	}
	
	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	public AID getParent() {
		return this.parent;
	}
	
	public boolean isChild(AID agent) {
		return this.childrens.contains(agent);
	}
	
	public void addChild(AID agent) {
		this.childrens.add(agent);
	}
	
	public void removeChild(AID agent) {
		this.childrens.remove(agent);
	}
	
	public ArrayList<AID> getChildren() {
		return this.childrens;
	}
	
	public int getChildCount() {
		return this.childrens.size();
	}
}
