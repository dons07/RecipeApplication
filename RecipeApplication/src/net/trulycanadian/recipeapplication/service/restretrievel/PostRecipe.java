package net.trulycanadian.recipeapplication.service.restretrievel;

import java.net.URI;
import java.util.ArrayList;

import net.trulycanadian.recipeapplication.model.SimpleIngredients;
import net.trulycanadian.recipeapplication.model.SimpleRecipe;
import net.trulycanadian.recipeapplication.model.uuid;
import net.trulycanadian.recipeapplication.service.RestService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PostRecipe extends WebCommand {

	Bundle resultData = new Bundle();

	public SimpleRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(SimpleRecipe recipe) {
		this.recipe = recipe;
	}

	public ArrayList<SimpleIngredients> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<SimpleIngredients> ingredients) {
		this.ingredients = ingredients;
	}

	SimpleRecipe recipe;
	ArrayList<SimpleIngredients> ingredients;

	public Bundle executeStatement(Bundle params) throws Exception {
		processRecipe(params);
		processIngredients(params);
		return resultData;
	}

	public void processRecipe(Bundle params) throws Exception {
		String json;
		HttpRequestBase request = null;

		resultData.putInt(RestService.REST_COMMAND, RestService.POSTRECIPE);
		request = new HttpPost();
		request.setURI(new URI(action.toString()));

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		json = gson.toJson(recipe);
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
			
			HttpResponse response = client.execute(postRequest);

			HttpEntity responseEntity = response.getEntity();
			gson = new GsonBuilder().setPrettyPrinting().create();
			uuid uuid = gson.fromJson(EntityUtils.toString(responseEntity),
					uuid.class);

			setIngredients2(uuid.getUuid(), ingredients);
		}
	}

	public void processIngredients(Bundle params) throws Exception {
		String json;
		HttpPost postRequest = new HttpPost(
				"http://rental.trulycanadian.net:8080/recipe/api/ingredients/jsonArray");
		String combined = params.getString("username") + ":"
				+ params.getString("password");
		postRequest.setHeader(
				"Authorization",
				"Basic "
						+ Base64.encodeToString(combined.getBytes(),
								Base64.NO_WRAP));
		if (postRequest != null) {
			HttpClient client = new DefaultHttpClient();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			json = gson.toJson(ingredients);
			System.out.println(json);
			StringEntity entity = new StringEntity(json, HTTP.UTF_8);
			entity.setContentType("application/json");
			postRequest.setEntity(entity);
			HttpResponse response = client.execute(postRequest);
			StatusLine responseStatus = response.getStatusLine();
			int statusCode = responseStatus != null ? responseStatus
					.getStatusCode() : 0;
			System.out.println("got to status code " + statusCode);
			resultData.putInt(RestService.REST_COMMAND,
					RestService.POSTRECIPE);
			resultData.putInt("statuscode", statusCode);
	}
	}
	public void setIngredients2(String uuid,
			ArrayList<SimpleIngredients> ingredients) {
		for (SimpleIngredients ingredient : ingredients) {
			ingredient.setUuid(uuid);
		}
	}
}
