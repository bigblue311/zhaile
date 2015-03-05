package com.victor.framework.search.basic;

import java.io.Serializable;

public class PriceRange implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8393170574644973807L;
	private long id = 0;
	private double min=0.0;
	private double max=0.0;
	private String name = "";
	
	public PriceRange(long id,double min,double max,String name){
		this.id = id;
		this.min = min;
		this.max = max;
		this.name = name;
	}
	
	public boolean inRange(double price){
		return (price>min && price<=max);
	}

	public long getId() {
		return id;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public String getName() {
		return name;
	}
}
