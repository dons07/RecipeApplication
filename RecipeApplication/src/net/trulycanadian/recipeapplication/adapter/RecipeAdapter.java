package net.trulycanadian.recipeapplication.adapter;

import java.util.ArrayList;
import java.util.List;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipeapplication.model.RecipeSum;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecipeAdapter extends ArrayAdapter<RecipeSum> {

	private ArrayList<RecipeSum> items;
	private final LayoutInflater mInflater;

	public RecipeAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_2);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<RecipeSum> data) {
		clear();
		if (data != null) {
			for (RecipeSum appEntry : data) {
				add(appEntry);
			}
		}
	}

	public RecipeAdapter(Context context, int textViewResourceId,
			ArrayList<RecipeSum> items) {
		super(context, textViewResourceId, items);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listviewrecipeitem, null);
		}
		RecipeSum o = items.get(position);
		if (o != null) {
			TextView id = (TextView) v.findViewById(R.id.recipeId);
 			TextView recipeName = (TextView) v.findViewById(R.id.recipeName);
			TextView healthRating = (TextView) v
					.findViewById(R.id.healthRating);
			TextView tasteRating = (TextView) v.findViewById(R.id.tasteRating);
			TextView userCreated = (TextView) v.findViewById(R.id.userCreated);
			TextView userRating = (TextView) v.findViewById(R.id.tasteRating);
			if (id != null )
			{
				id.setText(o.getId().toString());
			}
			if (recipeName != null) {
				recipeName.setText("Name: " + o.getName());
			}
			if (userCreated != null) {
				userCreated.setText("User Created: "
						+ o.getUserinfo().getUserName());
			}
			if (tasteRating != null) {
				tasteRating.setText("Taste Rating: " + o.getTasteRating());
			}
			if (healthRating != null) {
				healthRating.setText("Health Rating: " + o.getHealthrating());
			}

		}
		return v;
	}
}
