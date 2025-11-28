package view;

import java.util.Scanner;

public class ConsoleView implements GameView {

    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void exibirMensagem(String mensagem) {

        if (mensagem.contains("Rolagem") || mensagem.contains("Dano:")) {
            System.out.println("    " + mensagem);
        } else if (mensagem.contains("SUCESSO")) {
            System.out.println("  [!] " + mensagem.toUpperCase());
        } else if (mensagem.contains("FALHA")) {
            System.out.println("  [X] " + mensagem);
        } else {
            System.out.println(mensagem);
        }
    }
    @Override
    public void exibirErro(String mensagem) {
        System.err.println("ERRO: " + mensagem);
    }

    @Override
    public String obterEntradaUsuario() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    @Override
    public void exibirNarrativa(String mensagem) {

    }
}