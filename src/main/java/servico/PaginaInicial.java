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
    @Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
    public Response buscaTransacoes() {
        String mensagemBoasVindas = "Bem vindo à API mock de transações! " + System.lineSeparator() + GerenciadorServicos.MENSAGEM_INFOMACOES_SERVICOS;
        return GerenciadorResponse.geraResponseSucesso(mensagemBoasVindas);
    }

}
