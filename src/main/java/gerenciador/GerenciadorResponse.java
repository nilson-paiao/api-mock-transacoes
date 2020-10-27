package gerenciador;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;

public class GerenciadorResponse {

    private static final String MENSAGEM_ERRO_TEMPLATE = inicializaMensagemErroTemplate();

    public static Response geraResponseSucesso(Object entidade) {
        return Response.
                ok(entidade).
                build();
    }

    public static Response geraResponseErro(Response.Status status, String mensagemErro) {
        return Response.
                status(status).
                entity(formataMensagemErro(status, mensagemErro)).
                type(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1").
                build();
    }

    private static String inicializaMensagemErroTemplate() {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Ocorreu um erro: {0} - {1}").append(System.lineSeparator());
        mensagem.append("{2}").append(System.lineSeparator());
        mensagem.append(GerenciadorServicos.MENSAGEM_INFOMACOES_SERVICOS);
        return mensagem.toString();
    }

    private static String formataMensagemErro(Response.Status status, String mensagemErro) {
        return MessageFormat.format(
                MENSAGEM_ERRO_TEMPLATE,
                status.getStatusCode(),
                status.getReasonPhrase(),
                mensagemErro
        );
    }

}
