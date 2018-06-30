package it.polito.tdp.ufo.model;

public class Arco {

	private String statoPartenza;
	private String statoArrivo;
	
	public Arco(String statoPartenza, String statoArrivo) {
		super();
		this.statoPartenza = statoPartenza;
		this.statoArrivo = statoArrivo;
	}

	public String getStatoPartenza() {
		return statoPartenza;
	}

	public void setStatoPartenza(String statoPartenza) {
		this.statoPartenza = statoPartenza;
	}

	public String getStatoArrivo() {
		return statoArrivo;
	}

	public void setStatoArrivo(String statoArrivo) {
		this.statoArrivo = statoArrivo;
	}
	
	
	
}
