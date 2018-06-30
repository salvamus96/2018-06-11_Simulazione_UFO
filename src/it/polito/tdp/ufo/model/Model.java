package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {

	private List <AnnoCount> anniAvvistamenti;
	private SightingsDAO sdao;
	
	private List <String> stati; // stati - vertici che fanno riferimento a uno specifico anno
	
	private Graph <String, DefaultEdge> graph;
	
	public Model () {
		this.sdao = new SightingsDAO();
		
		this.anniAvvistamenti = sdao.getAnni();
	}
	
	public List<AnnoCount> getAnniAvvistamenti() {
		return anniAvvistamenti;
	}
	
	public List<String> getStati() {
		return stati;
	}

	public void creaGrafo (Year anno) {
		this.graph = new SimpleDirectedGraph<>(DefaultEdge.class);
		
		this.stati = this.sdao.getStati(anno);
		
		Graphs.addAllVertices(this.graph, stati);
		System.out.println(graph.vertexSet().size());
		
		List <Arco> allArchi = this.sdao.allEdge(anno);
		for (Arco arco : allArchi)
			Graphs.addEdgeWithVertices(this.graph, arco.getStatoPartenza(), arco.getStatoArrivo());
		
		System.out.println(this.graph.edgeSet().size());
	}
	
	public List <String> getStatiPrecedenti (String stato){
		return Graphs.predecessorListOf(this.graph, stato);
	}
	
	public List <String> getStatiSuccessivi (String stato){
		return Graphs.successorListOf(this.graph, stato);
	}
	
	public List <String> getStatiRaggiungibili (String stato){
		
		// effettuo una visita in ampiezza per trovare gli stati raggiungibili da uno stato di partenza
		BreadthFirstIterator<String, DefaultEdge> bfi =
						new BreadthFirstIterator<>(this.graph, stato);
		
		List <String> raggiungibili = new ArrayList<>();
		
		// iterazione
		bfi.next(); // butto via il primo elemento, che l'iteratore considera
		while (bfi.hasNext())
			raggiungibili.add(bfi.next());
		
		//raggiungibili.remove(0);
		return raggiungibili;
	}
}
