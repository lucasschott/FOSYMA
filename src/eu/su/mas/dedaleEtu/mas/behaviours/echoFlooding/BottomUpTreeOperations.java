package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.AID;

public abstract class BottomUpTreeOperations extends TreeOperations {
	/**
	 * 
	 */
	protected static final long serialVersionUID = 3177308571866902358L;
	
	protected int pending = 0;
	protected int done = 0;
	
	public BottomUpTreeOperations(AbstractMultiAgent myagent, String treeId) {
		super(myagent, treeId);
		
		this.actions.put(State.IDLE, () -> this.idle());
		this.actions.put(State.SEND_PARENT, () -> this.send_parent());
		this.actions.put(State.WAIT_CHILDREN, () -> this.wait_children());
		this.actions.put(State.WAIT_PARENT, () -> this.wait_parent());
		this.actions.put(State.REQUEST_CHILDREN, () -> this.request_children());
	}
	
	protected boolean check_done()
	{
		for (AID child: this._myAgent.getTree(this.treeId).getChildren()) {	
			if (this.receiveDone(child))
				this.done = this.done + 1;
			
			if (this.done == this._myAgent.getTree(this.treeId).getChildCount()) {
				
				this.finished = true;
					this.sendDone(this._myAgent.getTree(this.treeId).getParent());
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void action() 
	{
		if (this._myAgent.treeExist(this.treeId)) {
			
			if (this._myAgent.getTree(this.treeId).getIsLeaf() == false && this.check_done())
				return;
			
			this.actions.get(this.state).run();
		}
	}
	
	protected void idle()
	{
		this.pending = this._myAgent.getTree(this.treeId).getChildCount();
		
		System.out.println(this._myAgent.getLocalName() + " BOTTOM UP IDLE");
		
		this.resetStrategy();
		
		if (this._myAgent.getTree(treeId).getIsLeaf() == true) {
			System.out.println(this._myAgent.getLocalName() + " is leaf : SENDING PARENT");
			this.state = State.SEND_PARENT;
		}
		else {
			this.state = State.WAIT_CHILDREN;
			System.out.println(this._myAgent.getLocalName() + " is not leaf : WAITING CHILDREN");
		}
	}
	
	protected void wait_parent()
	{
		System.out.println(this._myAgent.getLocalName() + " BOTTOM WAIT PARENT");
		if (this.receiveParent() == true) {
			this.state = State.REQUEST_CHILDREN;
		}
	}
	
	protected void request_children()
	{	
		System.out.println(this._myAgent.getLocalName() + " BOTTOM SEND CHILDREN");
		
		if (this._myAgent.getTree(treeId).getIsLeaf() == false) {
			for (AID child: this._myAgent.getTree(treeId).getChildren())
				this.sendChild(child);
		}
		
		else if (this.endConditionStrategy()) {
				this.finished = true;
				this.sendDone(this._myAgent.getTree(this.treeId).getParent());
			}
		
		this.state = State.IDLE;
	}
	
	protected void wait_children()
	{
		System.out.println(this._myAgent.getLocalName() + " BOTTOM WAIT CHILDREN");
		
		for (AID child: this._myAgent.getTree(treeId).getChildren()) 
		{
			if (this.receiveChild(child)) {
				this.pending = this.pending - 1;
				System.out.println(this._myAgent.getLocalName() + " received from : " + child.toString());
			}
		}
		
		if (this.pending == 0) {
			this.state = State.SEND_PARENT;
		}
	}
	
	protected void send_parent()
	{
		System.out.println(this._myAgent.getLocalName() + " BOTTOM SEND_PARENT");
		
		if (this._myAgent.getTree(this.treeId).getIsRoot()) {
			this.state = State.REQUEST_CHILDREN;
		}
		
		else {
			this.sendParent();
			this.state = State.WAIT_PARENT;
		}
	}
	
	@Override
	public int onEnd() {
	System.out.println(this._myAgent.getLocalName() + " FINISHED : " + this.finished);
		
		if (this.finished == true) {
			System.out.println(this._myAgent.getLocalName() + " FINISHED TREE -> " + this._myAgent.getTree(treeId).getIsLeaf());
			System.out.println("ROOT : " + this._myAgent.getTree(treeId).getIsRoot());
			System.out.println("LEAF : " + this._myAgent.getTree(treeId).getIsLeaf());
			if (this._myAgent.getTree(treeId).getIsRoot())
				return FSMCodes.Events.SUCESS_PARENT.ordinal();
			return FSMCodes.Events.SUCESS_CHILD.ordinal();
		}
		
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
