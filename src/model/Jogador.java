package model;

import utils.GameLog;

// REQUISITO: APLICAÇÃO DE HERANÇA
// 'Jogador' "é uma" 'Entidade'. Ela herda todos os atributos (nome, vida)
// e métodos (receberDano, curar, getters) da classe 'Entidade'.
public class Jogador extends Entidade {

    // Atributos específicos do Jogador
    private int nivel;
    private int experiencia;
    private Inventario inventario;
    private ArmaSimples armaEquipada;


    public Jogador(String nome) {
        super(nome, 100, new Atributos(16, 12, 14, 8, 10, 12));

        this.nivel = 1;
        this.experiencia = 0;


        this.inventario = new Inventario();

        this.inventario.setImprimirMensagens(false);

        ArmaSimples adagaInicial = new ArmaSimples("Adaga", "Uma adaga simples.", 4);
        this.inventario.adicionarItem(adagaInicial);
        this.equiparArma(adagaInicial);

        this.inventario.adicionarItem(new PocaoCura("Poção de Cura Fraca", "Cura 20 HP.", 20));

        this.inventario.setImprimirMensagens(true);
    }

    /**
     * Tenta usar um item do inventário pelo nome.
     *
     * @param nomeDoItem O nome do item a ser usado.
     */
    public void usarItem(String nomeDoItem) {
        Item item = this.inventario.getItemPorNome(nomeDoItem);
        if (item == null) {
            GameLog.print("Você não possui o item '" + nomeDoItem + "'.");
            return;
        }

        item.usar(this);

        if (item instanceof PocaoCura) {
            this.inventario.removerItem(item);
        }
    }

    public void equiparArma(ArmaSimples arma) {

        if (this.inventario.getItens().contains(arma)) {
            this.armaEquipada = arma;
        } else {
            GameLog.print("Não é possível equipar " + arma.getNome() + " (não está no inventário).");
        }
    }

    // REQUISITO: APLICAÇÃO DE POLIMORFISMO (Sobrescrita)
    @Override
    public void atacar(Entidade alvo) {
        if (!this.isEstaVivo() || !alvo.isEstaVivo()) {
            GameLog.print("Ação inválida.");
            return;
        }

        GameLog.print(this.getNome() + " tenta atacar " + alvo.getNome() + "...");

        int d20 = Dados.d20();

        int modForca = this.getAtributos().getModForca();

        int bonusProficiencia = 1 + this.getNivel();

        int totalAtaque = d20 + modForca + bonusProficiencia;

        GameLog.print("  > Rolagem de Ataque: " + d20 + " + " + modForca + " (FOR) + " + bonusProficiencia + " (PROF) = " + totalAtaque);
        GameLog.print("  > Classe de Armadura (AC) do alvo: " + alvo.getClasseArmadura());

        if (totalAtaque >= alvo.getClasseArmadura()) {

            GameLog.print("  > ACERTOU!");

            int danoArma = 0;
            if (this.getArmaEquipada() != null) {

                danoArma = this.getArmaEquipada().getDanoBase();
            } else {
                danoArma = 1;
            }

            int danoTotal = danoArma + modForca;
            if (danoTotal < 1) danoTotal = 1;

            GameLog.print("  > Dano: " + danoArma + " (Arma) + " + modForca + " (FOR) = " + danoTotal);
            alvo.receberDano(danoTotal);

            if (!alvo.isEstaVivo() && alvo instanceof Monstro) {
                this.ganharExperiencia(((Monstro) alvo).getXpConcedido());
            }

        } else {
            GameLog.print("  > ERROU! O ataque foi desviado ou bloqueado pela armadura.");
        }
    }

    // Método específico da classe Jogador
    public void ganharExperiencia(int xp) {
        if (!this.isEstaVivo()) return;

        this.experiencia += xp;
        GameLog.print(this.getNome() + " ganhou " + xp + " de XP.");

        int xpParaProximoNivel = this.nivel * 100;
        if (this.experiencia >= xpParaProximoNivel) {
            this.experiencia -= xpParaProximoNivel;
            subirNivel();
        }

    }

    private void subirNivel() {
        this.nivel++;

        int aumentoVida = 20;

        super.curar(aumentoVida);
        GameLog.print(this.getNome() + " subiu para o Nível " + this.nivel + "!");
    }



    public void setAtributos(Atributos novosAtributos) {
        super.substituirAtributos(novosAtributos);
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public ArmaSimples getArmaEquipada() {
        return armaEquipada;
    }

    public void verInventario() {
        this.inventario.listarItens();
    }
}
