import java.util.Scanner;

public class Calculadora {
    private static int a=0;
    private static int b=0;
    private static float resultado;
    private static int realizar = 0;
    private static String tipoOperacao;

    public static void main(String[] args) {
        Calculo soma = (a,b) -> a+b;
        Calculo subtracao = (a,b) -> a-b;
        Calculo divisao = (a,b) -> a/b;
        Calculo multiplicacao = (a,b) -> a*b;

        Scanner opcao = new Scanner(System.in);

        do {
            System.out.println("\n===Calculadora===");
            System.out.println("Selecione uma operação\n");
            System.out.println("1 - Somar");
            System.out.println("2 - Subtrair");
            System.out.println("3 - Multiplicar");
            System.out.println("4 - Dividir\n");
            realizar = opcao.nextInt();

        if(realizar < 1 || realizar > 4){
          System.out.println("Opção inválida!");
          System.out.println("Selecione uma opção válida.");
          new Calculadora();
        }
        } while (realizar < 1 || realizar > 4);

        if (realizar > 0){
            dadosOperacao();
         }

        switch (realizar) {
            case 1:
                tipoOperacao = "Soma";
                resultado =soma.calculor(a,b);
                break;
            case 2:
                tipoOperacao = "Subtração";
                resultado=subtracao.calculor(a,b);
                break;
            case 3:
                tipoOperacao = "Multiplicação";
                resultado=multiplicacao.calculor(a,b);
                break;
            case 4:
                tipoOperacao = "Divisão";
                resultado=divisao.calculor(a,b);
                break;
        }
        System.out.println("Resultado da " + tipoOperacao + " é: "+ resultado);

    }
    public static void dadosOperacao(){
        Scanner valor = new Scanner(System.in);
        System.out.println("Digite o primeiro valor.\n");
        a = valor.nextInt();
        System.out.println("Digite o segundo valor.\n");
        b = valor.nextInt();
    }
    public static int executarOperacao(Calculo calculo, int a, int b){
        return calculo.calculor(a,b);
    }
}

@FunctionalInterface
interface Calculo{
    public int calculor(int a, int b);
}