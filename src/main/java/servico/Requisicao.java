package servico;

import java.util.Objects;

public class Requisicao {

    private final int id;
    private final int ano;
    private final int mes;

    public Requisicao(int id, int ano, int mes) {
        this.id = id;
        this.ano = ano;
        this.mes = mes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requisicao that = (Requisicao) o;
        return id == that.id &&
                ano == that.ano &&
                mes == that.mes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ano, mes);
    }

    public int getAno() {
        return ano;
    }

    public int getMes() {
        return mes;
    }
}
