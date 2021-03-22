import java.util.Objects;

public class StringIsBlankExemplo {
    public static void main(String[] args) {
        String espaco = "                                           ";

        //validação no java 11
        System.out.println(espaco.isBlank());

        //Validação antes do java 11
        System.out.println(espaco == null || espaco.length() ==0 || espaco.chars().allMatch(c -> c== ' '));
    }
}
