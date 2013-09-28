package net.trulycanadian.recipeapplication.fragment;

import java.util.ArrayList;

import net.trulycanadian.recipeapplication.activity.LoginActivity;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipeapplication.command.Command;
import net.trulycanadian.recipeapplication.command.CommandFactory;
import net.trulycanadian.recipeapplication.service.RestService;
import net.trulycanadian.recipleapplication.model.SimpleIngredients;
import net.trulycanadian.recipleapplication.model.SimpleRecipe;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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

	public void onRESTResult(int code, Bundle result) {

		Command command = CommandFactory.createCommand(result
				.getInt(RestService.REST_COMMAND));
		if (result.getInt(RestService.REST_COMMAND) != RestService.GETAUTH) {
			System.out.println("inside main");
			MainActivity activity = (MainActivity) getActivity();
			command.setActivity(activity);
		} else {
			System.out.println("inside auth");
			LoginActivity activity2 = (LoginActivity) getActivity();
			command.setLoginActivity(activity2);
		}
		command.parseWebResult(result);

	}
}
