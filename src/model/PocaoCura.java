package model;

import utils.GameLog;

public class PocaoCura implements Item {

    private String nome;
    private String descricao;
    private int pontosDeCura;

    public PocaoCura(String nome, String descricao, int pontosDeCura) {
        this.nome = nome;
        this.descricao = descricao;
        this.pontosDeCura = pontosDeCura;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    // A 'PocaoCura' implementa 'usar' de forma específica.
    @Override
    public void usar(Entidade alvo) {
        GameLog.print(alvo.getNome() + " usa " + this.nome + ".");
        alvo.curar(this.pontosDeCura);
    }

    public int getPontosDeCura() {
        return pontosDeCura;
    }
}