package net.trulycanadian.recipeapplication.fragment;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipleapplication.model.SimpleRecipe;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class InsertRecipe extends Fragment {
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.recipeadd, container, false);
		return view;
	}
	
	public void goEnterIngredient(View v)
	{
		
		MainActivity activity = (MainActivity) getActivity();
		EditText name = (EditText) getView().findViewById(R.id.recipeName);
		EditText cost = (EditText) getView().findViewById(R.id.recipeCost);
		EditText healthRating = (EditText) getView().findViewById(R.id.recipeHealthRating);
		EditText tasteRating = (EditText) getView().findViewById(R.id.recipeTasteRating);
		EditText servings = (EditText) getView().findViewById(R.id.recipeServings);
		SimpleRecipe recipe = new SimpleRecipe();
		recipe.setName(name.getText().toString());
		recipe.setCost(Float.parseFloat(cost.getText().toString()));
		recipe.setHealthRating(Integer.parseInt(healthRating.getText().toString()));
		recipe.setTasteRating(Integer.parseInt(tasteRating.getText().toString()));
		recipe.setServings(Integer.parseInt(servings.getText().toString()));
		activity.setRecipe(recipe);
		activity.replaceView(10);
		}

}
