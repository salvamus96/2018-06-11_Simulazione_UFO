package it.polito.tdp.ufo.model;

import java.time.Year;

public class AnnoCount {

	public Year anno;
	public int count;
	
	public AnnoCount(Year anno, int count) {
		super();
		this.anno = anno;
		this.count = count;
	}

	public Year getAnno() {
		return anno;
	}

	public void setAnno(Year anno) {
		this.anno = anno;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String toString () {
		return String.format("%s (%d)", anno, count);
	}
	
}
