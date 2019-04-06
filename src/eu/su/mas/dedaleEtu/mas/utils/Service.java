package eu.su.mas.dedaleEtu.mas.utils;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Service {
	
	private DFAgentDescription a_desc = null;
	private ServiceDescription s_desc = null;
	private AbstractDedaleAgent agent;
	
	public Service(AbstractDedaleAgent agent, String name)
	{
		this.agent = agent;
		this.a_desc = new DFAgentDescription();
		this.s_desc = new ServiceDescription();
		
		this.s_desc.setType(name);
		this.s_desc.setName(this.agent.getLocalName());
		this.a_desc.setName(this.agent.getAID());
		this.a_desc.addServices(s_desc);
	}
	
	public void register()
	{
		try {
			DFService.register(this.agent, a_desc);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deregister()
	{
		this.a_desc.removeServices(this.s_desc);
		try {
			DFService.deregister(this.agent, this.a_desc);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}