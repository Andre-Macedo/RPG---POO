package model;

public class Atributos {

    // Encapsulamento
    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;

    public Atributos(int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma) {
        this.forca = forca;
        this.destreza = destreza;
        this.constituicao = constituicao;
        this.inteligencia = inteligencia;
        this.sabedoria = sabedoria;
        this.carisma = carisma;
    }

    // Método utilitário para calcular o modificador baseado no valor
    // Ex: 10 -> 0, 12 -> +1, 8 -> -1, 16 -> +3
    public int getModificador(int valorAtributo) {
        return (int) Math.floor((valorAtributo - 10) / 2.0);
    }


    public int getModForca() { return getModificador(forca); }
    public int getModDestreza() { return getModificador(destreza); }
    public int getModConstituicao() { return getModificador(constituicao); }
    public int getModInteligencia() { return getModificador(inteligencia); }
    public int getModSabedoria() { return getModificador(sabedoria); }
    public int getModCarisma() { return getModificador(carisma); }

    public int getForca() { return forca; }
    public int getDestreza() { return destreza; }
    public int getConstituicao() { return constituicao; }
    public int getInteligencia() { return inteligencia; }
    public int getSabedoria() { return sabedoria; }
    public int getCarisma() { return carisma; }
}