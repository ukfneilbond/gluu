package com.amido.secureia.serenity.mocks;

import com.amido.secureia.serenity.exceptions.UserNotFoundException;

public class User {

	public String username;
	public String name;
	public String password;
	public String email;
	public String id;

	public User(String username, String name, String password, String email, String id) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.id = id;
	}

	private static User[] users = { new User("john", "john", "password", "lennon@demo.com", "@!9B20.BC69.0CBE.19CB!0001!B6B8.03BD!0000!5431.A5BC.A2C1.F352"),
			new User("notregistered", "notregistered", "notregistered", "notregistered@demo.com", "notregistered"),
			new User("george", "george", "password", "harrison@demo.com", "@!9B20.BC69.0CBE.19CB!0001!B6B8.03BD!0000!EF2F.2312.901A.6638"),
			new User("paul", "paul", "password", "mccartney@demo.com", "@!9B20.BC69.0CBE.19CB!0001!B6B8.03BD!0000!4A79.6AE1.EAD1.8EDF")};

	public static User getUserWithUsername(String username) throws Exception {
		for (int i = 0; i < users.length; i++) {
			if (users[i].username.equals(username)) {
				return users[i];
			}
		}
		throw new UserNotFoundException("User \"" + username + "\" can\'t be found on users");
	}

}
