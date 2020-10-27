package servico;

import entidade.Transacao;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ServicoTransacoesTest {

    final ServicoTransacoes servicoTransacoes = new ServicoTransacoes();

    @Test
    void testaQuantidadeTransacoesPorMes() {
        Map<Integer, List<Transacao>> transacoesPorMes = carregaTransacoesPorMes();
        for (int mes = 1; mes <= 12; mes++) {
            List<Transacao> transacoes = transacoesPorMes.get(mes);
            assertFalse(transacoes.isEmpty(), "Cada mês deve ter ao menos uma transação");
        }

        assertEquals(12, transacoesPorMes.size(), "Deve existir transações para todos os meses do ano");
    }

    @Test
    void testaConsistenciaTransacoesEntreRequisicoes() {
        Map<Integer, List<Transacao>> transacoesPorMes = carregaTransacoesPorMes();
        for (int mes = 1; mes <= 12; mes++) {
            List<Transacao> transacoes = consumirServico(mes);
            assertEquals(transacoesPorMes.get(mes), transacoes, "A lista de transações não pode mudar a cada requisição dado o mesmo conjunto de dados");
        }
    }

    @Test
    void testaVariacaoQuantidadeTransacoesNoAno() {
        Map<Integer, List<Transacao>> transacoesPorMes = carregaTransacoesPorMes();
        Set<Integer> transacoesQuantidadesDiferente = new HashSet<>();

        for (List<Transacao> transacoes : transacoesPorMes.values()) {
            transacoesQuantidadesDiferente.add(transacoes.size());
        }

        assertTrue(transacoesQuantidadesDiferente.size() > 1, "A quantidade de transações deve ser variável entre os meses");
    }

    @Test
    void testaAno() {
        int quantidadeMesesComTransacoesDuplicadas = 0;
        Map<Integer, List<Transacao>> transacoesPorMes = carregaTransacoesPorMes();

        for (List<Transacao> transacoes : transacoesPorMes.values()) {

            if (mesPossuiDuplicacaoDeTransacao(transacoes)){
                quantidadeMesesComTransacoesDuplicadas++;
            }
        }

        assertTrue(quantidadeMesesComTransacoesDuplicadas >= 3, "Deve haver ao menos 3 meses com transações duplicadas por ano");
    }

    private boolean mesPossuiDuplicacaoDeTransacao(List<Transacao> transacoes) {
        Map<String, List<Transacao>> transacoesAgrupadasPorDescricaoDataValor = carregaTransacoesAgrupadasPorDescricaoDataValor(transacoes);

        for (List<Transacao> transacoesAgrupadas : transacoesAgrupadasPorDescricaoDataValor.values()) {
            if (transacoesAgrupadas.size() > 1)
                return true;
        }

        return false;
    }

    @Test
    void testaMarcacaoDuplicada() {
        Map<Integer, List<Transacao>> transacoesPorMes = carregaTransacoesPorMes();
        for (List<Transacao> transacoes : transacoesPorMes.values()) {

            Map<String, List<Transacao>> transacoesAgrupadasPorDescricaoDataValor = carregaTransacoesAgrupadasPorDescricaoDataValor(transacoes);
            validaMarcacaoDeDuplicacao(transacoesAgrupadasPorDescricaoDataValor);
        }
    }

    private Map<Integer, List<Transacao>> carregaTransacoesPorMes() {
        Map<Integer, List<Transacao>> transacoesPorMes = new HashMap<>();

        for (int mes = 1; mes <= 12; mes++) {
            transacoesPorMes.put(mes, consumirServico(mes));
        }

        return transacoesPorMes;
    }

    private void validaMarcacaoDeDuplicacao(Map<String, List<Transacao>> transacoesAgrupadasPorDescricaoDataValor) {
        for (List<Transacao> transacoes : transacoesAgrupadasPorDescricaoDataValor.values()) {
            assertEquals(1, quantidadeTransacoesNaoMarcadasComoDuplicadas(transacoes), "Dado as mesmas informacoes de descricao, data e valor apenas uma transação pode estar marcada como não duplicada");
        }
    }

    private int quantidadeTransacoesNaoMarcadasComoDuplicadas(List<Transacao> transacoes) {
        int quantidadeNaoMarcadasDuplicadas = 0;

        for (Transacao transacao : transacoes) {
            if (!transacao.isDuplicated()) {
                quantidadeNaoMarcadasDuplicadas++;
            }
        }

        return quantidadeNaoMarcadasDuplicadas;
    }

    private Map<String, List<Transacao>> carregaTransacoesAgrupadasPorDescricaoDataValor(List<Transacao> transacoes) {
        Map<String, List<Transacao>> transacoesAgrupadas = new HashMap<>();

        for (Transacao transacao : transacoes) {
            String agrupamento = transacao.getDescricao() + "|" + transacao.getData() + "|" + transacao.getValor();

            if (!transacoesAgrupadas.containsKey(agrupamento)) {
                transacoesAgrupadas.put(agrupamento, new ArrayList<>());
            }

            transacoesAgrupadas.get(agrupamento).add(transacao);
        }

        return transacoesAgrupadas;
    }

    @Test
    void testaInformacoesPorTransacao() {
        List<Transacao> transacoes = consumirServico(10);

        for (Transacao transacao : transacoes) {
            validaDataTransacao(transacao);
            validaDescricaoTransacao(transacao);
            validaValorTransacao(transacao);
        }

    }

    private void validaValorTransacao(Transacao transacao) {
        assertTrue(transacao.getValor() >= -9999999);
        assertTrue(transacao.getValor() <= 9999999);
    }

    private void validaDescricaoTransacao(Transacao transacao) {
        assertNotNull(transacao.getDescricao());
        assertTrue(transacao.getDescricao().length() >= 10);
        assertTrue(transacao.getDescricao().length() <= 60);
    }

    private void validaDataTransacao(Transacao transacao) {
        assertNotNull(transacao.getData());

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(transacao.getData());

        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(2020, calendar.get(Calendar.YEAR));
    }

    @SuppressWarnings("unchecked")
    private List<Transacao> consumirServico(final int mes) {
        Response response = servicoTransacoes.buscaTransacoes(100000, 2020, mes);
        return (List<Transacao>) response.getEntity();
    }

    @Test
    void testaValidacaoParametroMesAbaixoDoMinimo() {
        Response response = servicoTransacoes.buscaTransacoes(100000, 2020, 0);

        assertEquals(validaParametroMesMensagemEsperada(),
                response.getEntity());
    }

    @Test
    void testaValidacaoParametroMesAcimaDoMaximo() {
        Response response = servicoTransacoes.buscaTransacoes(100000, 2020, 13);

        assertEquals(validaParametroMesMensagemEsperada(),
                response.getEntity());
    }

    @Test
    void testaValidacaoParametroIdAbaixoDoMinimo() {
        Response response = servicoTransacoes.buscaTransacoes(11, 2020, 10);

        assertEquals(validaParametroIdMensagemEsperada(),
                response.getEntity());
    }

    @Test
    void testaValidacaoParametroIdAcimaDoMaximo() {
        Response response = servicoTransacoes.buscaTransacoes(999999999, 2020, 10);

        assertEquals(validaParametroIdMensagemEsperada(),
                response.getEntity());
    }

    private String validaParametroMesMensagemEsperada() {
        StringBuilder mensagemErroEsperada = new StringBuilder();
        mensagemErroEsperada.append("Ocorreu um erro: 400 - Bad Request").append(System.lineSeparator());
        mensagemErroEsperada.append("O parâmetro \"mes\" deve ser um número entre 1 e 12!").append(System.lineSeparator());
        mensagemErroEsperada.append("====================").append(System.lineSeparator());
        mensagemErroEsperada.append("Serviços disponíveis:").append(System.lineSeparator());
        mensagemErroEsperada.append("Busca Transações: [GET] /<id>/transacoes/<ano>/<mes>").append(System.lineSeparator());
        mensagemErroEsperada.append("====================").append(System.lineSeparator());
        mensagemErroEsperada.append("Para maiores informações acesse: https://github.com/nilson-paiao/api-mock-transacoes");
        return mensagemErroEsperada.toString();
    }

    private String validaParametroIdMensagemEsperada() {
        StringBuilder mensagemErroEsperada = new StringBuilder();
        mensagemErroEsperada.append("Ocorreu um erro: 400 - Bad Request").append(System.lineSeparator());
        mensagemErroEsperada.append("O parâmetro \"id\" deve ser um número entre 1.000 e 100.000.000!").append(System.lineSeparator());
        mensagemErroEsperada.append("====================").append(System.lineSeparator());
        mensagemErroEsperada.append("Serviços disponíveis:").append(System.lineSeparator());
        mensagemErroEsperada.append("Busca Transações: [GET] /<id>/transacoes/<ano>/<mes>").append(System.lineSeparator());
        mensagemErroEsperada.append("====================").append(System.lineSeparator());
        mensagemErroEsperada.append("Para maiores informações acesse: https://github.com/nilson-paiao/api-mock-transacoes");
        return mensagemErroEsperada.toString();
    }
}