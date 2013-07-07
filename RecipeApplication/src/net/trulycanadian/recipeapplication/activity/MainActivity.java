package net.trulycanadian.recipeapplication.activity;

import java.util.ArrayList;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipeapplication.fragment.InsertRecipe;
import net.trulycanadian.recipleapplication.model.SimpleIngredients;
import net.trulycanadian.recipleapplication.model.SimpleRecipe;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {
	private SimpleRecipe recipe;
	private ArrayList<SimpleIngredients> ingredients;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Fragment data = new Fragment();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
	}

	public void replaceView(int id) {
		// Create new fragment and transaction
		
		switch (id)
		{
		case 1:
			InsertRecipe recipe = new InsertRecipe();
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();

			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack
			transaction.replace(R.id.frag_data, recipe);
			transaction.commit();
			break;
			default:
				break;
		}
	}
	public void setRecipe(SimpleRecipe recipe)
	{
		this.recipe = recipe;
	}
	// Commit the transaction


}
