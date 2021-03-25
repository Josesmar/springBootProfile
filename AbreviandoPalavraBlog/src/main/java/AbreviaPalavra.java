import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AbreviaPalavra {

        public static void main(String[] args) throws IOException {

            //Criando lista do alfabeto
            List<String> alfabeto = new ArrayList(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));

            //Lista das entradas de palavras
            List<String> entradas = new ArrayList<String>();
            Scanner leituraPalavrasEscritas = new Scanner(System.in, "ISO-8859-1");

            while(true) {
                String frase = leituraPalavrasEscritas.nextLine()
                               .trim()
                               .toLowerCase()
                               .replaceAll("\n", " ")
                               .replaceAll("\t", " ");

                if(frase.equals(".")) break;
                if(frase.length() <= 0) continue;

                List<String> ListaPalavrasFrase = new ArrayList(Arrays.asList(frase.split(" ")));
                Map<String, String> registrosDePalavras  = new HashMap<String, String>();

                alfabeto.stream().forEach(letra -> {
                    registrosDePalavras.put(letra, "");

                });
                Map<String, Map<String, Integer>> palavraRepetida = new HashMap<String, Map<String, Integer>>();

                alfabeto.stream().forEach(letra -> {
                    palavraRepetida.put(letra, new HashMap<String, Integer>());
                });

                ListaPalavrasFrase.stream().forEach(palavra -> {
                    palavraRepetida.get(palavra.substring(0, 1)).put(palavra, 0);

                });

                ListaPalavrasFrase.stream().forEach(palavra -> {
                    int quantidadePalavra = palavraRepetida.get(palavra.substring(0, 1)).get(palavra);
                    quantidadePalavra++;
                    palavraRepetida.get(palavra.substring(0, 1)).put(palavra, quantidadePalavra);
                });

                alfabeto.stream().forEach(letra -> {
                    Map<String, Integer> map = palavraRepetida.get(letra);
                    List<String> CodigosLetras = new ArrayList<String>(map.keySet());

                    int quantidadeTotalCharPorLetra = 0;

                    for (String codigo : CodigosLetras) {
                        quantidadeTotalCharPorLetra += map.get(codigo) * codigo.length();
                    }

                    int quantidadeTotalCharMelhorCenario;
                    int qtTotalCharMelhorCenarioAux = 10000;

                    for (String codigo : CodigosLetras) {
                        quantidadeTotalCharMelhorCenario = (quantidadeTotalCharPorLetra - (map.get(codigo) * codigo.length())) + (map.get(codigo) * 2);

                        if((qtTotalCharMelhorCenarioAux > quantidadeTotalCharMelhorCenario) && codigo.length() > 2) {
                            qtTotalCharMelhorCenarioAux = quantidadeTotalCharMelhorCenario;
                            registrosDePalavras.put(letra, codigo);
                        }
                    }
                });
                String fraseAbreviada = ListaPalavrasFrase
                        .stream()
                        .map(palavra -> {
                            String primeiraLetra = palavra.substring(0, 1);
                            return registrosDePalavras.get(primeiraLetra).equals(palavra) ? primeiraLetra + "." : palavra;
                        }).collect(Collectors.joining(" "));;

                System.out.println(fraseAbreviada);
                int quantidadeAbreviacoes = 0;

                for(String letra : alfabeto) {
                    if(!registrosDePalavras.get(letra).equals("")) {
                        quantidadeAbreviacoes++;
                    }
                }
                System.out.println(quantidadeAbreviacoes);

                alfabeto.stream().forEach(letra -> {

                    if(!registrosDePalavras.get(letra).equals("")) {
                        System.out.println(letra + ". = " + registrosDePalavras.get(letra) );
                    }
                });
            }
        }
    }

