package servico;

import gerenciador.GerenciadorResponse;
import gerenciador.GerenciadorServicos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class PaginaInicial {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response buscaTransacoes() {
        String mensagemBoasVindas = "Bem vindo a API mock de transacoes! " + System.lineSeparator() + GerenciadorServicos.MENSAGEM_INFOMACOES_SERVICOS;
        return GerenciadorResponse.geraResponseSucesso(mensagemBoasVindas);
    }

}
