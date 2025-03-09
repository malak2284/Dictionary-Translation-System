import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private DictionaryServersManager serversManager;

    public MainServer(int port, int poolSize) throws Exception {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
        serversManager = new DictionaryServersManager();
    }

    public void start() {
        System.out.println("Main Server started on port " + serverSocket.getLocalPort());
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket, serversManager));
            }
        } catch (Exception e) {
            System.out.println("Error in Main Server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8000; // Port na którym nasłuchuje serwer główny
        int poolSize = 10; // Maksymalna liczba wątków obsługujących żądania
        MainServer server = new MainServer(port, poolSize);
        server.start();
    }
}