import java.util.HashMap;
import java.util.Map;

public class DictionaryServersManager {
    private Map<String, LanguageServerInfo> languageServers;

    public DictionaryServersManager() {
        this.languageServers = new HashMap<>();
        initializeDefaultServers();
    }

    // Inicjalizacja domyślnych serwerów językowych
    private void initializeDefaultServers() {
        addLanguageServer("EN", 9001);
        addLanguageServer("FR", 9002);
        addLanguageServer("DE", 9003);
    }

    // Dodanie nowego serwera językowego
    public void addLanguageServer(String languageCode, int port) {
        LanguageServerInfo serverInfo = new LanguageServerInfo(port, languageCode);
        languageServers.put(languageCode, serverInfo);
        new Thread(new LanguageServer(port, languageCode)).start();
    }

    // Pobieranie informacji o serwerze językowym
    public LanguageServerInfo getServerInfo(String languageCode) {
        return languageServers.get(languageCode);
    }



    // Wewnętrzna klasa do przechowywania informacji o serwerach językowych
    public static class LanguageServerInfo {
        private int port;
        private String languageCode;

        public LanguageServerInfo(int port, String languageCode) {
            this.port = port;
            this.languageCode = languageCode;
        }

    }
}
