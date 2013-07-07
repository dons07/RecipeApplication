package net.trulycanadian.recipeapplication.fragment;

import net.trulycanadian.recipeapplication.service.RestService;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;

public abstract class RESTResponderFragment extends Fragment {

	private ResultReceiver mReceiver;

	// We are going to use a constructor here to make our ResultReceiver,
	// but be careful because Fragments are required to have only zero-arg
	// constructors. Normally you don't want to use constructors at all
	// with Fragments.
	public RESTResponderFragment() {
		mReceiver = new ResultReceiver(new Handler()) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				if (resultData != null
						&& resultData
								.containsKey(RestService.REST_AUTHENTICATION)) {
					onRESTResult(
							resultCode,
							resultData
									.getString(RestService.REST_AUTHENTICATION),
							RestService.REST_AUTHENTICATION);
				} else if (resultData != null
						&& resultData.containsKey(RestService.REST_POST_RECIPE)) {
					resultData.getString(RestService.REST_POST_RECIPE);
					onRESTResult(resultCode,
							resultData.getString(RestService.REST_POST_RECIPE),
							RestService.REST_POST_RECIPE);
				} else if (resultData != null
						&& resultData.containsKey(RestService.REST_GET_RECIPES)) {
					resultData.getString(RestService.REST_GET_RECIPES);
					onRESTResultJson(resultCode,
							RestService.REST_GET_RECIPES,resultData);
				} else {
					onRESTResult(resultCode, null, null);
				}
			}

		};
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// This tells our Activity to keep the same instance of this
		// Fragment when the Activity is re-created during lifecycle
		// events. This is what we want because this Fragment should
		// be available to receive results from our RESTService no
		// matter what the Activity is doing.
		setRetainInstance(true);
	}

	public ResultReceiver getResultReceiver() {
		return mReceiver;
	}

	// Implementers of this Fragment will handle the result here.
	abstract public void onRESTResult(int code, String result, String returnType);
	abstract public void onRESTResultJson(int code, String type, Bundle result);
}
