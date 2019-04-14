package eu.su.mas.dedaleEtu.mas.behaviours.tank;

import java.io.IOException;
import java.util.ArrayList;

import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import eu.su.mas.dedaleEtu.mas.utils.Agent;
import eu.su.mas.dedaleEtu.mas.utils.Mission;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendMissionAssignementBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2925644300003259511L;
	private TankMultiAgent _myAgent;

	public SendMissionAssignementBehaviour(TankMultiAgent myagent) 
	{
		super(myagent);
		this._myAgent = myagent;
	}
	
	@Override
	public void action() 
	{
		if (this._myAgent.getAvailableCount() > 0)
		{
			if (this._myAgent.getTreasureMap().size() == 0)
				return;
			
			String max = this._myAgent.getMaxTreasureQuantity();
			Agent agent = this._myAgent.popAvailable();
			Mission newMission = new Mission(agent, max,  this._myAgent.getTreasureMap().get(max).getLeft(),  this._myAgent.getTreasureMap().get(max).getRight());
			
			this._myAgent.removeFromTreasureMap(max);
			this._myAgent.addPendingMission(newMission);
			this.sendAssignMission(agent.getAID(), newMission);
			}
	}

	private void sendAssignMission(AID to, Mission newMission) 
	{
		ACLMessage msg = buildMessage(to);
		try {
			msg.setContentObject(newMission);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Sending ASSIGN-MISSION to : " + to);
		this._myAgent.sendMessage(msg);
	}
	
	private ACLMessage buildMessage(AID to) {
		ACLMessage msg=new ACLMessage(ACLMessage.PROPOSE);
		
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("ASSIGN-MISSION");
		msg.addReceiver(to);
		
		return msg;
	}
}
