package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import java.io.Serializable;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.AID;


public abstract class TopDownTreeOperations extends TreeOperations {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 3177308571866902358L;
	
	protected int pending = 0;
	
	public TopDownTreeOperations(AbstractMultiAgent myagent, String treeId) {
		super(myagent, treeId);
		
		this.actions.put(State.IDLE, () -> this.idle());
		this.actions.put(State.WAIT_PARENT, () -> this.wait_parent());
		this.actions.put(State.REQUEST_CHILDREN, () -> this.request_children());
		this.actions.put(State.WAIT_CHILDREN, () -> this.wait_children());
		this.actions.put(State.SEND_PARENT, () -> this.send_parent());
		
	}	
	
	protected abstract boolean endConditionStrategy();
	protected abstract Serializable sendParentStrategy();
	protected abstract Serializable sendChildStrategy();
	protected abstract void receiveParentStrategy(Serializable s);
	protected abstract void receiveChildStrategy(Serializable s);
	protected abstract void resetStrategy();
	
	protected boolean check_done()
	{
		if (this.receiveDone(this._myAgent.getTree(this.treeId).getParent())) {
			this.finished = true;
			
			for (AID child: this._myAgent.getTree(this.treeId).getChildren())
				this.sendDone(child);
			
			return true;
		}
		return false;
	}
	
	@Override
	public void action() 
	{
		if (this._myAgent.treeExist(this.treeId)) {
			
			if (this._myAgent.getTree(this.treeId).getIsRoot() == false && this.check_done()) {
				this.finished = true;
				System.out.println(this._myAgent.getLocalName() + " RECEIVED DONE");
				return;
			}
			
			this.actions.get(this.state).run();
		}
	}
	
	protected void idle()
	{
		System.out.println(this._myAgent.getLocalName() + " TOP DOWN IDLE");
		this.pending = 0;
		
		this.resetStrategy();
		
		if (this._myAgent.getTree(treeId).getIsRoot() == true) {
			this.state = State.REQUEST_CHILDREN;
		}
		else {
			this.state = State.WAIT_PARENT;
		}
	}
	
	protected void wait_parent()
	{
		System.out.println(this._myAgent.getLocalName() + " TOP DOWN WAIT PARENT");
		
		if (this.receiveParent() == true) {
			System.out.println("RECEIVED FROM PAREEEEEEEENT");
			this.state = State.REQUEST_CHILDREN;
		}
	}
	
	protected void request_children()
	{
		System.out.println(this._myAgent.getLocalName() + " TOP DOWN REQUEST CHILDREN (" + this._myAgent.getTree(this.treeId).getChildCount() + ")");
		System.out.println("CHILDRENS : " + this._myAgent.getTree(treeId).getChildren());
		System.out.println("TREE : " + treeId);
		
		for (AID child: this._myAgent.getTree(treeId).getChildren()) {
			this.sendChild(child);
			this.pending = this.pending + 1;
		}
		
		this.state = State.WAIT_CHILDREN;
	}
	
	protected void wait_children()
	{
		System.out.println(this._myAgent.getLocalName() + " TOP DOWN WAIT CHILDREN (" + this.pending + ")");
		
		for (AID child: this._myAgent.getTree(treeId).getChildren()) 
		{
			if (this.receiveChild(child))
				this.pending = this.pending - 1;
		}
		
		if (this.pending == 0) {
			System.out.println(this._myAgent.getLocalName() + " TOP DOWN RECEIVED ALL CHILDREN");
			this.state = State.SEND_PARENT;
		}
	}
	
	protected void send_parent()
	{
		System.out.println(this._myAgent.getLocalName() + " TOP DOWN SEND PARENT");
		
		if (this._myAgent.getTree(treeId).getIsRoot() == false) {
			this.sendParent();
			this.state = State.IDLE;	
		}
		
		else {
			if (this.endConditionStrategy()) {
				
				System.out.println("END CONDITION REACHED");
				this.finished = true;
				
				for (AID child: this._myAgent.getTree(this.treeId).getChildren())
					this.sendDone(child);
			}
			
			this.state = State.IDLE;
		}
	}

	public int onEnd() 
	{
		System.out.println(this._myAgent.getLocalName() + " FINISHED : " + this.finished);
		
		if (this.finished == true) {
			System.out.println(this._myAgent.getLocalName() + " FINISHED TREE");
			if (this._myAgent.getTree(treeId).getIsRoot())
				return FSMCodes.Events.SUCESS_PARENT.ordinal();
			return FSMCodes.Events.SUCESS_CHILD.ordinal();
		}
		
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
