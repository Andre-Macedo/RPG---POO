package model.monstros;

import model.Atributos;
import model.Entidade;
import model.Monstro;
import utils.GameLog;
import model.Dados;

// Herança
public class GoblinArqueiro extends Monstro {

    public GoblinArqueiro() {
        super("Goblin Arqueiro", 12, "Goblin", 50,
                new Atributos(8, 16, 10, 10, 8, 8));
    }

    @Override
    public void atacar(Entidade alvo) {
        if (!this.isEstaVivo()) return;

        GameLog.print(this.getNome() + " dispara uma flecha contra " + alvo.getNome() + "!");

        int d20 = Dados.d20();
        int modDestreza = this.getAtributos().getModDestreza();
        int totalAtaque = d20 + modDestreza + 2;

        if (totalAtaque >= alvo.getClasseArmadura()) {
            int dano = Dados.d6() + modDestreza;
            GameLog.print("  > A flecha acerta! (Rolagem: " + totalAtaque + " vs AC " + alvo.getClasseArmadura() + ")");
            alvo.receberDano(dano);
        } else {
            GameLog.print("  > A flecha passa zunindo! (Rolagem: " + totalAtaque + " vs AC " + alvo.getClasseArmadura() + ")");
        }
    }
}