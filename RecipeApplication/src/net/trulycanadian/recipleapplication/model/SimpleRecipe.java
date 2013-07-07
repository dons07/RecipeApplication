package net.trulycanadian.recipleapplication.model;

import java.io.Serializable;

public class SimpleRecipe implements Serializable{

	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public int getHealthRating() {
		return healthRating;
	}
	public void setHealthRating(int healthRating) {
		this.healthRating = healthRating;
	}
	public int getTasteRating() {
		return tasteRating;
	}
	public void setTasteRating(int tasteRating) {
		this.tasteRating = tasteRating;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getServings() {
		return servings;
	}
	public void setServings(int servings) {
		this.servings = servings;
	}
	
	private float cost;
	private int healthRating;
	private int tasteRating;
	private String name;
	private int servings;
	
}
