package net.trulycanadian.recipeapplication.fragment;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipleapplication.model.RecipeDetailed;
import net.trulycanadian.recipleapplication.model.SimpleIngredients;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SingleRecipe extends Fragment {
	View view;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void fillInData(RecipeDetailed detailed) {

		TextView recipeName = (TextView) view
				.findViewById(R.id.recipeNameDetailed);
		recipeName.setText(detailed.getName());
		TextView userCreated = (TextView) view.findViewById(R.id.userCreated);
		userCreated.setText(detailed.getUserinfo().getUserName());
		TextView directions = (TextView) view.findViewById(R.id.directions);
		directions.setText(detailed.getDirections());
		// reference the table layout
		TextView healthRating = (TextView) view.findViewById(R.id.healthRatingCount);
		healthRating.setText(detailed.getHealthrating().toString());
		TableLayout tbl = (TableLayout) view
				.findViewById(R.id.ingredientsTable);
		TextView tasteRating = (TextView) view.findViewById(R.id.tasteRatingCount);
		tasteRating.setText(detailed.getTasteRating().toString());
		
		// delcare a new row
		for (SimpleIngredients ingredient : detailed.getIngredients()) {
			TableRow newRow = new TableRow(getActivity());
			// add views to the row
			TextView ingredients = new TextView(getActivity());
			TextView measurments = new TextView(getActivity());
			TextView units = new TextView(getActivity());
			ingredients.setText(ingredient.getName());
			measurments.setText(ingredient.getMeasurement().toString());
			units.setText(ingredient.getUnit());
			newRow.addView(ingredients);
			newRow.addView(measurments);
			newRow.addView(units);
			tbl.addView(newRow);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.recipedetailed, container, false);
		return view;
	}
}
