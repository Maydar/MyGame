package ru.mail.projects.utils;


public class LongId <T> {
	
	private Integer id;
	
	public LongId (int id) {
		
		this.id = new Integer (id);
	}
	
	public Integer getLong () {
		 return id;
	}
	
	public boolean equals (LongId<?> other) {
		return (this.id.equals(other.getLong()));
	}
	
	public int hashCode () {
		return id;
	}
}
