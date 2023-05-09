// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//public class Cipher {
//
//    // Number of letters in the Kyrgyz alphabet

//    public static String decrypt(String encryptedText, int shift) {
//        StringBuilder decryptedText = new StringBuilder();
//        for (int i = 0; i < encryptedText.length(); i++) {
//            char c = encryptedText.charAt(i);
//            char decryptedChar = decryptChar(c, shift);
//            decryptedText.append(decryptedChar);
//        }
//        return decryptedText.toString();
//    }
//
//    private static char decryptChar(char c, int shift) {
//        if (!Character.isLetter(c)) {
//            return c;
//        }
//
//        int index = ALPHABET.indexOf(Character.toUpperCase(c));
//        if (index < 0 || index >= ALPHABET_SIZE) {
//            return c;
//        }
//
//        int decryptedIndex = (index - shift + ALPHABET_SIZE) % ALPHABET_SIZE;
//
//        char decryptedChar = ALPHABET.charAt(decryptedIndex);
//        return Character.isLowerCase(c) ? Character.toLowerCase(decryptedChar) : decryptedChar;
//    }

public class CaesarCipherGUI extends JFrame {
    private static final int ALPHABET_SIZE = 36;
    private static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНҢОӨПРСТУҮФХЦЧШЩЪЫЬЭЮЯ";



    private JTextField encryptedTextField;
    private JTextField shiftTextField;
    private JTextArea decryptedTextArea;

    public CaesarCipherGUI() {
        super("Caesar Cipher Decryption");
        setLayout(new BorderLayout());

        // Create encrypted text input field
        JPanel encryptedTextPanel = new JPanel(new FlowLayout());
        JLabel encryptedTextLabel = new JLabel("Encrypted Text:");
        encryptedTextField = new JTextField(20);
        encryptedTextPanel.add(encryptedTextLabel);
        encryptedTextPanel.add(encryptedTextField);
        add(encryptedTextPanel, BorderLayout.NORTH);

        // Create shift value input field
        JPanel shiftPanel = new JPanel(new FlowLayout());
        JLabel shiftLabel = new JLabel("Shift Value:");
        shiftTextField = new JTextField(3);
        shiftPanel.add(shiftLabel);
        shiftPanel.add(shiftTextField);
        add(shiftPanel, BorderLayout.WEST);

        // Create decrypted text output area
        JPanel decryptedTextPanel = new JPanel(new BorderLayout());
        JLabel decryptedTextLabel = new JLabel("Decrypted Text:");
        decryptedTextArea = new JTextArea(5, 20);
        decryptedTextArea.setEditable(false);
        JScrollPane decryptedTextScrollPane = new JScrollPane(decryptedTextArea);
        decryptedTextPanel.add(decryptedTextLabel, BorderLayout.NORTH);
        decryptedTextPanel.add(decryptedTextScrollPane, BorderLayout.CENTER);
        add(decryptedTextPanel, BorderLayout.CENTER);

        // Create decrypt button
        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the encrypted text and shift value from the input fields
                    String encryptedText = encryptedTextField.getText();
                    int shift = Integer.parseInt(shiftTextField.getText());

                    // Decrypt the text using the Caesar cipher decryption algorithm
                    String decryptedText = decrypt(encryptedText, shift);

                    // Display the decrypted text in the output area
                    decryptedTextArea.setText(decryptedText);
                } catch (NumberFormatException ex) {
                    // If the shift value is not a valid integer, display an error message
                    JOptionPane.showMessageDialog(CaesarCipherGUI.this,
                            "Please enter a valid integer for the shift value.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            });
            add(decryptButton, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null); // Center the window on the screen
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        /**
         * Decrypts a Caesar cipher encrypted Kyrgyz text with a given shift value.
         *
         * @param encryptedText The encrypted text to be decrypted.
         * @param shift         The shift value used for encryption.
         * @return The decrypted text.
         */
        public static String decrypt(String encryptedText, int shift) {
            StringBuilder decryptedText = new StringBuilder();
            for (int i = 0; i < encryptedText.length(); i++) {
                char c = encryptedText.charAt(i);
                char decryptedChar = decryptChar(c, shift);
                decryptedText.append(decryptedChar);
            }
            return decryptedText.toString();
        }
        /**
         * Decrypts a single character from a Caesar cipher encrypted Kyrgyz text with a given shift value.
         *
         * @param c The character to be decrypted.
         * @param shift The shift value used for encryption.
         * @return The decrypted character.
         */
        private static char decryptChar(char c, int shift) {
            int index = ALPHABET.indexOf(Character.toUpperCase(c));
            if (index < 0 || index >= ALPHABET_SIZE) {
                // If the character is not found in the alphabet, return it unchanged
                return c;
            } else {
                // Shift the index by the negative shift value to get the decrypted index
                int decryptedIndex = (index - shift + ALPHABET_SIZE) % ALPHABET_SIZE;
                // Get the decrypted character from the alphabet using the decrypted index
                return ALPHABET.charAt(decryptedIndex);
            }
        }

        public static void main(String[] args) {
            // Create the GUI frame
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new CaesarCipherGUI();
                }
            });
        }
    }