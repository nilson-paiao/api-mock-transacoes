package entidade;

import gerador.GeradorFraseAleatoria;
import gerador.GeradorNumeroAleatorio;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Transacao {

    private final String descricao;
    private final Long data;
    private final int valor;
    private final boolean duplicated;

    public Transacao(final int mes, final int ano) {
        this.descricao = gerarDescricaoAleatoria();
        this.data = gerarDataAleatoria(mes, ano);
        this.valor = gerarValorAleatorio();
        this.duplicated = false;
    }

    public Transacao(Transacao transacao) {
        this.descricao = transacao.descricao;
        this.data = transacao.data;
        this.valor = transacao.valor;
        this.duplicated = true;
    }

    public Transacao duplicarTransacao() {
        return new Transacao(this);
    }

    private Long gerarDataAleatoria(final int mes, final int ano) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, mes-1);
        calendar.set(Calendar.YEAR, ano);

        int diaAleatorio = GeradorNumeroAleatorio.gerarNumero(calendar.getActualMinimum(Calendar.DAY_OF_MONTH), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, diaAleatorio);
        return calendar.getTimeInMillis();
    }

    private String gerarDescricaoAleatoria() {
        return GeradorFraseAleatoria.gerarFrase(10);
    }

    private int gerarValorAleatorio() {
        return GeradorNumeroAleatorio.gerarNumero(-9999999, 9999999);
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getData() {
        return data;
    }

    public int getValor() {
        return valor;
    }

    public boolean isDuplicated() {
        return duplicated;
    }
}
