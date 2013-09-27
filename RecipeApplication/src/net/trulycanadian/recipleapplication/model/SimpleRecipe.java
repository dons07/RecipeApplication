package net.trulycanadian.recipleapplication.model;

import java.io.Serializable;

public class SimpleRecipe implements Serializable {

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
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
	private Integer tasteRating;
	private String name;
	private int servings;
	private Integer healthrating;
	



	public Integer getTasteRating() {
		return tasteRating;
	}

	public void setTasteRating(Integer tasteRating) {
		this.tasteRating = tasteRating;
	}

	public Integer getHealthrating() {
		return healthrating;
	}

	public void setHealthrating(Integer healthrating) {
		this.healthrating = healthrating;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	private String directions;

}
