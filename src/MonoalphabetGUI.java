import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class MonoalphabetGUI  extends JFrame {
    private static final int ALPHABET_SIZE = 36;
    private static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНҢОӨПРСТУҮФХЦЧШЩЪЫЬЭЮЯ";

    private static ArrayList<Character> frequent;

    private static HashMap<Character, Character> substitutionMap;
    private JTextArea decryptedTextArea;
    private JTextArea encryptedTextArea;
    private JTextField letterToChangeTextField;
    private JTextField substitutionLetterTextField;

    public MonoalphabetGUI() {
        super("Mono alphabetical Cipher Decryption");
        setLayout(new FlowLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JLabel encryptedTextLabel = new JLabel("Encrypted Text:");
        encryptedTextArea = new JTextArea(20, 40);
        encryptedTextArea.setLineWrap(true);
        encryptedTextArea.setWrapStyleWord(true);
        JScrollPane encryptedTextScrollPane = new JScrollPane(encryptedTextArea);
        encryptedTextScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(encryptedTextLabel);
        leftPanel.add(encryptedTextScrollPane);

        JLabel decryptedTextLabel = new JLabel("Decrypted Text:");
        decryptedTextArea = new JTextArea(20, 40);
        decryptedTextArea.setEditable(false);
        decryptedTextArea.setLineWrap(true);
        decryptedTextArea.setWrapStyleWord(true);
        JScrollPane decryptedTextScrollPane = new JScrollPane(decryptedTextArea);
        decryptedTextScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(decryptedTextLabel);
        leftPanel.add(decryptedTextScrollPane);

        letterToChangeTextField = new JTextField(50);
        JLabel letterToChangeTextLabel = new JLabel("Encrypted Alphabet:");

        substitutionLetterTextField = new JTextField(50);
        JLabel substitutionLetterTextLabel = new JLabel("Decrypted Alphabet:");

        JButton decryptButton = new JButton("Decrypt");
        setFrequent();
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encryptedText = encryptedTextArea.getText();

                findSubstitution(encryptedText);
                String decryptedText = decrypt(encryptedText);

                decryptedTextArea.setText(decryptedText);
                letterToChangeTextField.setText(getAlphabet());
                substitutionLetterTextField.setText(getDecryptedInAlphabeticalOrder());

            }
        });

        leftPanel.add(decryptButton);
        add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(letterToChangeTextLabel);
        rightPanel.add(letterToChangeTextField);

        rightPanel.add(substitutionLetterTextLabel);
        rightPanel.add(substitutionLetterTextField);

        JButton changeButton = new JButton("Substitute");
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encryptedText = encryptedTextArea.getText();
                String encryptedAlphabet = letterToChangeTextField.getText();
                String decryptedAlphabet = substitutionLetterTextField.getText();
                editSubstitution(encryptedAlphabet, decryptedAlphabet);
                decryptedTextArea.setText(decrypt(encryptedText));
            }
        });
        rightPanel.add(changeButton);
        
        add(rightPanel);


        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setFrequent() {
        frequent = new ArrayList<Character>();
        frequent.add('А');
        frequent.add('П');
        frequent.add('К');
        frequent.add('Р');
        frequent.add('Ы');
        frequent.add('Т');
        frequent.add('Л');
        frequent.add('И');
        frequent.add('Е');
        frequent.add('Д');
        frequent.add('О');
        frequent.add('У');
        frequent.add('Г');
        frequent.add('М');
        frequent.add('С');
        frequent.add('Ү');
        frequent.add('Б');
        frequent.add('Ө');
        frequent.add('Ж');
        frequent.add('П');
        frequent.add('Й');
        frequent.add('Ш');
        frequent.add('З');
        frequent.add('Ч');
        frequent.add('Э');
        frequent.add('Я');
        frequent.add('В');
        frequent.add('Ң');
        frequent.add('Ф');
        frequent.add('Ц');
        frequent.add('Х');
        frequent.add('Ю');
        frequent.add('Ь');
        frequent.add('Щ');
        frequent.add('Ё');
        frequent.add('Ъ');
    }

    public static void findSubstitution(String encryptedText) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (int i = 0; i < encryptedText.length(); i++) {
            char c = encryptedText.charAt(i);
            int index = ALPHABET.indexOf(Character.toUpperCase(c));
            if (Character.isLetter(c) && index >= 0) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }

        List<Map.Entry<Character, Integer>> sortedList = new ArrayList<>(frequencyMap.entrySet());
        Collections.sort(sortedList, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> entry1, Map.Entry<Character, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });

        substitutionMap = new HashMap<>();
        for (int i=0; i<sortedList.size(); i++){
            substitutionMap.put(sortedList.get(i).getKey(), frequent.get(i));
        }
    }

    public static void editSubstitution(char encrypted, char supposedLetter){
        substitutionMap.replace(encrypted, supposedLetter);
    }

    public static void editSubstitution(String encrypted, String decrypted){
        for (int i=0; i<encrypted.length(); i++){
            char e = encrypted.charAt(i);
            if (substitutionMap.containsKey(e)) {
                char d = decrypted.charAt(i);
                substitutionMap.replace(e, d);
            }
        }
    }

    public static String decrypt(String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            char c = encryptedText.charAt(i);
            char decryptedChar = decryptChar(c);
            decryptedText.append(decryptedChar);
        }
        return decryptedText.toString() + "\n";
    }

    private static char decryptChar(char c) {
        int index = ALPHABET.indexOf(Character.toUpperCase(c));
        if (index < 0 || index >= ALPHABET_SIZE) {
            return c;
        } else {
            return substitutionMap.getOrDefault(c, '#');
        }
    }

    public String getDecryptedInAlphabeticalOrder(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<ALPHABET_SIZE; i++){
            char c = ALPHABET.charAt(i);
            stringBuilder.append(substitutionMap.getOrDefault(c, '#')).append(' ').append(' ');
        }
        return stringBuilder.toString();
    }

    public String getAlphabet(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<ALPHABET_SIZE; i++){
            stringBuilder.append(ALPHABET.charAt(i)).append(' ').append(' ');
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MonoalphabetGUI();
            }
        });
    }
}
