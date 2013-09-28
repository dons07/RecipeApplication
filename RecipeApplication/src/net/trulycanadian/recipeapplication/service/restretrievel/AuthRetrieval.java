package net.trulycanadian.recipeapplication.service.restretrievel;

import net.trulycanadian.recipeapplication.service.RestService;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.util.Base64;

public class AuthRetrieval extends WebCommand {

	public Bundle retrieveResults(Bundle params) throws Exception {
		Bundle resultData = new Bundle();
		HttpRequestBase request = null;
		request = new HttpGet();
		String combined = params.getString("username") + ":"
				+ params.getString("password");
		System.out.println(combined);
		request.setHeader(
				"Authorization",
				"Basic "
						+ Base64.encodeToString(combined.getBytes(),
								Base64.NO_WRAP));
		attachUriWithQuery(request, action, params);

		resultData.putInt(RestService.REST_COMMAND, RestService.GETAUTH);
		if (request != null) {
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);
			StatusLine responseStatus = response.getStatusLine();
			int statusCode = responseStatus != null ? responseStatus
					.getStatusCode() : 0;
			resultData.putInt("statuscode", statusCode);
		}
		return resultData;
	}
}
