package gerador;

import java.util.Random;

public class GeradorNumeroAleatorio {

    public static int gerarNumero(final int numeroMinimo, final int numeroMaximo) {
        return new Random().nextInt((numeroMaximo - numeroMinimo) + 1) + numeroMinimo;
    }
}
