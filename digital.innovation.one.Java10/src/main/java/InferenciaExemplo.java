import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class InferenciaExemplo {
    public static void main(String[] args) {
        printarNomeCompleto("Josesmar", "Santos");
        printarSoma(4,5,5,5);
        connectAndPrintURLJavaOracle();
    }

    private static void connectAndPrintURLJavaOracle()  {

        try {
            var url = new URL("https://docs.oracle.com/javase/10/langueage");
            var urlConnection = url.openConnection();
            var bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            System.out.println(bufferedReader.lines().collect(Collectors.joining()).replaceAll(">",">\n"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void printarNomeCompleto(String nome, String sobreNome){
        var nomeCompleto = String.format("%s %s", nome, sobreNome);
        System.out.println(nomeCompleto);
    }

    public static void printarSoma(int... numeros){
        var soma = 0;
        if (numeros.length > 0){
//            for (var numero: numeros) {
//             soma+=numero;
//            }
            for (var numero  = 0; numero < numeros.length; numero ++) {
                soma+=numeros[numero];
            }
        }
        System.out.println("A soma é: " + soma);
    }
  //***Consegue
  // variáveis locais inicializadas
  // variável suporte do enhaced for
  // variavel suporte do for iterativo
  // variavel try witg resource

  //***Não consegue
  // var não pode ser utilizado em nivel de classe
  // var não pode ser utilizado como parâmetro
  // var não pode ser utilizado em variáveis não inicializadas

  //https://docs.oracle.com/javase/10/langueage
}

