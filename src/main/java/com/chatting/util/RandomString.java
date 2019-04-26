package com.chatting.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomString {
    final String salt = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    final String random = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    final String MD5_HEADER = "$1$";

    public String getRandomSalt(final int num) {
        final StringBuilder saltString = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            saltString.append(salt.charAt(new Random().nextInt(salt.length())));
        }
        return saltString.toString();
    }
    public String getRandomString(final int num) {
        final StringBuilder saltString = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            saltString.append(random.charAt(new Random().nextInt(random.length())));
        }
        return saltString.toString();
    }
    public String criptPassWord(String password,String salt){
        return DigestUtils.md5Hex(Md5Crypt.md5Crypt(password.getBytes(), "$1$"+salt));
    }
    public static void main(String args[]){
        RandomString randomString = new RandomString();
        System.out.println(randomString.getRandomString(32));
        String salt = randomString.getRandomSalt(8);
        System.out.println(salt);
        System.out.println(randomString.criptPassWord("123456",salt));
    }
}
