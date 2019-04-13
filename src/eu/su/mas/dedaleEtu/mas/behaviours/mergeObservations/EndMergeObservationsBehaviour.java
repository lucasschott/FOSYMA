package eu.su.mas.dedaleEtu.mas.behaviours.mergeObservations;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
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
		}
		
		public int onEnd() {
			return FSMCodes.Events.SUCESS.ordinal();
		}
}
