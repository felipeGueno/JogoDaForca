import java.util.Random;
import java.util.Scanner;

public class DesafioDaForca {
    static Scanner sc = new Scanner(System.in);
    static Scanner entradaString = new Scanner(System.in);

    public static void main(String[] args) {

        menuInicial();
    }

    public static void menuInicial() {
        int palavraSorteada = new Random().nextInt(10); // Sorteio do numero index
        Dicionario palavra = Dicionario.values()[palavraSorteada]; // adiciona a palavra vinda do enum com base no index sorteado
        char acertos[] = new char[palavra.name().length()]; // cria array para acertos de letras com paramentro do tamanho da palavra
        String letrasErradas[] = new String[7]; // Cria array de letras e palavras erradas passando a qnt de tentativa

        System.out.println("Mostrando a palavra para teste: " + palavra.name() + "\n");

        System.out.println("Pressione 1 para nova partida ou 0 para fechar o jogo");
        int opcao = sc.nextInt();
        if (opcao == 1) {
            System.out.println("Sua palavra tem " + palavra.name().length() + " letras");
            pedeLetra(palavra, acertos, letrasErradas);
        } else {
            System.out.println("É uma pena que não queira jogar");
            System.exit(0);
        }


    }

    public static void pedeLetra(Dicionario palavra, char[] acertos, String[] letrasErradas) {

        String letraEscolhida = "";
        int tentativa = 0;
        int palavraCompleta = 0;

        //PERMANECE NO LOOP ATÉ ESGOTAR TENTATIVA, ACERTAR PELAS LETRAS OU PALAVRA COMPLETA
        while (tentativa < 7 && !acertouLetras(palavra, acertos) && palavraCompleta == 0) {

            System.out.println("Escolha uma letra ou digite a palavra");
            letraEscolhida = "";
            if(letraEscolhida.equals("")) {
                letraEscolhida = entradaString.nextLine().toUpperCase();
                if(letraEscolhida.length() == 1) { //verifica se é letra
                    if (verificaLetrasErradas(letraEscolhida.charAt(0), palavra, acertos, letrasErradas, tentativa)) {
                        tentativa++;
                        verificaTentativa(tentativa);
                    }
                    informaLetras(acertos, letrasErradas); //informa letras e palavras erradas e corretas
                } else if (letraEscolhida.length()>1){ // verifica se é palavra
                    if(acertouPalavraCompleta(letraEscolhida, palavra, letrasErradas, tentativa)){
                        palavraCompleta =1;
                        menuInicial();
                    } else {
                        tentativa++;
                        verificaTentativa(tentativa); // verifica quantas tentativas restam
                        informaLetras(acertos, letrasErradas);
                    }
                }
            }
            if (tentativa == 7) {
                System.out.println("Você foi enforcado parcero");
                System.out.printf("A palavra era: %s%n%n", palavra.name());
                menuInicial();
            }
        }
    }
    public static boolean encontraLetra(char letraEscolhida, Dicionario palavra, char[] acertos) {

        int contadorLetra = 0;
        for (int i = 0; i < palavra.name().length(); i++) {
            if (letraEscolhida == palavra.name().charAt(i)) { // Verif se a letra escolhida é igual a alguma letra da palavra
                acertos[i] = letraEscolhida; // inclui no vetor de char a letra na mesma posição da palavra sorteada
                contadorLetra++;// se encontrar letra contador incrementa
            }
        }
        if (contadorLetra > 0) { // se meu contador for maior que 0 significa que encontrou pelo menos uma letra
            System.out.printf("Sua letra apareceu %d vezes%n", contadorLetra);
            return true;
        } else {

            return false;
        }
    }
    public static boolean verificaLetrasErradas(char letraEscolhida, Dicionario palavra, char[] acertos, String[] letrasErradas, int tentativa) {
        String str = Character.toString(letraEscolhida);; // transformo minha letra escolhida em char
        if (!encontraLetra(letraEscolhida, palavra, acertos)) { // verifico se meu encontra letra nao encontrou
            letrasErradas[tentativa] = str; // se nao encontrou recebe no vetor letras erradas na mesma pos da tentativa
            System.out.println("Esta letra não existe na palavra\n");
            return true;
        }
        return false;
    }
    public static void verificaTentativa(int  tentativa){
        System.out.printf("Restam %d tentativas %n", (7-tentativa)); // informa quantas tentativas faltam
    }
    public static boolean acertouLetras(Dicionario palavra, char[] acertos) {
        String str = new String(acertos); // transforma meu array de char acertos em string
        if (str.equals(palavra.name())) { // compara minha nova string com a palavra
            System.out.println("Parabens voce acertou a palavra: " + palavra.name()+"\n");
            menuInicial();
            return true;
        }

        return false;
    }

    public static void informaLetras(char[] acertos, String[] letrasErradas) {
        System.out.printf("Respostas erradas digitadas: "  );

        for (int i = 0; i< letrasErradas.length; i++){
            if(letrasErradas[i] == null){ //caso a posição seja nula inclui um espaço
                letrasErradas[i] = "";
            }
            System.out.print(letrasErradas[i]+"-"); // mostra respostas erradas separando por hífen
        }
        System.out.print("\nRespostas corretas digitadas: " );
        for (int i = 0; i<acertos.length; i++){
            if(acertos[i] == '\u0000'){ //caso a posição seja nula inclui um underline
                acertos[i] = '_';
            }
            System.out.print(acertos[i]); // mostra respostas corretas
        }
        System.out.println("\n");
    }

    public static boolean acertouPalavraCompleta (String letraEscolhida, Dicionario palavra, String[] letrasErradas, int tentativa){
        if (letraEscolhida.equals(palavra.name())){ // pega minha palavra e compara com a palavra sorteada
            System.out.println("Parabens voce acertou a palavra completa\n");
            return true;
        }
        letrasErradas[tentativa] = letraEscolhida; // inclui no array de respostas erradas a palavra na posição da tentativa
        System.out.println("Resposta incorreta");
        return false;

    }

}