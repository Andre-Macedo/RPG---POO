package model;

import utils.GameLog;

import java.util.ArrayList;
import java.util.List;

public class Monstro extends Entidade {

    private String tipo;
    private int xpConcedido;
    private List<Item> loot;

    public Monstro(String nome, int pontosDeVidaMaximos, String tipo, int xpConcedido, Atributos atributos) {
        super(nome, pontosDeVidaMaximos, atributos);
        this.tipo = tipo;
        this.xpConcedido = xpConcedido;
        this.loot = new ArrayList<>(); //
    }

    // REQUISITO: APLICAÇÃO DE POLIMORFISMO (Sobrescrita)
    // O Monstro implementa o 'atacar' de forma diferente do Jogador.
    @Override
    public void atacar(Entidade alvo) {
        if (!this.isEstaVivo()) return;

        GameLog.print(this.getNome() + " ataca " + alvo.getNome() + "!");

        int d20 = Dados.d20();
        int modDestreza = this.getAtributos().getModDestreza();
        int totalAtaque = d20 + modDestreza + 2; // +2 é a proficiência do Goblin

        if (totalAtaque >= alvo.getClasseArmadura()) {
            int dano = Dados.d6() + modDestreza;
            GameLog.print("  > " + this.getNome() + " acerta! (Rolagem: " + totalAtaque + " vs AC " + alvo.getClasseArmadura() + ")");
            alvo.receberDano(dano);
        } else {
            GameLog.print("  > " + this.getNome() + " erra o ataque. (Rolagem: " + totalAtaque + " vs AC " + alvo.getClasseArmadura() + ")");
        }
    }

    public void adicionarItemLoot(Item item) {
        this.loot.add(item);
    }

    public String getTipo() {
        return tipo;
    }

    public int getXpConcedido() {
        return xpConcedido;
    }

    public List<Item> getLoot() {
        return loot;
    }
}
