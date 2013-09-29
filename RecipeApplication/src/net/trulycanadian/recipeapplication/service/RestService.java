package net.trulycanadian.recipeapplication.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.trulycanadian.recipeapplication.model.SimpleIngredients;
import net.trulycanadian.recipeapplication.model.SimpleRecipe;
import net.trulycanadian.recipeapplication.service.restretrievel.AuthRetrieval;
import net.trulycanadian.recipeapplication.service.restretrievel.GetRecipes;
import net.trulycanadian.recipeapplication.service.restretrievel.PostRecipe;
import net.trulycanadian.recipeapplication.service.restretrievel.SingleRecipe;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class RestService extends IntentService {
	public static final String TAG = RestService.class.getName();

	public static final int GETAUTH = 0x1;
	public static final int POST = 0x2;
	public static final int PUT = 0x3;
	public static final int DELETE = 0x4;
	public static final int POSTINGREDIENT = 0x5;
	public static final int POSTRECIPE = 0x6;
	public static final int GETRECIPES = 0x7;
	public static final int SINGLERECIPE = 0x8;

	public static final String EXTRA_HTTP_VERB = "net.trulycanadian.recipeapplication.EXTRA_HTTP_VERB";
	public static final String ARGS_PARAMS = "net.trulycanadian.recipeapplication.activity.ARGS_PARAMS";
	public static final String EXTRA_RESULT_RECEIVER = "net.trulycanadian.recipeapplication.EXTRA_RESULT_RECEIVER";

	public static final String REST_COMMAND = "net.trulycanadian.recipeapplication.COMMAND";

	public RestService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// When an intent is received by this Service, this method
		// is called on a new thread.
		Bundle resultData = new Bundle();
		Uri action = intent.getData();
		Bundle extras = intent.getExtras();

		if (extras == null || action == null
				|| !extras.containsKey(EXTRA_RESULT_RECEIVER)) {
			// Extras contain our ResultReceiver and data is our REST action.
			// So, without these components we can't do anything useful.
			Log.e(TAG, "You did not pass extras or data with the Intent.");

			return;
		}
		// We default to GET if no verb was specified.
		int verb = extras.getInt(EXTRA_HTTP_VERB, GETAUTH);
		Bundle params = extras.getParcelable(ARGS_PARAMS);
		SimpleRecipe recipe = (SimpleRecipe) extras.get("recipe");
		ResultReceiver receiver = extras.getParcelable(EXTRA_RESULT_RECEIVER);

		try {
			// Here we define our base request object which we will
			// send to our REST service via HttpClient.
			HttpRequestBase request = null;
			ArrayList<SimpleIngredients> ingredients = (ArrayList<SimpleIngredients>) extras
					.get("ingredients");
			// Let's build our request based on the HTTP verb we were
			// given.
			int status;
			switch (verb) {
			case SINGLERECIPE:
				SingleRecipe singleRecipe = new SingleRecipe();
				singleRecipe.setAction(action);
				resultData = singleRecipe.retrieveResults(params);
				status = resultData.getInt("statuscode");
				receiver.send(status, resultData);
				break;

			case GETRECIPES:
				GetRecipes webCommand = new GetRecipes();
				webCommand.setAction(action);
				resultData = webCommand.retrieveResults(params);
				status = resultData.getInt("statuscode");
				receiver.send(status, resultData);

				break;

			case GETAUTH:
				AuthRetrieval authRetrieval = new AuthRetrieval();
				authRetrieval.setAction(action);
				resultData = authRetrieval.retrieveResults(params);
				status = resultData.getInt("statuscode");
				receiver.send(status, resultData);

				break;

			case DELETE: {
				request = new HttpDelete();
				attachUriWithQuery(request, action, params);
			}
				break;

			case POSTRECIPE:
				PostRecipe postRecipe = new PostRecipe();
				postRecipe.setAction(action);
				postRecipe.setRecipe(recipe);
				postRecipe.setIngredients(ingredients);
				resultData = postRecipe.retrieveResults(params);
				status = resultData.getInt("statuscode");
				receiver.send(status, resultData);
				break;
			}

		} catch (URISyntaxException e) {
			Log.e(TAG, "URI syntax was incorrect. " + verbToString(verb) + ": "
					+ action.toString(), e);
			receiver.send(0, null);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG,
					"A UrlEncodedFormEntity was created with an unsupported encoding.",
					e);
			receiver.send(0, null);
		} catch (ClientProtocolException e) {
			Log.e(TAG, "There was a problem when sending the request.", e);
			receiver.send(0, null);
		} catch (IOException e) {
			Log.e(TAG, "There was a problem when sending the request.", e);
			receiver.send(0, null);
		} catch (Exception e) {
			Log.e(TAG, "There was a problem when sending the request.", e);
			receiver.send(0, null);
		}
	}

	private static void attachUriWithQuery(HttpRequestBase request, Uri uri,
			Bundle params) {
		try {
			if (params == null) {
				// No params were given or they have already been
				// attached to the Uri.
				request.setURI(new URI(uri.toString()));
			} else {
				Uri.Builder uriBuilder = uri.buildUpon();

				// Loop through our params and append them to the Uri.
				for (BasicNameValuePair param : paramsToList(params)) {
					uriBuilder.appendQueryParameter(param.getName(),
							param.getValue());
				}

				uri = uriBuilder.build();
				request.setURI(new URI(uri.toString()));
			}
		} catch (URISyntaxException e) {
			Log.e(TAG, "URI syntax was incorrect: " + uri.toString(), e);
		}
	}

	private static String verbToString(int verb) {
		switch (verb) {
		case GETRECIPES:
			return "GETRECIPES";

		case GETAUTH:
			return "GET";

		case POSTINGREDIENT:
			return "POSTINGREDIENT";

		case SINGLERECIPE:
			return "SINGLERECIPE";
		case POSTRECIPE:
			return "POSTRECIPE";

		case PUT:
			return "PUT";

		case DELETE:
			return "DELETE";
		}

		return "";
	}

	private static List<BasicNameValuePair> paramsToList(Bundle params) {
		ArrayList<BasicNameValuePair> formList = new ArrayList<BasicNameValuePair>(
				params.size());

		for (String key : params.keySet()) {
			Object value = params.get(key);

			// We can only put Strings in a form entity, so we call the
			// toString()
			// method to enforce. We also probably don't need to check for null
			// here
			// but we do anyway because Bundle.get() can return null.
			if (value != null)
				formList.add(new BasicNameValuePair(key, value.toString()));
		}

		return formList;
	}

	public void setIngredients(String uuid,
			ArrayList<SimpleIngredients> ingredients) {
		for (SimpleIngredients ingredient : ingredients) {
			ingredient.setUuid(uuid);
		}
	}
}
