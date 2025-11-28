package model;


import utils.GameLog;

// REQUISITO: APLICAÇÃO DE CLASSE ABSTRATA
// Entidade é uma classe abstrata que define a base para qualquer ser vivo no jogo.
public abstract class Entidade {

    // REQUISITO: APLICAÇÃO DE ENCAPSULAMENTO
    // Todos os atributos são 'private'. O acesso a eles é controlado
    // por métodos públicos (getters) ou métodos de regra de negócio (receberDano, curar).
    private String nome;
    private int pontosDeVida;
    private int pontosDeVidaMaximos;
    private boolean estaVivo;
    private Atributos atributos;
    private int classeArmadura;

    public Entidade(String nome, int pontosDeVidaMaximos, Atributos atributos) {
        this.nome = nome;
        this.pontosDeVidaMaximos = pontosDeVidaMaximos;
        this.pontosDeVida = pontosDeVidaMaximos;
        this.estaVivo = true;
        this.atributos = atributos;

        // Em D&D, a AC base é 10 + Modificador de Destreza (sem armadura)
        this.classeArmadura = 10 + atributos.getModDestreza();
    }

    /**
     * Aplica dano à entidade. Este método encapsula a lógica
     * de não deixar a vida ficar negativa e de atualizar o status 'estaVivo'.
     * @param dano O total de dano a ser recebido.
     */
    public void receberDano(int dano) {
        if (dano < 0) return;

        this.pontosDeVida -= dano;
        GameLog.print("  > " + this.nome + " recebe " + dano + " de dano. Vida atual: " + this.pontosDeVida);

        if (this.pontosDeVida <= 0) {
            this.pontosDeVida = 0;
            this.estaVivo = false;
            GameLog.print("  > " + this.nome + " foi derrotado!");
        }
    }

    /**
     * Cura a entidade. Este método encapsula a lógica
     * de não ultrapassar a vida máxima.
     * @param cura O total de vida a ser recuperado.
     */
    public void curar(int cura) {
        if (cura < 0 || !this.estaVivo) return; // Não pode curar negativo ou se estiver morto

        this.pontosDeVida += cura;
        if (this.pontosDeVida > this.pontosDeVidaMaximos) {
            this.pontosDeVida = this.pontosDeVidaMaximos;
        }
        GameLog.print("  > " + this.nome + " recupera " + cura + " de vida. Vida atual: " + this.pontosDeVida);
    }

    // REQUISITO: PREPARAÇÃO PARA POLIMORFISMO
    public abstract void atacar(Entidade alvo);

    public String getNome() {
        return nome;
    }

    public int getPontosDeVida() {
        return pontosDeVida;
    }

    public int getPontosDeVidaMaximos() {
        return pontosDeVidaMaximos;
    }

    public boolean isEstaVivo() {
        return estaVivo;
    }

    public Atributos getAtributos() {
        return atributos;
    }

    public void setClasseArmadura(int ac) {
        this.classeArmadura = ac;
    }

    public int getClasseArmadura() {
        return classeArmadura;
    }

    protected void substituirAtributos(Atributos novosAtributos) {
        this.atributos = novosAtributos;

        this.classeArmadura = 10 + atributos.getModDestreza();
    }
}
