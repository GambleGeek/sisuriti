// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cipher {

    // Number of letters in the Kyrgyz alphabet
    private static final int ALPHABET_SIZE = 36;
    private static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНҢОӨПРСТУҮФХЦЧШЩЪЫЬЭЮЯ";
    public static String decrypt(String encryptedText, int shift) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            char c = encryptedText.charAt(i);
            char decryptedChar = decryptChar(c, shift);
            decryptedText.append(decryptedChar);
        }
        return decryptedText.toString();
    }

    private static char decryptChar(char c, int shift) {
        if (!Character.isLetter(c)) {
            return c;
        }

        int index = ALPHABET.indexOf(Character.toUpperCase(c));
        if (index < 0 || index >= ALPHABET_SIZE) {
            return c;
        }

        int decryptedIndex = (index - shift + ALPHABET_SIZE) % ALPHABET_SIZE;

        char decryptedChar = ALPHABET.charAt(decryptedIndex);
        return Character.isLowerCase(c) ? Character.toLowerCase(decryptedChar) : decryptedChar;
    }
}