package net.trulycanadian.recipeapplication.receiver;

import net.trulycanadian.recipeapplication.activity.LoginActivity;
import net.trulycanadian.recipeapplication.activity.MainActivity;
import net.trulycanadian.recipeapplication.fragment.command.Command;
import net.trulycanadian.recipeapplication.fragment.command.CommandFactory;
import net.trulycanadian.recipeapplication.service.RestService;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class WebServiceReceiver extends ResultReceiver {

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	Context context;

	public WebServiceReceiver(Handler handler) {
		super(handler);
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle result) {
		Command command = CommandFactory.createCommand(result
				.getInt(RestService.REST_COMMAND));
		if (result.getInt(RestService.REST_COMMAND) != RestService.GETAUTH) {
			System.out.println("inside main");
			MainActivity activity = (MainActivity) context;
			command.setActivity(activity);
		} else {
			System.out.println("inside auth");
			LoginActivity activity2 = (LoginActivity) context;
			command.setLoginActivity(activity2);
		}
		if (command.getActivity() != null || command.getLoginActivity() != null)
			command.parseWebResult(result);

	}
}
