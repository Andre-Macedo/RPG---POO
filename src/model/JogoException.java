package model;

// REQUISITO: APLICAÇÃO DE TRATAMENTO DE EXCEÇÕES
// Esta é uma classe de exceção customizada. Nós a usaremos para
// sinalizar erros específicos da lógica do nosso jogo (como um comando
// inválido ou uma ação impossível).
// Ela herda de 'Exception' ou seja, o Java nos obriga a tratá-la (com try-catch ou throws).
public class JogoException extends Exception {

    public JogoException(String mensagem) {
        super(mensagem);
    }
}