package br.com.jj.builder;

import br.com.jj.entity.User;

public class UserBuilder {

	private User user;
	
	private UserBuilder() {}
	
	public static UserBuilder initUser() {
		UserBuilder builder = new UserBuilder();
		builder.user = new User("User 1");
		return builder;
	}
	
	public User builder() {
		return user;
	}
}
