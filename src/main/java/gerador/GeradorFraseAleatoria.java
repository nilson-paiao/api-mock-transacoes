package gerador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeradorFraseAleatoria {

    private static final List<String> PALAVRAS_ALEATORIAS = inicializaPalavrasAleatorias();

    private static List<String> inicializaPalavrasAleatorias() {
        List<String> linhasArquivo = new ArrayList<>();

        String arquivoPalavrasAleatorias = GeradorFraseAleatoria.class.getClassLoader().getResource("PalavrasAleatorias.txt").getFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoPalavrasAleatorias))) {

            carregaLinhasArquivo(linhasArquivo, reader);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return linhasArquivo;
    }

    private static void carregaLinhasArquivo(List<String> linhasArquivo, BufferedReader reader) throws IOException {
        String linha;
        while ((linha = reader.readLine()) != null) {
            linhasArquivo.add(linha);
        }
    }

    public static String gerarFrase(final int tamanhoMinimo) {
        StringBuilder frase = new StringBuilder();

        do {
            frase.append(gerarPalavra()).append(" ");
        } while (frase.toString().trim().length() < tamanhoMinimo);

        return frase.toString().trim();
    }

    private static String gerarPalavra() {
        int numeroAleatorio = GeradorNumeroAleatorio.gerarNumero(0, PALAVRAS_ALEATORIAS.size()-1);
        return PALAVRAS_ALEATORIAS.get(numeroAleatorio);
    }

}
