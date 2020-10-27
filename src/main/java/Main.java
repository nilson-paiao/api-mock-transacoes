import handler.exception.HandlerNotFoundException;
import servico.PaginaInicial;
import servico.ServicoTransacoes;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class Main extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(PaginaInicial.class );
        classes.add(ServicoTransacoes.class );
        classes.add(HandlerNotFoundException.class );
        return classes;
    }

}