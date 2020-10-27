package servico;

import gerenciador.GerenciadorResponse;
import mock.MockTransacoes;
import entidade.Transacao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/{id}/transacoes")
public class ServicoTransacoes {

    public static final String SERVICO_BUSCA_TRANSACOES_URL = "Busca Transacoes: [GET] /<id>/transacoes/<ano>/<mes>";

    @GET
    @Path("{ano}/{mes}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscaTransacoes(@PathParam("id") int usuarioId, @PathParam("ano") int ano, @PathParam("mes") int mes) {

        boolean idEstaInvalido = usuarioId < 1000 || usuarioId > 100000000;
        if (idEstaInvalido) {
            return GerenciadorResponse.geraResponseErro(Response.Status.BAD_REQUEST, "O parametro \"id\" deve ser um numero entre 1.000 e 100.000.000!");
        }

        boolean mesEstaInvalido = mes < 1 || mes > 12;
        if (mesEstaInvalido) {
            return GerenciadorResponse.geraResponseErro(Response.Status.BAD_REQUEST, "O parametro \"mes\" deve ser um numero entre 1 e 12!");
        }

        Requisicao requisicao = new Requisicao(usuarioId, ano, mes);
        List<Transacao> transacoes = MockTransacoes.obterTransacoes(requisicao);

        return GerenciadorResponse.geraResponseSucesso(transacoes);
    }

}
