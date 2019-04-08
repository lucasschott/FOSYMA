package eu.su.mas.dedaleEtu.mas.behaviours.echoFlooding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PushSumBehaviour extends OneShotBehaviour{

	public enum Mode {
		TREE_SIZE, CAPACITY, LOCK_PICK
	}
	
	enum State {
		IDLE, WAIT_PARENT, WAIT_CHILDREN, REQUEST_CHILDREN, SEND_PARENT
	}
	
	private static final long serialVersionUID = 7295849474602290463L;
	private ExploreMultiAgent _myAgent;
	private String treeId;
	private Mode mode;
	private State state;
	
	private int treshold;
	private int pending = 0;
	private int value = 0;
	private boolean finished = false;
	Map<State, Runnable> actions = new HashMap<>();
	
	
	public PushSumBehaviour(ExploreMultiAgent myagent, String treeId, Mode mode, int treshold) {
		super(myagent);
		this._myAgent = myagent;
		this.treeId = treeId;
		this.mode = mode;
		this.treshold = treshold;
		this.state = State.IDLE;
		  
		this.actions.put(State.IDLE, () -> this.idle());
		this.actions.put(State.WAIT_PARENT, () -> this.wait_parent());
		this.actions.put(State.REQUEST_CHILDREN, () -> this.request_children());
		this.actions.put(State.WAIT_CHILDREN, () -> this.wait_children());
		this.actions.put(State.SEND_PARENT, () -> this.send_parent());
	}
	
	private void idle()
	{
		this.pending = 0;
		this.value = 0;
		if (this._myAgent.getTree(treeId).getIsRoot() == true) {
			this.state = State.REQUEST_CHILDREN;
		}
		else {
			this.state = State.WAIT_PARENT;
		}
	}
	
	private void wait_parent()
	{
		if (this.checkParentRequest() == true) {
			this.state = State.REQUEST_CHILDREN;
		}
	}
	
	private void request_children()
	{
		for (AID child: this._myAgent.getTree(treeId).getChildren()) {
			this.request_child(child);
			this.pending = this.pending + 1;
		}
		this.state = State.WAIT_CHILDREN;
	}
	
	private void wait_children()
	{
		this.checkChildrenResponse();
		if (this.pending == 0) {
			this.state = State.SEND_PARENT;
		}
	}
	
	private void send_parent()
	{
		if (this._myAgent.getTree(treeId).getIsRoot() == false) {
			this.sendParent();
			this.state = State.IDLE;	
		}
		
		else {

			System.out.println("Count : " + (this.value + 1) + " Treshold : " + String.valueOf(this.treshold));
			
			if (this.value + 1 >= this.treshold) {
				this.finished = true;
			}
			
			this.state = State.IDLE;
		}
	}
	
	@Override
	public void action() 
	{
		if (this._myAgent.treeExist(this.treeId)) {
			action_treeSize();
		}
	}
	
	public void action_treeSize()
	{	
		this.actions.get(this.state).run();
	}
	
	public void sendParent()
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		
		msg.setSender(this.myAgent.getAID());
		msg.addReceiver(this._myAgent.getTree(this.treeId).getParent());
		msg.setProtocol("TREE-ANSWER-PARENT");
		msg.setConversationId(this.treeId);
		msg.setContent(String.valueOf(this.value + 1));
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	public boolean checkParentRequest()  
	{
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("TREE-REQUEST-CHILD"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchSender(this._myAgent.getTree(treeId).getParent()));
		pattern = MessageTemplate.and(pattern, MessageTemplate.MatchConversationId(this.treeId));
		
		ACLMessage msg;
		
		msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
		
		System.out.println("MESSAGE : " + msg);
		
		if (msg != null)
			System.out.println("CHECK REQ : " + this._myAgent.getLocalName() + " Received from : " + msg.getSender().getLocalName());
		
		return msg != null;
	}
	
	public void checkChildrenResponse() {
		
		if (this._myAgent.getTree(this.treeId).getChildCount() == 0)
			return;
		
		for (AID child: this._myAgent.getTree(treeId).getChildren()) 
		{
			ACLMessage msg;
			
			MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol("TREE-ANSWER-PARENT"), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			pattern = MessageTemplate.and(pattern, MessageTemplate.MatchConversationId(this.treeId));
			pattern = MessageTemplate.and(pattern, MessageTemplate.MatchSender(child));	
			
			msg = ((AbstractDedaleAgent)this.myAgent).receive(pattern);
			
			if (msg != null) {
				this.value = this.value + Integer.valueOf(msg.getContent());
				this.pending = this.pending - 1;	
			}
		}
	}
	
	public void request_child(AID child)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		
		msg.setSender(this.myAgent.getAID());
		msg.addReceiver(child);
		msg.setProtocol("TREE-REQUEST-CHILD");
		msg.setConversationId(this.treeId);
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
	}
	
	public int onEnd() 
	{
		if (this.finished == true) {
			System.out.println("FINAL VALUE : " + (this.value + 1));
			System.out.println("TRESHOLD : " + this.treshold);
			return FSMCodes.Events.SUCESS.ordinal();	
		}
		
		return FSMCodes.Events.FAILURE.ordinal();
	}
}
