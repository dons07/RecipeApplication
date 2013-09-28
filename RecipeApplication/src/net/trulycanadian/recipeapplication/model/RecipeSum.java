package net.trulycanadian.recipeapplication.model;

public class RecipeSum extends SimpleRecipe {
	private long id;
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private UserInfo useraccount;

	public UserInfo getUserinfo() {
		return useraccount;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.useraccount = userinfo;
	}
	
}
