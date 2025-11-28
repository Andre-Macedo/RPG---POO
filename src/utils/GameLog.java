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
        } else {
            System.out.println(mensagem);
        }
    }
}