import javax.swing.plaf.TableHeaderUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ClientHttpExemplo1 {
    static ExecutorService executorService = Executors.newFixedThreadPool(6, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            System.out.println("Nova thread criada :: " + (thread.isDaemon()? "demamaoin" : "") + "Thread Group ::" + thread.getThreadGroup());
            return thread;
        }
    });

    public static void main(String[] args) throws Exception {
        connectAkamiHttp11Cliente();
//        connectAndPrintURLJavaOracle();
    }

    private static void connectAkamiHttp11Cliente() throws Exception {
        System.out.println("Running HTTP/1.1 example ...");
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .proxy(ProxySelector.getDefault())
                    .build();

            //Acompanhar performance
            long start = System.currentTimeMillis();

            HttpRequest mainResquest = HttpRequest.newBuilder()
                    .uri(URI.create("https://http2.akamai.com/demo/h2_demo_frame.html"))
                    .build();

            HttpResponse<String> response = httpClient.send(mainResquest, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code:: " + response.statusCode());
            System.out.println("Responde Headrs :: " + response.headers());
            String respondeBody = response.body();
            System.out.println(respondeBody);

            List<Future<?>> future = new ArrayList<>();
            respondeBody
                    .lines()
                    .filter(line -> line.trim().startsWith("<img height"))
                    .map(line -> line.substring(line.indexOf("src='")+5, line.indexOf("/>")))
                    .forEach(image -> {
                        Future<?> imgFuture = executorService.submit(() -> {
                           HttpRequest imgRequest = HttpRequest.newBuilder()
                               .uri(URI.create("https://http2.akamai.com" + image))
                               .build();

                            try {
                                HttpResponse<String> imageResponse = httpClient.send(imgRequest, HttpResponse.BodyHandlers.ofString());
                                System.out.println("imagem carregada: " + image + " status code: " + imageResponse.statusCode());
                            } catch (IOException | InterruptedException e ) {
                                System.out.println("Menssagem de error durante requisição para recuperar a imagem" + image);
                            }
                        });
                        future.add(imgFuture);
                        System.out.println("Submetido um futuro para imagem" + image);
                    });

            future.forEach(f -> {
                try {
                    f.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Erro ao esperar carregar imagem do futuro");
                }
            });

            long end = System.currentTimeMillis();
            System.out.println("Tempo de carregamento total ::" + (end - start) + "ms");
        } finally {
            executorService.shutdown();
        }
    }
    private static void connectAndPrintURLJavaOracle() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET().uri(URI.create("https://docs.oracle.com/javase/10/language/"))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String>response = httpClient.send (request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code :: "+ response.statusCode());
        System.out.println("Headers response :: " + response.headers());
        System.out.println(response.body());
    }
}
