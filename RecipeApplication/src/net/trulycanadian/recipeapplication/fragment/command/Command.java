package net.trulycanadian.recipeapplication.fragment.command;

import net.trulycanadian.recipeapplication.activity.LoginActivity;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import android.os.Bundle;

public abstract class  Command {
	
	private MainActivity activity;
	private LoginActivity loginActivity;
	
	
	public LoginActivity getLoginActivity() {
		return loginActivity;
	}

	public void setLoginActivity(LoginActivity loginActivity) {
		this.loginActivity = loginActivity;
	}

	public MainActivity getActivity() {
		return activity;
	}

	public void setActivity(MainActivity activity) {
		this.activity = activity;
	}

	public abstract void parseWebResult(Bundle bundle);
	
	
}
