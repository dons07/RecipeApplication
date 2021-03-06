package net.trulycanadian.recipeapplication.activity;

import java.util.ArrayList;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipeapplication.adapter.RecipeAdapter;
import net.trulycanadian.recipeapplication.algo.PassEncoding;
import net.trulycanadian.recipeapplication.fragment.InsertIngredient;
import net.trulycanadian.recipeapplication.fragment.InsertRecipe;
import net.trulycanadian.recipeapplication.fragment.ListRecipes;
import net.trulycanadian.recipeapplication.fragment.RestServiceFragment;
import net.trulycanadian.recipeapplication.fragment.SingleRecipe;
import net.trulycanadian.recipeapplication.model.RecipeDetailed;
import net.trulycanadian.recipeapplication.model.RecipeSum;
import net.trulycanadian.recipeapplication.model.SimpleIngredients;
import net.trulycanadian.recipeapplication.model.SimpleRecipe;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {
	private SimpleRecipe recipe;
	private ArrayList<SimpleIngredients> ingredients;
	RestServiceFragment responder;

	SharedPreferences appPrefs1;
	String prefs1 = "recipeapplication";
	SharedPreferences.Editor appPrefsEditor;
	private RecipeAdapter recipeAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Fragment data = new Fragment();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		responder = (RestServiceFragment) fm
				.findFragmentByTag("RESTResponder");
		if (responder == null) {
			responder = new RestServiceFragment();
//** Add Fragment responder for Web Service Calls.
			
			ft.add(responder, "RESTResponder");
		}

		// Make sure you commit the FragmentTransaction or your fragments
		// won't get added to your FragmentManager. Forgetting to call
		// ft.commit()
		// is a really common mistake when starting out with Fragments.
		ft.commit();
	}

	public Bundle getUserBundle() {
		Uri loginUri = Uri
				.parse("http://rental.trulycanadian.net:8080/recipe/api/userCred");
		Bundle params = new Bundle();
		appPrefs1 = getSharedPreferences(prefs1, MODE_PRIVATE);
		appPrefsEditor = appPrefs1.edit();

		String userName = appPrefs1.getString("username", "");
		String passWord = "";
		String encodedPassword = appPrefs1.getString("password", "");
		if (encodedPassword != "")
			passWord = PassEncoding.decode(encodedPassword);

		params.putString("username", userName);
		params.putString("password", passWord);
		System.out.println(params.getString("username"));
		return params;

	}

	public void setDetailedRecipe(RecipeDetailed detailed) {
		FragmentManager fm = getSupportFragmentManager();

		SingleRecipe responder2 = (SingleRecipe) fm
				.findFragmentById(R.id.frag_data);
		responder2.fillInData(detailed);

	}

	public void loadRecipe(String number) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		SingleRecipe recipeFragment = new SingleRecipe();

		transaction.replace(R.id.frag_data, recipeFragment);
		transaction.commit();
		responder.getSingleRecipe(number);

	}

	public void replaceView(int id) {
		// Create new fragment and transaction
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		switch (id) {
		case 1:
			InsertRecipe recipe = new InsertRecipe();

			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack
			transaction.replace(R.id.frag_data, recipe);
			transaction.commit();
			break;
		case 12:
			InsertIngredient ingredient = new InsertIngredient();

			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack
			transaction.replace(R.id.frag_data, ingredient);
			transaction.commit();
			break;
		case 2:

			ListRecipes listRecipes = new ListRecipes();
			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack

			transaction.replace(R.id.frag_data, listRecipes);
			transaction.commit();
			responder.getRecipes();
			break;

		default:
			break;
		}
	}

	public RecipeAdapter getArrayAdapter() {
		return recipeAdapter;
	}

	public void setRecipes(ArrayList<RecipeSum> recipesums) {
		FragmentManager fm = getSupportFragmentManager();

		ListRecipes responder2 = (ListRecipes) fm
				.findFragmentById(R.id.frag_data);
		recipeAdapter = new RecipeAdapter(this, R.layout.listviewrecipeitem,
				recipesums);

		responder2.setRecipeItems(recipesums);
	}

	public void setIngredients(ArrayList<SimpleIngredients> ingredients) {
		this.ingredients = ingredients;
	}

	public void saveRecipe() {
		responder.insertRecipe(recipe, ingredients);

	}

	public void getRecipes() {
		responder.getRecipes();
	}

	public void setRecipe(SimpleRecipe recipe) {
		this.recipe = recipe;
	}

	// Commit the transaction

}
