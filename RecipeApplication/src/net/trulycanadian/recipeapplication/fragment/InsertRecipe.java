package net.trulycanadian.recipeapplication.fragment;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipleapplication.model.SimpleRecipe;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class InsertRecipe extends Fragment {
	View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Button loginButton = (Button) view
				.findViewById(R.id.enterIngredient);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				MainActivity activity = (MainActivity) getActivity();
				EditText name = (EditText) view.findViewById(R.id.recipeName);
				EditText cost = (EditText) view.findViewById(R.id.recipeCost);
				EditText healthRating = (EditText) view
						.findViewById(R.id.recipeHealthRating);
				EditText tasteRating = (EditText) view
						.findViewById(R.id.recipeTasteRating);
				EditText servings = (EditText) view
						.findViewById(R.id.recipeServings);

				EditText directions = (EditText) view
						.findViewById(R.id.directions);

				SimpleRecipe recipe = new SimpleRecipe();
				recipe.setName(name.getText().toString());
				recipe.setCost(Float.parseFloat(cost.getText().toString()));
				recipe.setHealthrating(Integer.parseInt(healthRating.getText()
						.toString()));
				recipe.setTasteRating(Integer.parseInt(tasteRating.getText()
						.toString()));
				recipe.setServings(Integer.parseInt(servings.getText()
						.toString()));
				recipe.setDirections(directions.getText().toString());
				activity.setRecipe(recipe);
				activity.replaceView(12);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.recipeadd, container, false);
		return view;
	}

}
