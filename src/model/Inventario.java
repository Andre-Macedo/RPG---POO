package model;

import utils.GameLog;

import java.util.ArrayList;
import java.util.List;

// REQUISITO: APLICAÇÃO DE CLASSE GENÉRICA E COLEÇÕES
// Esta classe usa 'ArrayList<Item>', que é uma implementação
// da interface 'List' (Coleções).
// 'ArrayList<E>' é uma Classe Genérica, onde especificamos
// que o tipo que ela irá guardar é 'Item'.
public class Inventario {

    private boolean imprimirMensagens = true;
    private List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        this.itens.add(item);
        if (imprimirMensagens) {
            GameLog.print("  > " + item.getNome() + " foi adicionado ao inventário.");
        }
    }

    public void removerItem(Item item) {
        this.itens.remove(item);
        if (imprimirMensagens) {
            GameLog.print("  > " + item.getNome() + " foi removido do inventário.");
        }
    }

    public Item getItemPorNome(String nome) {

        for (Item item : this.itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                return item;
            }
        }
        return null;
    }

    public void listarItens() {
        if (itens.isEmpty()) {
            GameLog.print("Inventário está vazio.");
            return;
        }

        GameLog.print("--- Inventário ---");

        for (Item item : this.itens) {
            GameLog.print("- " + item.getNome() + ": " + item.getDescricao());
        }
        GameLog.print("------------------");
    }

    public void setImprimirMensagens(boolean imprimir) {
        this.imprimirMensagens = imprimir;
    }

    public List<Item> getItens() {
        return this.itens;
    }
}