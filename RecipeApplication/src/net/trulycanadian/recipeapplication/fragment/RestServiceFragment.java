package net.trulycanadian.recipeapplication.fragment;

import java.util.ArrayList;

import net.trulycanadian.recipeapplication.activity.LoginActivity;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipeapplication.command.Command;
import net.trulycanadian.recipeapplication.command.CommandFactory;
import net.trulycanadian.recipeapplication.receiver.WebServiceReceiver;
import net.trulycanadian.recipeapplication.service.RestService;
import net.trulycanadian.recipleapplication.model.SimpleIngredients;
import net.trulycanadian.recipleapplication.model.SimpleRecipe;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

public class RestServiceFragment extends Fragment {

	private static String TAG = RestServiceFragment.class.getName();
	private WebServiceReceiver receiver;
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		receiver = new WebServiceReceiver(new Handler());
		receiver.setContext(getActivity());
	}

	public void getSingleRecipe(String id) {
		MainActivity activity = (MainActivity) getActivity();
		Bundle params = activity.getUserBundle();
		Intent intent = new Intent(activity, RestService.class);
		intent.setData(Uri
				.parse("http://rental.trulycanadian.net:8080/recipe/api/recipe/"
						+ id));
		intent.putExtra(RestService.EXTRA_HTTP_VERB, RestService.SINGLERECIPE);
		intent.putExtra(RestService.ARGS_PARAMS, params);
		receiver.setContext(activity);
		intent.putExtra(RestService.EXTRA_RESULT_RECEIVER, receiver);
		activity.startService(intent);
	}

	public void getRecipes() {
		MainActivity activity = (MainActivity) getActivity();
		Bundle params = activity.getUserBundle();
		Intent intent = new Intent(activity, RestService.class);
		intent.setData(Uri
				.parse("http://rental.trulycanadian.net:8080/recipe/api/recipe"));
		intent.putExtra(RestService.EXTRA_HTTP_VERB, RestService.GETRECIPES);
		intent.putExtra(RestService.ARGS_PARAMS, params);
		receiver.setContext(activity);
		intent.putExtra(RestService.EXTRA_RESULT_RECEIVER, receiver);
		activity.startService(intent);
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
		receiver.setContext(activity);
		intent.putExtra(RestService.EXTRA_RESULT_RECEIVER, receiver);
		intent.putExtra("recipe", recipe);
		intent.putExtra("ingredients", ingredients);
		activity.startService(intent);
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
		receiver.setContext(activity);
		intent.putExtra(RestService.EXTRA_RESULT_RECEIVER, receiver);
		

		activity.startService(intent);
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
