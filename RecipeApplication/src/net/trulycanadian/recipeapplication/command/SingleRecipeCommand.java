package net.trulycanadian.recipeapplication.command;

import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipleapplication.model.RecipeDetailed;

import org.apache.http.HttpStatus;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SingleRecipeCommand extends Command {
	public void parseWebResult(Bundle bundle) {
		if (bundle.getInt("statuscode") == HttpStatus.SC_OK) {
			String json = bundle.getString("json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			MainActivity activity = (MainActivity) getActivity();
			RecipeDetailed detailed = gson.fromJson(json, RecipeDetailed.class);
			System.out.println(json);
			System.out.println(detailed.getName());
			System.out.println(detailed.getHealthrating());
			activity.setDetailedRecipe(detailed);

		}
	}
}
