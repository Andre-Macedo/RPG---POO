package view;

// REQUISITO: APLICAÇÃO DE INTERFACE
// Esta interface define o contrato para qualquer tipo de View
// (seja console ou gráfica). O Controller irá programar
// para esta interface, e não para a implementação concreta.
public interface GameView {

    /**
     * Exibe uma mensagem padrão para o usuário.
     * @param mensagem A mensagem a ser exibida.
     */
    void exibirMensagem(String mensagem);

    /**
     * Exibe uma mensagem de erro.
     * @param mensagem A mensagem de erro.
     */
    void exibirErro(String mensagem);

    /**
     * Solicita e lê uma linha de comando do usuário.
     * @return O texto digitado pelo usuário.
     */
    String obterEntradaUsuario();

    /**
     * Exibe uma mensagem com efeito de maquina de escrerver
     * @param mensagem a mensagem a ser exibida
     */
    void exibirNarrativa(String mensagem);
}