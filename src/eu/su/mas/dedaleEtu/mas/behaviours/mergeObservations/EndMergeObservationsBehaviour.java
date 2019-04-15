package eu.su.mas.dedaleEtu.mas.behaviours.mergeObservations;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.CollectMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import eu.su.mas.dedaleEtu.mas.agents.TankMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.CollectFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploreMultiFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import eu.su.mas.dedaleEtu.mas.behaviours.TankFSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class EndMergeObservationsBehaviour extends OneShotBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = -626168446823784736L;
		AbstractMultiAgent _myAgent;
		
		public EndMergeObservationsBehaviour(AbstractMultiAgent myagent) {
			super(myagent);
			this._myAgent = myagent;
		}

		public void action() {
			System.out.println(this.myAgent.getLocalName() + " Obs : " + ((AbstractMultiAgent)this.myAgent).getTreasureMap());
			System.out.println("RENDEZ-VOUS-TREE : " + this._myAgent.treeExist("RENDEZ-VOUS-TREE"));
			this.dispatchBehaviours();
		}
		
		public int onEnd() {
			return FSMCodes.Events.SUCESS.ordinal();
		}
		
		public void dispatchBehaviours()
		{
			switch(this._myAgent.getAgentType())
			{
				case COLLECT:
					CollectMultiAgent collectAgent = ((CollectMultiAgent)this._myAgent);
					System.out.println(this.myAgent.getLocalName() + " switching to collect role");
					this._myAgent.addBehaviour(new CollectFSMBehaviour(collectAgent));
				break;
				
				case TANK:
					TankMultiAgent tankAgent = ((TankMultiAgent)this._myAgent);
					System.out.println(this.myAgent.getLocalName() + " switching to tank role");
					this._myAgent.addBehaviour(new TankFSMBehaviour(tankAgent));
				break;
					
				case EXPLORATION:
					ExploreMultiAgent exploreAgent = ((ExploreMultiAgent)this._myAgent);
					System.out.println(this.myAgent.getLocalName() + " switching to exploration role");
					this._myAgent.addBehaviour(new ExploreMultiFSMBehaviour(exploreAgent));
				break;
					
				default:
					System.out.println("/!\\ SHOULD NOT HAPPEN /!\\");
				break;
			}
		}
}
