package net.trulycanadian.recipeapplication.fragment;

import net.trulycanadian.recipeapplication.activity.MainActivity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class ListMenu extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] values = new String[] { "List Most Recent Recipes",
				"Search For A Recipe", "Insert a new Recipe",
				"Search for a user", "Recipe of the day" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		TextView clickedView = (TextView) v;
		if (clickedView.getText().toString().contains("Insert a new Recipe"))
		{
			MainActivity activity = (MainActivity) getActivity();
			activity.replaceView(1);
		}
	

	}
}
