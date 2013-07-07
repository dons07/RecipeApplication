package net.trulycanadian.recipeapplication.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.trulycanadian.recipleapplication.model.SimpleIngredients;
import net.trulycanadian.recipleapplication.model.SimpleRecipe;
import net.trulycanadian.recipleapplication.model.uuid;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestService extends IntentService {
	public static final String TAG = RestService.class.getName();

	public static final int GETAUTH = 0x1;
	public static final int POST = 0x2;
	public static final int PUT = 0x3;
	public static final int DELETE = 0x4;
	public static final int POSTINGREDIENT = 0x5;
	public static final int POSTRECIPE = 0x6;
	public static final int GETRECIPES = 0x7;

	public static final String EXTRA_HTTP_VERB = "net.neilgoodman.android.restservicetutorial.EXTRA_HTTP_VERB";
	public static final String ARGS_PARAMS = "net.trulycanadian.recipeapplication.activity.ARGS_PARAMS";
	public static final String EXTRA_RESULT_RECEIVER = "net.neilgoodman.android.restservicetutorial.EXTRA_RESULT_RECEIVER";

	public static final String REST_AUTHENTICATION = "net.trulycanadian.AUTHENTICATION";
	public static final String REST_POST_RECIPE = "net.trulycanadian.POSTRECIPE";
	public static final String REST_GET_RECIPES = "net.trulycanadian.GETRECIPES";

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
		System.out.println("got here twice");
		// We default to GET if no verb was specified.
		int verb = extras.getInt(EXTRA_HTTP_VERB, GETAUTH);
		Bundle params = extras.getParcelable(ARGS_PARAMS);
		ResultReceiver receiver = extras.getParcelable(EXTRA_RESULT_RECEIVER);

		try {
			// Here we define our base request object which we will
			// send to our REST service via HttpClient.
			HttpRequestBase request = null;
			String json;
			ArrayList<SimpleIngredients> ingredients = (ArrayList<SimpleIngredients>) extras
					.get("ingredients");
			// Let's build our request based on the HTTP verb we were
			// given.
			switch (verb) {
			case GETRECIPES: {
				request = new HttpGet();
				System.out.println("got to get");
				String combined = params.getString("username") + ":"
						+ params.getString("password");
				System.out.println(combined);
				request.setHeader(
						"Authorization",
						"Basic "
								+ Base64.encodeToString(combined.getBytes(),
										Base64.NO_WRAP));
				attachUriWithQuery(request, action, params);

				resultData.putString(REST_GET_RECIPES, "GetRecipes");
				if (request != null) {
					HttpClient client = new DefaultHttpClient();

					// Let's send some useful debug information so we can
					// monitor
					// things
					// in LogCat.
					Log.d(TAG, "Executing request: " + verbToString(verb)
							+ ": " + action.toString());

					// Finally, we send our request using HTTP. This is the
					// synchronous
					// long operation that we need to run on this thread.
					HttpResponse response = client.execute(request);

					HttpEntity responseEntity = response.getEntity();
					resultData.putString("json",
							EntityUtils.toString(responseEntity));
					StatusLine responseStatus = response.getStatusLine();
					int statusCode = responseStatus != null ? responseStatus
							.getStatusCode() : 0;

					// Our ResultReceiver allows us to communicate back the
					// results
					// to the caller. This
					// class has a method named send() that can send back a code
					// and
					// a Bundle
					// of data. ResultReceiver and IntentService abstract away
					// all
					// the IPC code
					// we would need to write to normally make this work.
					if (responseEntity != null) {
						receiver.send(statusCode, resultData);
					} else {
						receiver.send(statusCode, null);
					}
				}
			}
				break;

			case GETAUTH: {
				request = new HttpGet();
				System.out.println("got to get");
				String combined = params.getString("username") + ":"
						+ params.getString("password");
				System.out.println(combined);
				request.setHeader(
						"Authorization",
						"Basic "
								+ Base64.encodeToString(combined.getBytes(),
										Base64.NO_WRAP));
				attachUriWithQuery(request, action, params);

				resultData.putString(REST_AUTHENTICATION, "Authentication");
				if (request != null) {
					HttpClient client = new DefaultHttpClient();

					// Let's send some useful debug information so we can
					// monitor
					// things
					// in LogCat.
					Log.d(TAG, "Executing request: " + verbToString(verb)
							+ ": " + action.toString());

					// Finally, we send our request using HTTP. This is the
					// synchronous
					// long operation that we need to run on this thread.
					HttpResponse response = client.execute(request);

					HttpEntity responseEntity = response.getEntity();
					StatusLine responseStatus = response.getStatusLine();
					int statusCode = responseStatus != null ? responseStatus
							.getStatusCode() : 0;
					System.out.println("got to status code " + statusCode);
					// Our ResultReceiver allows us to communicate back the
					// results
					// to the caller. This
					// class has a method named send() that can send back a code
					// and
					// a Bundle
					// of data. ResultReceiver and IntentService abstract away
					// all
					// the IPC code
					// we would need to write to normally make this work.
					if (responseEntity != null) {
						receiver.send(statusCode, resultData);
					} else {
						receiver.send(statusCode, null);
					}
				}
			}
				break;

			case DELETE: {
				request = new HttpDelete();
				attachUriWithQuery(request, action, params);
			}
				break;

			case POSTRECIPE: {
				resultData.putString(REST_POST_RECIPE, "Authentication");
				request = new HttpPost();
				request.setURI(new URI(action.toString()));

				SimpleRecipe recipe = (SimpleRecipe) extras.get("recipe");

				Gson gson = new GsonBuilder().setPrettyPrinting().create();

				json = gson.toJson(recipe);// Attach form entity if necessary.
											// Note: some REST APIs
				// require you to POST JSON. This is easy to do, simply use
				// postRequest.setHeader('Content-Type', 'application/json')
				// and StringEntity instead. Same thing for the PUT case
				// below.
				String combined = params.getString("username") + ":"
						+ params.getString("password");
				HttpPost postRequest = (HttpPost) request;
				request.setHeader(
						"Authorization",
						"Basic "
								+ Base64.encodeToString(combined.getBytes(),
										Base64.NO_WRAP));
				if (request != null) {
					HttpClient client = new DefaultHttpClient();

					StringEntity entity = new StringEntity(json, HTTP.UTF_8);
					entity.setContentType("application/json");
					postRequest.setEntity(entity);
					// Let's send some useful debug information so we can
					// monitor
					// things
					// in LogCat.
					Log.d(TAG, "Executing request: " + verbToString(verb)
							+ ": " + action.toString());

					// Finally, we send our request using HTTP. This is the
					// synchronous
					// long operation that we need to run on this thread.
					HttpResponse response = client.execute(postRequest);

					HttpEntity responseEntity = response.getEntity();
					gson = new GsonBuilder().setPrettyPrinting().create();
					uuid uuid = gson.fromJson(
							EntityUtils.toString(responseEntity), uuid.class);

					setIngredients(uuid.getUuid(), ingredients);

					StatusLine responseStatus = response.getStatusLine();
					int statusCode = responseStatus != null ? responseStatus
							.getStatusCode() : 0;
					System.out.println("got to status code " + statusCode);
					// Our ResultReceiver allows us to communicate back the
					// results
					// to the caller. This
					// class has a method named send() that can send back a code
					// and
					// a Bundle
					// of data. ResultReceiver and IntentService abstract away
					// all
					// the IPC code
					// we would need to write to normally make this work.
					// if (responseEntity != null) {
					// receiver.send(statusCode, resultData);
					// } else {
					// receiver.send(statusCode, null);
					// }
				}
			}

			case POSTINGREDIENT: {

				// Attach form entity if necessary. Note: some REST APIs
				// require you to POST JSON. This is easy to do, simply use
				// postRequest.setHeader('Content-Type', 'application/json')
				// and StringEntity instead. Same thing for the PUT case
				// below.
				HttpPost postRequest = new HttpPost(
						"http://rental.trulycanadian.net:8080/recipe/api/ingredients/jsonArray");
				String combined = params.getString("username") + ":"
						+ params.getString("password");
				postRequest.setHeader(
						"Authorization",
						"Basic "
								+ Base64.encodeToString(combined.getBytes(),
										Base64.NO_WRAP));

				if (request != null) {
					HttpClient client = new DefaultHttpClient();
					Gson gson = new GsonBuilder().setPrettyPrinting().create();

					json = gson.toJson(ingredients);
					System.out.println(json);
					StringEntity entity = new StringEntity(json, HTTP.UTF_8);
					entity.setContentType("application/json");
					postRequest.setEntity(entity);

					// Let's send some useful debug information so we can
					// monitor
					// things
					// in LogCat.
					Log.d(TAG, "Executing request: " + verbToString(verb)
							+ ": " + action.toString());

					// Finally, we send our request using HTTP. This is the
					// synchronous
					// long operation that we need to run on this thread.
					HttpResponse response = client.execute(postRequest);
					HttpEntity responseEntity = response.getEntity();
					StatusLine responseStatus = response.getStatusLine();
					int statusCode = responseStatus != null ? responseStatus
							.getStatusCode() : 0;
					System.out.println("got to status code " + statusCode);
					// Our ResultReceiver allows us to communicate back the
					// results
					// to the caller. This
					// class has a method named send() that can send back a code
					// and
					// a Bundle
					// of data. ResultReceiver and IntentService abstract away
					// all
					// the IPC code
					// we would need to write to normally make this work.
					if (responseEntity != null) {
						receiver.send(statusCode, resultData);
					} else {
						receiver.send(statusCode, null);
					}
				}

			}
				break;

			case PUT: {
				request = new HttpPut();
				request.setURI(new URI(action.toString()));

				// Attach form entity if necessary.
				HttpPut putRequest = (HttpPut) request;

				if (params != null) {
					UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
							paramsToList(params));
					putRequest.setEntity(formEntity);
				}
			}
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
