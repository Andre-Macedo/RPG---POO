package utils;

import view.GameView;

public class GameLog {
    private static GameView viewDestino;

    public static void setView(GameView view) {
        viewDestino = view;
    }

    public static void print(String mensagem) {
        if (viewDestino != null) {
            viewDestino.exibirMensagem(mensagem);
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println(mensagem);
        }
    }
    public static void printRapido(String mensagem) {
        if (viewDestino != null) {
            viewDestino.exibirMensagem(mensagem);
            // pequeno delay
            try { Thread.sleep(50); } catch (Exception e) {}
        }
    }

    public static void narrar(String mensagem) {
        if (viewDestino != null) {
            viewDestino.exibirNarrativa(mensagem);
        } else {
            System.out.println(mensagem); // Fallback para console
        }
    }
}