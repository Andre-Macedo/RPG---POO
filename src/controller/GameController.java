package controller;

import model.EstadoJogo;
import model.GameEngine;
import model.JogoException;
import view.GameView;

public class GameController {

    private GameEngine model;

    // REQUISITO: APLICAÇÃO DE POLIMORFISMO
    private GameView view;

    public GameController(GameEngine model, GameView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Inicia o loop principal do jogo.
     */
    public void iniciarJogo() {

        while (true) {
            String comando = view.obterEntradaUsuario();

            if (comando.equalsIgnoreCase("sair")) {
                view.exibirMensagem("Obrigado por jogar. Até a próxima!");
                break;
            }

            // REQUISITO: APLICAÇÃO DE TRATAMENTO DE EXCEÇÕES
            try {
                model.processarComando(comando);
            } catch (JogoException e) {
                view.exibirErro(e.getMessage());
            }

            if (model.getEstadoJogo() == EstadoJogo.FIM_DE_JOGO) {
                view.exibirMensagem("\n--- FIM DE JOGO ---");
                break;
            }
        }
    }
}