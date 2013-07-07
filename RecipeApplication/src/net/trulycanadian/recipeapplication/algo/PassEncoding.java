package net.trulycanadian.recipeapplication.algo;

import org.jasypt.util.text.BasicTextEncryptor;

public class PassEncoding {

    private static String myEncryptedPassword = "recipessalt";

	public static String encode(String password) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(myEncryptedPassword);
		return textEncryptor.encrypt(password);
	}

	public static String decode(String password) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(myEncryptedPassword);
		return textEncryptor.decrypt(password);
	}
}
