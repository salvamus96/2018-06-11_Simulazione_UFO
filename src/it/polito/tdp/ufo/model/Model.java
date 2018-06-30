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
	
	private List <String> soluzione;
	
	
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
	
	
	public List <String> getPercorsoMassimo (String statoPartenza){
	
		this.soluzione = new ArrayList<>();
		
		List <String> parziale = new ArrayList<>();
		parziale.add(statoPartenza);
		
		this.ricorsiva (parziale);
		
		return soluzione;
	}

	private void ricorsiva(List<String> parziale) {
		
		// caso terminale (se ho aggiunto un elemento a parziale)
		if (parziale.size() > this.soluzione.size())
			this.soluzione = new ArrayList<>(parziale);
		
		// passo ricorsivo
		List <String> candidati = this.getStatiSuccessivi(parziale.get(parziale.size() - 1));
		
		for (String prova : candidati) {
			
			if (!parziale.contains(prova)) {
				parziale.add(prova);
				this.ricorsiva(parziale);
				parziale.remove(parziale.size() - 1);
			}
		}
	}
	
	
}
