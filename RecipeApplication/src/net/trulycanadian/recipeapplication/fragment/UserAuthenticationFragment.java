package net.trulycanadian.recipeapplication.fragment;

import net.trulycanadian.recipeapplication.activity.LoginActivity;
import net.trulycanadian.recipeapplication.service.RestService;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class UserAuthenticationFragment extends RESTResponderFragment {

	private static String TAG = UserAuthenticationFragment.class.getName();

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// This gets called each time our Activity has finished creating itself.

	}

	public void checkAuthenticaiton() {

		LoginActivity activity = (LoginActivity) getActivity();
		Intent intent = new Intent(activity, RestService.class);
		intent.setData(Uri.parse("http://rental.trulycanadian.net:8080/recipe/api/userCred"));

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
	public void onRESTResult(int code, String result) {
		// Here is where we handle our REST response. This is similar to the
		// LoaderCallbacks<D>.onLoadFinished() call from the previous tutorial.

		// Check to see if we got an HTTP 200 code and have some data.
		if (code == 200 && result != null) {
			LoginActivity activity = (LoginActivity) getActivity();
			activity.startMainActivity();
			// For really complicated JSON decoding I usually do my heavy
			// lifting
			// with Gson and proper model classes, but for now let's keep it
			// simple
			// and use a utility method that relies on some of the built in
			// JSON utilities on Android.

		} else {
			LoginActivity activity = (LoginActivity) getActivity();
			activity.clearUserName();
			Toast.makeText(
					activity,
					"Invalid user name or password.",
					Toast.LENGTH_SHORT).show();

		}
	}
}
