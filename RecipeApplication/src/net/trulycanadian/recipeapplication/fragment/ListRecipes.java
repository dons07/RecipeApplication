package net.trulycanadian.recipeapplication.fragment;

import java.util.ArrayList;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipeapplication.adapter.RecipeAdapter;
import net.trulycanadian.recipeapplication.model.RecipeSum;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ListRecipes extends ListFragment {
	private static String TAG = RestServiceFragment.class.getName();
	private View view;
	private RecipeAdapter recipeAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		;
	}

	public void setRecipeItems(ArrayList<RecipeSum> recipesums) {

		recipeAdapter = new RecipeAdapter(getActivity(),
				R.layout.listviewrecipeitem, recipesums);
		setListAdapter(recipeAdapter);
		recipeAdapter.notifyDataSetChanged();
		recipeAdapter.notifyDataSetInvalidated();
	}

	public View loadView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.recipelist, container, false);
		return view;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.recipelist, container, false);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		TextView clickedView = (TextView) v.findViewById(R.id.recipeId);

		MainActivity activity = (MainActivity) getActivity();
		activity.loadRecipe(clickedView.getText().toString());
	}

}
