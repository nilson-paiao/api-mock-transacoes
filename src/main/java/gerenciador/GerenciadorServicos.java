package gerenciador;

import servico.ServicoTransacoes;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorServicos {

    public static final String MENSAGEM_INFOMACOES_SERVICOS = inicializaMensagemInformacoesServicos();

    private static String inicializaMensagemInformacoesServicos() {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("====================").append(System.lineSeparator());
        mensagem.append("Servicos disponiveis:").append(System.lineSeparator());

        for (String servicoUrl : getServicosDisponiveisUrl()) {
            mensagem.append(servicoUrl).append(System.lineSeparator());
        }

        mensagem.append("====================").append(System.lineSeparator());
        mensagem.append("Para maiores informacoes acesse: https://github.com/nilson-paiao/api-mock-transacoes");

        return mensagem.toString();
    }

    private static List<String> getServicosDisponiveisUrl() {
        List<String> servicosDisponiveisUrl = new ArrayList<>();
        servicosDisponiveisUrl.add(ServicoTransacoes.SERVICO_BUSCA_TRANSACOES_URL);
        return servicosDisponiveisUrl;
    }


}
