package main.java.utils;

public class PasswordUtils
{

    private static final int DECALAGE = 5;

    public static String encrypt(String password)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : password.toCharArray())
        {
            sb.append((char) (c + DECALAGE));
        }
        return sb.toString();
    }

    public static String decrypt(String encryptedPassword)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : encryptedPassword.toCharArray())
        {
            sb.append((char) (c - DECALAGE));
        }
        return sb.toString();
    }
}