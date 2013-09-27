package net.trulycanadian.recipleapplication.model;

import java.util.ArrayList;

public class RecipeDetailed extends RecipeSum {

	public ArrayList<SimpleIngredients> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<SimpleIngredients> ingredients) {
		this.ingredients = ingredients;
	}
	private ArrayList<SimpleIngredients> ingredients;
	
}
