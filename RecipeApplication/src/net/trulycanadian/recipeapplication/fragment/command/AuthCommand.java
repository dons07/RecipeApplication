package net.trulycanadian.recipeapplication.fragment.command;

import android.os.Bundle;
import android.widget.Toast;

public class AuthCommand extends Command {
	public void parseWebResult(Bundle bundle) {
		if (bundle.getInt("statuscode") == 200) {
			System.out.println("got here");
			this.getLoginActivity().startMainActivity();
		}
		if (bundle.getInt("statuscode") == 401) {
			this.getLoginActivity().clearUserName();
			Toast.makeText(this.getLoginActivity(),
					"Invalid user name or password.", Toast.LENGTH_SHORT)
					.show();

		}
	}
}