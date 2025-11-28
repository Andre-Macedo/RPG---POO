package model;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
    private List<Jogador> membros;

    public Grupo() {
        this.membros = new ArrayList<>();
    }

    public void adicionarMembro(Jogador j) {
        if (membros.size() < 4) {
            membros.add(j);
        }
    }

    public List<Jogador> getMembros() {
        return membros;
    }

    public List<Jogador> getMembrosVivos() {
        List<Jogador> vivos = new ArrayList<>();
        for (Jogador j : membros) {
            if (j.isEstaVivo()) vivos.add(j);
        }
        return vivos;
    }

    public boolean todoMundoMorreu() {
        return getMembrosVivos().isEmpty();
    }
}