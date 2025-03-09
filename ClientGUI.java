import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGUI extends JFrame {
    private JTextField wordField;
    private JComboBox<String> languageBox;
    private JButton translateButton;
    private JTextArea resultArea;

    public ClientGUI() {
        super("Dictionary Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        wordField = new JTextField(10);
        languageBox = new JComboBox<>(new String[]{"EN", "FR", "DE"}); // Dostępne języki
        translateButton = new JButton("Translate");
        topPanel.add(new JLabel("Word:"));
        topPanel.add(wordField);
        topPanel.add(new JLabel("To:"));
        topPanel.add(languageBox);
        topPanel.add(translateButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendTranslationRequest();
            }
        });

        setVisible(true);
    }

    private void sendTranslationRequest() {
        String word = wordField.getText();
        String language = (String) languageBox.getSelectedItem();
        try (Socket socket = new Socket("localhost", 8000); // Załóżmy, że serwer główny działa na porcie 8000
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(word + "," + language);
            // Odebranie odpowiedzi z serwera językowego można obsłużyć w oddzielnym wątku
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ClientGUI();
    }
}