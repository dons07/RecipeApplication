package net.trulycanadian.recipeapplication.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import net.trulycanadian.recipeapplication.activity.LoginActivity;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipeapplication.service.RestService;
import net.trulycanadian.recipleapplication.model.RecipeDetailed;
import net.trulycanadian.recipleapplication.model.RecipeSum;
import net.trulycanadian.recipleapplication.model.SimpleIngredients;
import net.trulycanadian.recipleapplication.model.SimpleRecipe;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestAssuredServiceFragment extends RESTResponderFragment {

	private static String TAG = RestAssuredServiceFragment.class.getName();

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// This gets called each time our Activity has finished creating itself.

	}

	public void getSingleRecipe(String id) {
		MainActivity activity = (MainActivity) getActivity();
		Bundle params = activity.getUserBundle();
		System.out.println(params.getString("username"));
		Intent intent = new Intent(activity, RestService.class);
		intent.setData(Uri
				.parse("http://rental.trulycanadian.net:8080/recipe/api/recipe/"
						+ id));
		intent.putExtra(RestService.EXTRA_HTTP_VERB, RestService.SINGLERECIPE);
		intent.putExtra(RestService.ARGS_PARAMS, params);
		intent.putExtra(RestService.EXTRA_RESULT_RECEIVER, getResultReceiver());

		if (intent == null) {
			System.out.println("error");

		} else {
		}
		// Here we send our Intent to our RESTService.
		activity.startService(intent);
		if (activity != null) {
			// Here we check to see if our activity is null or not.
			// We only want to update our views if our activity exists.

			// Load our list adapter with our Tweets.
		}
	}

	public void getRecipes() {
		MainActivity activity = (MainActivity) getActivity();
		Bundle params = activity.getUserBundle();
		Intent intent = new Intent(activity, RestService.class);
		intent.setData(Uri
				.parse("http://rental.trulycanadian.net:8080/recipe/api/recipe"));
		intent.putExtra(RestService.EXTRA_HTTP_VERB, RestService.GETRECIPES);
		intent.putExtra(RestService.ARGS_PARAMS, params);
		intent.putExtra(RestService.EXTRA_RESULT_RECEIVER, getResultReceiver());

		if (intent == null) {
			System.out.println("error");

		} else {
		}
		// Here we send our Intent to our RESTService.
		activity.startService(intent);
		if (activity != null) {
			// Here we check to see if our activity is null or not.
			// We only want to update our views if our activity exists.

			// Load our list adapter with our Tweets.
		}

	}

	public void insertRecipe(SimpleRecipe recipe,
			ArrayList<SimpleIngredients> ingredients) {
		MainActivity activity = (MainActivity) getActivity();
		Bundle params = activity.getUserBundle();
		Intent intent = new Intent(activity, RestService.class);
		intent.setData(Uri
				.parse("http://rental.trulycanadian.net:8080/recipe/api/recipe"));
		intent.putExtra(RestService.EXTRA_HTTP_VERB, RestService.POSTRECIPE);
		intent.putExtra(RestService.ARGS_PARAMS, params);
		intent.putExtra(RestService.EXTRA_RESULT_RECEIVER, getResultReceiver());
		intent.putExtra("recipe", recipe);
		intent.putExtra("ingredients", ingredients);

		if (intent == null) {
			System.out.println("error");

		} else {
		}
		// Here we send our Intent to our RESTService.
		activity.startService(intent);
		if (activity != null) {
			// Here we check to see if our activity is null or not.
			// We only want to update our views if our activity exists.

			// Load our list adapter with our Tweets.
		}

	}

	public void checkAuthenticaiton() {

		LoginActivity activity = (LoginActivity) getActivity();
		Intent intent = new Intent(activity, RestService.class);
		intent.setData(Uri
				.parse("http://rental.trulycanadian.net:8080/recipe/api/userCred"));

		// Here we are going to place our REST call parameters. Note that
		// we could have just used Uri.Builder and appendQueryParameter()
		// here, but I wanted to illustrate how to use the Bundle params.
		Bundle params = activity.getUserBundle();

		intent.putExtra(RestService.ARGS_PARAMS, params);
		intent.putExtra(RestService.EXTRA_RESULT_RECEIVER, getResultReceiver());

		// Here we send our Intent to our RESTService.
		activity.startService(intent);
		if (activity != null) {
			// Here we check to see if our activity is null or not.
			// We only want to update our views if our activity exists.

			// Load our list adapter with our Tweets.
		}
	}

	@Override
	public void onRESTResult(int code, String result, String returnType) {
		// Here is where we handle our REST response. This is similar to the
		// LoaderCallbacks<D>.onLoadFinished() call from the previous tutorial.

		// Check to see if we got an HTTP 200 code and have some data.
		if (code == 200 && returnType == RestService.REST_AUTHENTICATION) {
			LoginActivity activity = (LoginActivity) getActivity();
			activity.startMainActivity();
			// For really complicated JSON decoding I usually do my heavy
			// lifting
			// with Gson and proper model classes, but for now let's keep it
			// simple
			// and use a utility method that relies on some of the built in
			// JSON utilities on Android.

		} else if (code == HttpStatus.SC_CREATED
				&& returnType == RestService.REST_POST_RECIPE) {
			Activity activity = getActivity();
			Toast.makeText(activity, "Inserted Recipe and ingredients",
					Toast.LENGTH_SHORT).show();
		} else if (code == 401 && returnType == RestService.REST_AUTHENTICATION) {
			LoginActivity activity = (LoginActivity) getActivity();
			activity.clearUserName();
			Toast.makeText(activity, "Invalid user name or password.",
					Toast.LENGTH_SHORT).show();

		} else {
			System.out.println(returnType + " " + code);
		}
	}

	public void onRESTResultJson(int code, String returnType, Bundle result) {

		System.out.println(returnType + code);
		if (code == HttpStatus.SC_OK
				&& returnType == RestService.REST_GET_RECIPES) {

			String json = result.getString("json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			RecipeSum[] recipeSums = gson.fromJson(json, RecipeSum[].class);
			ArrayList<RecipeSum> recipeSumsArray = new ArrayList<RecipeSum>(
					Arrays.asList(recipeSums));
			MainActivity activity = (MainActivity) getActivity();
			activity.setRecipes(recipeSumsArray);
		}
		if (code == HttpStatus.SC_OK
				&& returnType == RestService.REST_SINGLE_RECIPE) {
			String json = result.getString("json");
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
