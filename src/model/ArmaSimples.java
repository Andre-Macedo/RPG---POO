package model;

import utils.GameLog;

// Interface
public class ArmaSimples implements Item {

    // Encapsulamento
    private String nome;
    private String descricao;
    private int danoBase;

    public ArmaSimples(String nome, String descricao, int danoBase) {
        this.nome = nome;
        this.descricao = descricao;
        this.danoBase = danoBase;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    // 'Usar' uma arma significa equipá-la.
    @Override
    public void usar(Entidade alvo) {
        // Verifica se o alvo é um Jogador para poder equipar
        if (alvo instanceof Jogador) {
            Jogador jogador = (Jogador) alvo;
            jogador.equiparArma(this);
            GameLog.print(jogador.getNome() + " equipou " + this.nome + ".");
        } else {
            GameLog.print(this.nome + " não pode ser equipado por " + alvo.getNome());
        }
    }

    public int getDanoBase() {
        return danoBase;
    }
}