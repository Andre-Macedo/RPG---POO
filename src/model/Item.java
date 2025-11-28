package model;

// REQUISITO: APLICAÇÃO DE INTERFACE
// A interface 'Item' define um um conjunto de métodos que
// qualquer classe de item deve implementar.
// Isso permite que o Inventario trate 'PocaoCura' e 'ArmaSimples'
// da mesma forma (como 'Item').
public interface Item {

    /**
     * Retorna o nome do item.
     * @return O nome do item.
     */
    String getNome();

    /**
     * Retorna a descrição do item.
     * @return A descrição do item.
     */
    String getDescricao();

    /**
     * Executa a ação principal do item.
     * Por exemplo, beber uma poção ou equipar uma arma.
     * @param alvo A entidade que está usando o item.
     */
    void usar(Entidade alvo);
}