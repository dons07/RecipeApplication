package net.trulycanadian.recipeapplication.activity;

import net.trulycanadian.recipeapplication.R;
import net.trulycanadian.recipeapplication.algo.PassEncoding;
import net.trulycanadian.recipeapplication.fragment.RestAssuredServiceFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends FragmentActivity {

	private static final String ARGS_URI = "net.trulycanadian.recipeapplication.activity.ARGS_URI";
	private static final String ARGS_PARAMS = "net.trulycanadian.recipeapplication.activity.ARGS_PARAMS";

	String userName, passWord;
	EditText username, password;
	Button login;
	SharedPreferences appPrefs1;
	String prefs1 = "recipeapplication";
	SharedPreferences.Editor appPrefsEditor;
	RestAssuredServiceFragment responder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		// UI elements gets bind in form of Java Objects
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		// Since we are using the Android Compatibility library
		// we have to use FragmentActivity. So, we use ListFragment
		// to get the same functionality as ListActivity.

		// Let's set our list adapter to a simple ArrayAdapter.

		// RESTResponderFragments call setRetainedInstance(true) in their
		// onCreate() method. So that means
		// we need to check if our FragmentManager is already storing an
		// instance of the responder.
		responder = (RestAssuredServiceFragment) fm
				.findFragmentByTag("RESTResponder");
		if (responder == null) {
			responder = new RestAssuredServiceFragment();

			// We add the fragment using a Tag since it has no views. It will
			// make the Twitter REST call
			// for us each time this Activity is created.
			ft.add(responder, "RESTResponder");
		}

		// Make sure you commit the FragmentTransaction or your fragments
		// won't get added to your FragmentManager. Forgetting to call
		// ft.commit()
		// is a really common mistake when starting out with Fragments.
		ft.commit();
		appPrefs1 = getSharedPreferences(prefs1, MODE_PRIVATE);
		appPrefsEditor = appPrefs1.edit();
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		userName = appPrefs1.getString("username", "");

		login = (Button) findViewById(R.id.login);
		// now we have got the handle over the UI widgets
		// setting listener on Login Button
		// i.e. OnClick Event

		login.setOnClickListener(loginListener);

	}

	@Override
	public void onStart() {
		super.onStart();
		String encodedPassword = appPrefs1.getString("password", "");
		if (encodedPassword != "")
			passWord = PassEncoding.decode(encodedPassword);
		else
			passWord = "";
		if (userName != "") {
			username.setVisibility(View.GONE);
			password.setVisibility(View.GONE);
			testUserAuthentication();
			login.setText("Logging In Please Wait");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void clearUserName() {
		userName = "";
		passWord = "";
		username.setVisibility(View.VISIBLE);
		password.setVisibility(View.VISIBLE);
		login.setText("Login");
		appPrefsEditor.putString("username", "");
		appPrefsEditor.putString("password", "");
		appPrefsEditor.commit();
	}

	public Bundle getUserBundle() {
		Uri loginUri = Uri
				.parse("http://rental.trulycanadian.net:8080/recipe/api/userCred");
		Bundle params = new Bundle();
		params.putString("username", userName);
		params.putString("password", passWord);

		return params;

	}

	private void testUserAuthentication() {
		responder.checkAuthenticaiton();
		System.out.println("got to test user authenticasiton");

	}

	public void startMainActivity() {
		appPrefsEditor.putString("username", userName);
		appPrefsEditor.putString("password", PassEncoding.encode(passWord));
		appPrefsEditor.commit();

		Intent i2 = new Intent(this, MainActivity.class);
		startActivity(i2);
	}

	private OnClickListener loginListener = new OnClickListener() {
		public void onClick(View v) {
			// getting inputs from user and performing data operations

			// getting inputs from user and performing data operations
			userName = username.getText().toString();
			passWord = password.getText().toString();
			testUserAuthentication();

			/*
			 * HttpResponse response = null;
			 * 
			 * 
			 * if (android.os.Build.VERSION.SDK_INT > 9) {
			 * StrictMode.ThreadPolicy policy = new
			 * StrictMode.ThreadPolicy.Builder() .permitAll().build();
			 * StrictMode.setThreadPolicy(policy); }
			 * 
			 * try { HttpClient client = new DefaultHttpClient(); HttpGet
			 * request = new
			 * HttpGet("http://rental.trulycanadian.net:8080/recipe/api/recipe"
			 * );
			 * 
			 * 
			 * String combined = userName + ":" + passWord; request.setHeader(
			 * "Authorization", "Basic " +
			 * Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP));
			 * response = client.execute(request);
			 * 
			 * } catch (Exception ex) {
			 * 
			 * ex.printStackTrace(); }
			 * 
			 * if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			 * { // responding to the User inputs
			 * Toast.makeText(getApplicationContext(), "Login Successfully !!!",
			 * Toast.LENGTH_LONG).show(); } else
			 * Toast.makeText(getApplicationContext(),
			 * "Login Not Successful !!!", Toast.LENGTH_LONG).show();
			 */
		}
	};
}
