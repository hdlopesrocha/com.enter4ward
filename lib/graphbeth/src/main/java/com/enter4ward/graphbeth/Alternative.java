package com.enter4ward.graphbeth;

public class Alternative implements Comparable<Alternative>{
	


	private String id;
	
	public Alternative(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public int compareTo(Alternative a) {
		return id.compareTo(a.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}	
	
	

}
