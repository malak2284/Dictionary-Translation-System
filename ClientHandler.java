import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DictionaryServersManager serversManager;

    public ClientHandler(Socket clientSocket, DictionaryServersManager serversManager) {
        this.clientSocket = clientSocket;
        this.serversManager = serversManager;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Parsowanie żądania klienta
                String[] parts = inputLine.split(",");
                if (parts.length == 2) {
                    String wordToTranslate = parts[0].trim();
                    String targetLanguage = parts[1].trim();

                    // Pobranie informacji o serwerze językowym
                    DictionaryServersManager.LanguageServerInfo serverInfo = serversManager.getServerInfo(targetLanguage);
                    if (serverInfo != null) {
                        // Przekazanie żądania do odpowiedniego serwera językowego
                        String translatedWord = sendTranslationRequest(wordToTranslate, serverInfo);
                        out.println(translatedWord);
                    } else {
                        // Obsługa sytuacji, gdy nie istnieje serwer dla podanego języka
                        out.println("Error: No server available for the requested language.");
                    }
                } else {
                    out.println("Error: Invalid request format.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error handling client request: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    private String sendTranslationRequest(String word, DictionaryServersManager.LanguageServerInfo serverInfo) {
        // Logika wysyłania żądania do konkretnego serwera językowego
        // Możemy założyć, że tutaj jest otwarte nowe połączenie do serwera językowego i wysyłane jest żądanie
        // Ta funkcja powinna zwracać przetłumaczone słowo
        return "Translated word (mock)";
    }
}