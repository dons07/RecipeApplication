package net.trulycanadian.recipeapplication.service.restretrievel;

import net.trulycanadian.recipeapplication.service.RestService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.util.Base64;

public class GetRecipes extends WebCommand {

	public Bundle retrieveResults(Bundle params) throws Exception {
		HttpRequestBase request = null;
		Bundle resultData = new Bundle();
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
		System.out.println("got here inside retrieveResults");
		resultData.putInt(RestService.REST_COMMAND, RestService.GETRECIPES);
		if (request != null) {
			HttpClient client = new DefaultHttpClient();

			HttpResponse response = client.execute(request);
			HttpEntity responseEntity = response.getEntity();
			resultData.putString("json", EntityUtils.toString(responseEntity));
			StatusLine responseStatus = response.getStatusLine();
			int statusCode = responseStatus != null ? responseStatus
					.getStatusCode() : 0;
			resultData.putInt("statuscode", statusCode);

		}
		return resultData;
	}
}
