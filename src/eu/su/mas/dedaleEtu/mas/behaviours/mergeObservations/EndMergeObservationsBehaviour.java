package eu.su.mas.dedaleEtu.mas.behaviours.mergeObservations;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.CollectFSMBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploreFSMBehaviour;
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
					System.out.println(this.myAgent.getLocalName() + " switching to collect role");
					this._myAgent.addBehaviour(new CollectFSMBehaviour(this._myAgent));
				break;
				
				case TANK:
					System.out.println(this.myAgent.getLocalName() + " switching to tank role");
					this._myAgent.addBehaviour(new TankFSMBehaviour(this._myAgent));
				break;
					
				case EXPLORATION:
					System.out.println(this.myAgent.getLocalName() + " switching to exploration role");
					this._myAgent.addBehaviour(new ExploreFSMBehaviour(this._myAgent));
				break;
					
				default:
					System.out.println("/!\\ SHOULD NOT HAPPEN /!\\");
				break;
			}
		}
}
