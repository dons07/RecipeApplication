package net.trulycanadian.recipeapplication.command;

import net.trulycanadian.recipeapplication.service.RestService;

public class CommandFactory {

	public static Command createCommand(int key) {
		Command command = null;
		switch (key) {
		case RestService.GETRECIPES:
			command = new GetRecipeCommand();
			return command;
		case RestService.GETAUTH:
			command = new AuthCommand();
			return command;
		case RestService.SINGLERECIPE:
			command = new SingleRecipeCommand();
			return command;
		case RestService.POSTRECIPE:
			command = new PostRecipeCommand();
			return command;
		default:
			return null;
		}
	}
}
