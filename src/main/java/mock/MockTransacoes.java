package mock;

import entidade.Transacao;
import gerador.GeradorNumeroAleatorio;
import servico.Requisicao;

import java.util.*;

public class MockTransacoes {

    private static final Map<Requisicao, List<Transacao>> TRANSACOES_AGRUPADAS_POR_REQUISICAO = new HashMap<>();
    private static final Map<Integer, Set<Integer>> ANO_MES_PARA_DUPLICACOES = new HashMap<>();

    public static List<Transacao> obterTransacoes(Requisicao requisicao) {
        if (TRANSACOES_AGRUPADAS_POR_REQUISICAO.containsKey(requisicao)) {
            return TRANSACOES_AGRUPADAS_POR_REQUISICAO.get(requisicao);
        }

        definirMesesParaDuplicacoes(requisicao.getAno());

        gerarTransacoes(requisicao);

        return TRANSACOES_AGRUPADAS_POR_REQUISICAO.get(requisicao);
    }

    private static void definirMesesParaDuplicacoes(final int ano) {
        if (ANO_MES_PARA_DUPLICACOES.containsKey(ano)) {
            return;
        }

        Set<Integer> mesesComDuplicacoes = new HashSet<>();
        int quantidadeMesesDoAnoParaDuplicacoes = GeradorNumeroAleatorio.gerarNumero(3, 5);

        do {
            mesesComDuplicacoes.add(GeradorNumeroAleatorio.gerarNumero(1, 12));
        } while (mesesComDuplicacoes.size() <= quantidadeMesesDoAnoParaDuplicacoes);

        ANO_MES_PARA_DUPLICACOES.put(ano, mesesComDuplicacoes);
    }

    private static void gerarTransacoes(Requisicao requisicao) {
        boolean possuiTransacaoDuplicada = false;
        List<Transacao> transacoes = new ArrayList<>();

        int quantidadeTransacoesSeremGeradas = GeradorNumeroAleatorio.gerarNumero(1, 10);
        for (int i = 1; i <= quantidadeTransacoesSeremGeradas; i++) {
            Transacao transacao = new Transacao(requisicao.getMes(), requisicao.getAno());
            transacoes.add(transacao);

            boolean mesPermiteDuplicacaoTransacao = ANO_MES_PARA_DUPLICACOES.get(requisicao.getAno()).contains(requisicao.getMes());
            if (mesPermiteDuplicacaoTransacao && !possuiTransacaoDuplicada) {
                transacoes.add(transacao.duplicarTransacao());
                possuiTransacaoDuplicada = true;
            }
        }

        TRANSACOES_AGRUPADAS_POR_REQUISICAO.put(requisicao, transacoes);
    }

}
