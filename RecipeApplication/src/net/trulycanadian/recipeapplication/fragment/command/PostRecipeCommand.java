package net.trulycanadian.recipeapplication.fragment.command;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class PostRecipeCommand extends Command {
	public void parseWebResult(Bundle bundle) {
		if (bundle.getInt("statuscode") == HttpStatus.SC_CREATED) {
			
			Toast.makeText(this.getActivity(), "Inserted Recipe and ingredients",
					Toast.LENGTH_SHORT).show();
		}
	}
}
