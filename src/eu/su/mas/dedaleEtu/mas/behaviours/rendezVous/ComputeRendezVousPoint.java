package eu.su.mas.dedaleEtu.mas.behaviours.rendezVous;

import java.util.ArrayList;
import java.util.Collections;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.ExploreMultiAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class ComputeRendezVousPoint extends OneShotBehaviour {

	ExploreMultiAgent _myAgent;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7107325471968158201L;

	public ComputeRendezVousPoint(ExploreMultiAgent myAgent) {
		super(myAgent);
		_myAgent = myAgent;
	}
	
	@Override
	public void action() {
	
		this._myAgent.map.computeCentroids();
		ArrayList<String> centroids = this._myAgent.map.getCentroids();
		ArrayList<Integer> degrees = new ArrayList<Integer>();
		
		for (String centroid : centroids) {
			degrees.add(this._myAgent.map.getNodeDegree(centroid));
		}
		
		System.out.println("Centroid : " + centroids.get(degrees.indexOf(Collections.max(degrees))));
		System.out.println("ENDED");
	}
}
