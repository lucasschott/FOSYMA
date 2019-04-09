package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public abstract class TreeOperations extends OneShotBehaviour{
	
	/**
	 * 
	 */
	protected static final long serialVersionUID = 3177308571866902358L;
	
	enum State {
		IDLE, WAIT_PARENT, WAIT_CHILDREN, REQUEST_CHILDREN, SEND_PARENT
	}
	
	protected AbstractMultiAgent _myAgent;
	protected String treeId;
	protected String treeType;
	protected State state;
	
	protected int pending = 0;
	protected boolean finished = false;
	protected Map<State, Runnable> actions = new HashMap<>();
	
	public TreeOperations(AbstractMultiAgent myagent, String treeId) {
		super(myagent);
		this._myAgent = myagent;
		this.treeId = treeId;
		this.state = State.IDLE;
		  
		this.actions.put(State.IDLE, () -> this.idle());
		this.actions.put(State.WAIT_PARENT, () -> this.wait_parent());
		this.actions.put(State.REQUEST_CHILDREN, () -> this.request_children());
		this.actions.put(State.WAIT_CHILDREN, () -> this.wait_children());
		this.actions.put(State.SEND_PARENT, () -> this.send_parent());
		
		this.treeType = "";
	}
	
	protected abstract boolean endConditionStrategy();
	protected abstract Serializable sendParentStrategy();
	protected abstract Serializable sendChildStrategy();
	protected abstract void receiveParentStrategy(Serializable s);
	protected abstract void receiveChildStrategy(Serializable s);
	protected abstract void resetStrategy();
	
	protected void idle()
	{
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
		if (this.receiveParent() == true) {
			this.state = State.REQUEST_CHILDREN;
		}
	}
	
	protected void request_children()
	{
		for (AID child: this._myAgent.getTree(treeId).getChildren()) {
			this.sendChild(child);
			this.pending = this.pending + 1;
		}
		this.state = State.WAIT_CHILDREN;
	}
	
	protected void wait_children()
	{
		for (AID child: this._myAgent.getTree(treeId).getChildren()) 
		{
			if (this.receiveChild(child))
				this.pending = this.pending - 1;
		}
		
		if (this.pending == 0) {
			this.state = State.SEND_PARENT;
		}
	}
	
	protected void send_parent()
	{
		if (this._myAgent.getTree(treeId).getIsRoot() == false) {
			this.sendParent();
			this.state = State.IDLE;	
		}
		
		else {
			
			if (this.endConditionStrategy())
				this.finished = true;
			
			this.state = State.IDLE;
		}
	}
	
	@Override
	public void action() 
	{
		if (this._myAgent.treeExist(this.treeId)) {
			this.actions.get(this.state).run();
		}
	}
	
	protected void sendParent()
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		
		msg.setSender(this.myAgent.getAID());
		msg.addReceiver(this._myAgent.getTree(this.treeId).getParent());
		msg.setProtocol(this.treeType + "-ANSWER-PARENT");
		msg.setLanguage(this.treeType);
		msg.setConversationId(this.treeId);
		
		try 
		{
			msg.setContentObject(this.sendParentStrategy());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	protected boolean receiveParent()  
	{
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol(this.treeType + "-REQUEST-CHILD"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchSender(this._myAgent.getTree(treeId).getParent()));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchConversationId(this.treeId));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchLanguage(this.treeType));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		if (msg != null) 
		{
			try 
			{
				this.receiveParentStrategy(msg.getContentObject());
			} 
			catch (UnreadableException e) 
			{
				e.printStackTrace();
			}
		}
		
		return msg != null;
	}
	
	protected boolean receiveChild(AID child) {
		
		if (this._myAgent.getTree(this.treeId).getChildCount() == 0)
			return false;
	
		ACLMessage msg;
			
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol(this.treeType + "-ANSWER-PARENT"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchConversationId(this.treeId));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchSender(child));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchLanguage(this.treeType));
			
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
			
		if (msg != null) 
		{
			try {
				this.receiveChildStrategy(msg.getContentObject());
			} 
				
			catch (UnreadableException e) {
				e.printStackTrace();
			}
		}
		
		return msg != null;
	}
	
	protected void sendChild(AID child)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		
		msg.setSender(this.myAgent.getAID());
		msg.addReceiver(child);
		msg.setProtocol(this.treeType + "-REQUEST-CHILD");
		msg.setLanguage(this.treeType);
		msg.setConversationId(this.treeId);
		
		try 
		{
			msg.setContentObject(this.sendChildStrategy());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	public int onEnd() 
	{
		if (this.finished == true) {
			return FSMCodes.Events.SUCESS.ordinal();	
		}
		
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
