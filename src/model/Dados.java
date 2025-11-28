package model;

import java.util.List;
import java.util.Random;

public class Dados {

    private static Random random = new Random();

    /**
     * Rola um dado de N faces.
     * @param faces Número de faces (ex: 20 para d20).
     * @return Um número entre 1 e faces.
     */
    public static int rolar(int faces) {
        return random.nextInt(faces) + 1;
    }

    // Atalhos para os dados mais comuns
    public static int d20() { return rolar(20); }
    public static int d12() { return rolar(12); }
    public static int d10() { return rolar(10); }
    public static int d8()  { return rolar(8); }
    public static int d6()  { return rolar(6); }
    public static int d4()  { return rolar(4); }

    // --- REQUISITO: MÉTODO GENÉRICO  ---
    // O <T> antes do tipo de retorno indica que este é um método genérico.
    // Ele funciona para List<String>, List<Jogador>, List<Monstro>, etc.
    public static <T> T escolherAleatorio(List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        int indice = random.nextInt(lista.size());
        return lista.get(indice);
    }
}