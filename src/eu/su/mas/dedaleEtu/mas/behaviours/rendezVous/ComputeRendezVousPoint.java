package eu.su.mas.dedaleEtu.mas.behaviours.rendezVous;

import java.util.ArrayList;
import java.util.Collections;

import eu.su.mas.dedaleEtu.mas.agents.AbstractMultiAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.FSMCodes;
import jade.core.behaviours.OneShotBehaviour;

public class ComputeRendezVousPoint extends OneShotBehaviour {

	AbstractMultiAgent _myAgent;
	
	private static final long serialVersionUID = 7107325471968158201L;

	public ComputeRendezVousPoint(AbstractMultiAgent myAgent) {
		super(myAgent);
		_myAgent = myAgent;
	}
	
	@Override
	public void action() {
		String destination;
		String position = this._myAgent.getCurrentPosition();
		ArrayList<Integer> degrees = new ArrayList<Integer>();
		
		this._myAgent.map.computeCentroids();
		ArrayList<String> centroids = this._myAgent.map.getCentroids();
		Collections.sort(centroids);
		
		for (String centroid : centroids) {
			degrees.add(this._myAgent.map.getNodeDegree(centroid));
		}
		
		destination = centroids.get(degrees.indexOf(Collections.max(degrees)));
		
		ArrayList<String> shortestPath = (ArrayList<String>) this._myAgent.map.getMap().getShortestPath(position, destination);
		
		this._myAgent.setPath(shortestPath);
		this._myAgent.setDestinationId(centroids.get(degrees.indexOf(Collections.max(degrees))));
		
		System.out.println("TARGET CENTROID : " + this._myAgent.getDestinationId());
		this._myAgent.registerService("RENDEZ-VOUS");
	}
	
	public int onEnd() {
		return FSMCodes.Events.SUCESS.ordinal();
	}
}
