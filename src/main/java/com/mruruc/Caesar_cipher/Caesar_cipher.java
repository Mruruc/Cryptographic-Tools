package com.mruruc.Caesar_cipher;

public class Caesar_cipher {


        public static String encrypted(String message, int key) {
            StringBuilder encrypted = new StringBuilder();
            char ch;

            for(int i = 0; i < message.length(); ++i){
                ch = message.charAt(i);

                // If it's a letter (uppercase or lowercase), shift it.
                if(Character.isLetter(ch)) {
                    char base = Character.isLowerCase(ch) ? 'a' : 'A';
                    ch = (char) ((ch - base + key) % 26 + base);
                }
                // If it's not a letter, leave it as it is.

                encrypted.append(ch);
            }
            return encrypted.toString();
        }

    public static String decrypt(String encrypted,int key){
        return encrypted(encrypted, 26 - (key % 26));
    }

    public static void main(String[] args) {
        String message="WHVW WRGDB";
        int key=2;
       // System.out.println( encrypted(message,key));
      //  System.out.println(decrypt(message,3));

        System.out.println();

    }
}
