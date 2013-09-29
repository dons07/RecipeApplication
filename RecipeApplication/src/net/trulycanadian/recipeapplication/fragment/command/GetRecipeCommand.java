package net.trulycanadian.recipeapplication.fragment.command;

import java.util.ArrayList;
import java.util.Arrays;

import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipeapplication.model.RecipeSum;

import org.apache.http.HttpStatus;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetRecipeCommand extends Command {

	public void parseWebResult(Bundle bundle) {
		if (HttpStatus.SC_OK == bundle.getInt("statuscode")) {
			String json = bundle.getString("json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			RecipeSum[] recipeSums = gson.fromJson(json, RecipeSum[].class);
			ArrayList<RecipeSum> recipeSumsArray = new ArrayList<RecipeSum>(
					Arrays.asList(recipeSums));
			MainActivity activity = (MainActivity) getActivity();
			activity.setRecipes(recipeSumsArray);
		}
	}
}
