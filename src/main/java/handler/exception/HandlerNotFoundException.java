package handler.exception;

import gerenciador.GerenciadorResponse;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class HandlerNotFoundException implements ExceptionMapper<NotFoundException> {

    @Context
    private HttpHeaders headers;

    @Override
    public Response toResponse(NotFoundException notFoundException){
        return GerenciadorResponse.geraResponseErro(Response.Status.NOT_FOUND, "Requisicao invalida");
    }

}