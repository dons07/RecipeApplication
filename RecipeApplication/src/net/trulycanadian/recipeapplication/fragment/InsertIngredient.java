package net.trulycanadian.recipeapplication.fragment;

import java.util.ArrayList;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipeapplication.model.SimpleIngredients;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertIngredient extends Fragment {
	View view;
	private ArrayList<SimpleIngredients> ingredients;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.ingredientadd, container, false);
		ingredients = new ArrayList<SimpleIngredients> ();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Button ingredientButton = (Button) view
				.findViewById(R.id.enterNewIngredient);
		ingredientButton.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(final View v) {
				EditText name = (EditText) view
						.findViewById(R.id.ingredientName);
				EditText serving = (EditText) view
						.findViewById(R.id.ingredientServing);
				EditText unit = (EditText) view
						.findViewById(R.id.ingredientUnit);
				SimpleIngredients ingredient = new SimpleIngredients();
				ingredient.setName(name.getText().toString());
				ingredient.setUnit(unit.getText().toString());
				ingredient.setMeasurement(Double.parseDouble(serving.getText().toString()));				
				ingredients.add(ingredient);
				name.setText("");
				unit.setText("");
				serving.setText("");
				Activity activity = getActivity();
				Toast.makeText(
						activity,
						"Entered ingredient to list.",
						Toast.LENGTH_SHORT).show();

				

			}

		});
		final Button saveIngredientButton = (Button) view
				.findViewById(R.id.finishIngredient);
		saveIngredientButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				
				MainActivity activity = (MainActivity) getActivity();
				activity.setIngredients(ingredients);
				activity.replaceView(1);
				activity.saveRecipe();
			}

		});
		
	}
}
