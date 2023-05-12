// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

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

    private static ArrayDeque<Character> frequent;

    private JTextField encryptedTextField;
    private JTextField shiftTextField;
    private JTextArea decryptedTextArea;

    public void setMostUsedLetter() {
        frequent = new ArrayDeque<Character>();
        frequent.addFirst('Н');
        frequent.addFirst('Ш');
        frequent.addFirst('Л');
        frequent.addFirst('У');
        frequent.addFirst('Г');
        frequent.addFirst('Ч');
        frequent.addFirst('О');
        frequent.addFirst('У');
        frequent.addFirst('О');
        frequent.addFirst('Ж');
        frequent.addFirst('Ы');
        frequent.addFirst('Н');
        frequent.addFirst('М');
        frequent.addFirst('Р');
        frequent.addFirst('Б');
        frequent.addFirst('Э');
        frequent.addFirst('Т');
        frequent.addFirst('К');
        frequent.addFirst('И');
        frequent.addFirst('А');
    }


    public CaesarCipherGUI() {
        super("Caesar Cipher Decryption");
        setLayout(new BorderLayout());

        // Create encrypted text input field
        JPanel encryptedTextPanel = new JPanel(new FlowLayout());
        JLabel encryptedTextLabel = new JLabel("Encrypted Text:");
        encryptedTextField = new JTextField(80);
        encryptedTextPanel.add(encryptedTextLabel);
        encryptedTextPanel.add(encryptedTextField);
        add(encryptedTextPanel, BorderLayout.NORTH);

//        // Create shift value input field
//        JPanel shiftPanel = new JPanel(new FlowLayout());
//        JLabel shiftLabel = new JLabel("Shift Value:");
//        shiftTextField = new JTextField(3);
//        shiftPanel.add(shiftLabel);
//        shiftPanel.add(shiftTextField);
//        add(shiftPanel, BorderLayout.WEST);

        // Create decrypted text output area
        JPanel decryptedTextPanel = new JPanel(new BorderLayout());
        JLabel decryptedTextLabel = new JLabel("Decrypted Text:");
        decryptedTextArea = new JTextArea(20, 100);
        decryptedTextArea.setEditable(false);
        JScrollPane decryptedTextScrollPane = new JScrollPane(decryptedTextArea);
        decryptedTextPanel.add(decryptedTextLabel, BorderLayout.NORTH);
        decryptedTextPanel.add(decryptedTextScrollPane, BorderLayout.CENTER);
        add(decryptedTextPanel, BorderLayout.CENTER);

        // Create decrypt button
        JButton decryptButton = new JButton("Decrypt");
        setMostUsedLetter();
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Get the encrypted text and shift value from the input fields
            String encryptedText = encryptedTextField.getText();
//                    int shift = Integer.parseInt(shiftTextField.getText());

            // Decrypt the text using the Caesar cipher decryption algorithm
            String decryptedText = decrypt(encryptedText);

            // Display the decrypted text in the output area
            decryptedTextArea.setText(decryptedText);

            }
            });
            add(decryptButton, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null); // Center the window on the screen
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        public static String decrypt(String encryptedText) {
            StringBuilder decryptedText = new StringBuilder();
            int shift = findShift(encryptedText);
            for (int i = 0; i < encryptedText.length(); i++) {
                char c = encryptedText.charAt(i);
                char decryptedChar = decryptChar(c, shift);
                decryptedText.append(decryptedChar);
            }
            return decryptedText.toString() + "\n";
        }

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

        public static int findShift(String encryptedText) {
            Map<Character, Integer> frequencyMap = new HashMap<>();


            // Count the frequency of each letter
            for (int i = 0; i < encryptedText.length(); i++) {
                char c = encryptedText.charAt(i);
                if (Character.isLetter(c)) {
                    c = Character.toLowerCase(c);
                    frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
                }
            }

            // Create a list of pairs and sort it by frequency count
            List<Map.Entry<Character, Integer>> sortedList = new ArrayList<>(frequencyMap.entrySet());
            Collections.sort(sortedList, new Comparator<Map.Entry<Character, Integer>>() {
                @Override
                public int compare(Map.Entry<Character, Integer> entry1, Map.Entry<Character, Integer> entry2) {
                    return entry2.getValue().compareTo(entry1.getValue());
                }
            });

            char mostFrInText = sortedList.get(0).getKey();
            char mostFrInLang = frequent.pop();
            int shift = (36 + ALPHABET.indexOf(mostFrInText) - ALPHABET.indexOf(mostFrInLang))%36;
            return shift;
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