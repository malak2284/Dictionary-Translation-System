import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class LanguageServer implements Runnable {
    private int port;
    private String languageCode;

    public LanguageServer(int port, String languageCode) {
        this.port = port;
        this.languageCode = languageCode;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Language Server for " + languageCode + " started on port " + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    String word = in.readLine();
                    String translatedWord = translateWord(word);
                    out.println(translatedWord);
                } catch (IOException e) {
                    System.out.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port + ": " + e.getMessage());
        }
    }

    private String translateWord(String word) {
        // Tłumaczenie słowa - w rzeczywistości użylibyśmy prawdziwej bazy danych lub API do tłumaczenia
        word = "woda";
        return "Translated " + word + " to " + languageCode;
    }

    public static void main(String[] args) {
        int port = 9001; // Przykładowy port dla serwera językowego
        String languageCode = "PL"; // Kod języka
        new Thread(new LanguageServer(port, languageCode)).start();
    }
}