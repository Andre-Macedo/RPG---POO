package model;

import model.monstros.GoblinArqueiro;
import utils.GameLog;
import model.EstadoJogo;

import java.util.ArrayList;
import java.util.List;

// REQUISITO: APLICAÇÃO DE AGREGAÇÃO
public class GameEngine {

    private Grupo grupoJogadores;
    private List<Monstro> monstrosAtuais;
    private EstadoJogo estadoJogo;
    private boolean emboscadaVencida = false;

    // Controle de turno: índice de qual jogador do grupo está agindo agora
    private int indiceJogadorAtual = 0;

    public GameEngine(Grupo grupo) {
        this.grupoJogadores = grupo;
        this.estadoJogo = EstadoJogo.EXPLORANDO;
        this.monstrosAtuais = new ArrayList<>();
    }

    /**
     * Retorna o primeiro jogador do grupo (Líder) para ações de exploração.
     */
    public Jogador getLider() {
        if (grupoJogadores.getMembrosVivos().isEmpty()) return null;
        return grupoJogadores.getMembros().get(0);
    }

    /**
     * Tenta investigar algo no cenário.
     */
    public void investigarCenario(String alvo) {
        if (emboscadaVencida) {
            GameLog.print("Tudo o que resta é o corpo do Goblin e os cavalos. A emboscada acabou.");
            return;
        }

        if (this.estadoJogo != EstadoJogo.EXPLORANDO) {
            GameLog.print("Não há tempo para investigar agora! Você está em combate!");
            return;
        }

        Jogador lider = getLider();
        if (!lider.isEstaVivo()) {
            GameLog.print("O líder está inconsciente e não pode investigar.");
            return;
        }

        StringBuilder resultado = new StringBuilder();
        int rolagem = Dados.d20();
        int modificador = lider.getAtributos().getModInteligencia();
        int total = rolagem + modificador;

        resultado.append("Rolagem de Investigação: ").append(rolagem)
                .append(" + ").append(modificador).append(" (INT) = ").append(total).append("\n");

        boolean iniciarCombate = false;
        boolean surpresa = false;

        if (alvo.equalsIgnoreCase("cavalos")) {
            if (total >= 10) {
                resultado.append("SUCESSO: Ao examinar os cavalos, você nota flechas de penas pretas.\n");
                resultado.append("São flechas de Goblin! O grupo ganha 10 XP.");

                for(Jogador j : grupoJogadores.getMembrosVivos()) j.ganharExperiencia(10);
            } else {
                resultado.append("FALHA: Os cavalos estão mortos. Nada mais chama atenção.");
            }
        } else if (alvo.equalsIgnoreCase("arbustos")) {
            int modSabedoria = lider.getAtributos().getModSabedoria();
            int totalSab = Dados.d20() + modSabedoria;

            resultado.append("\n(Teste de Percepção do Líder: ").append(totalSab).append(")\n");

            if (totalSab >= 15) {
                resultado.append("SUCESSO: Você nota movimento! Goblins estavam escondidos!");
                iniciarCombate = true;
                surpresa = false;
            } else {
                resultado.append("FALHA: O vento balança os arbustos...\n");
                resultado.append("DE REPENTE! Flechas voam e Goblins saltam!");
                iniciarCombate = true;
                surpresa = true;
            }
        } else {
            resultado.append("Não há nada de interessante em '").append(alvo).append("'.");
        }

        GameLog.print(resultado.toString());

        if (iniciarCombate) {
            iniciarCombate(surpresa);
        }
    }

    /**
     * Inicia o combate gerando os monstros e definindo a ordem.
     */
    public void iniciarCombate(boolean surpresa) {
        this.estadoJogo = EstadoJogo.COMBATE;
        this.monstrosAtuais.clear();
        this.indiceJogadorAtual = 0;


        Atributos attrGoblin = new Atributos(8, 14, 10, 10, 8, 8);
        Monstro batedor = new Monstro("Goblin Batedor", 15, "Goblin", 50, attrGoblin);

        batedor.adicionarItemLoot(new PocaoCura("Poção de Cura Fraca", "Cura 20 HP", 20));
        monstrosAtuais.add(batedor);

        monstrosAtuais.add(new GoblinArqueiro());

        GameLog.print("\n!!! COMBATE INICIADO !!!");
        GameLog.print("Você enfrenta " + monstrosAtuais.size() + " inimigos!");

        if (surpresa) {
            GameLog.print(">> ATAQUE SURPRESA DOS INIMIGOS! <<");
            turnoDosMonstros();
        } else {
            anunciarTurnoJogador();
        }
    }

    public void processarComando(String comando) throws JogoException {
        if (comando == null || comando.trim().isEmpty()) {
            throw new JogoException("Comando não pode ser vazio.");
        }

        if (this.estadoJogo == EstadoJogo.COMBATE) {
            processarCombate(comando);
            return;
        }

        String[] partes = comando.toLowerCase().split(" ");
        String acao = partes[0];

        switch (acao) {
            case "investigar":
                if (partes.length < 2) throw new JogoException("Investigar o quê?");
                String alvo = comando.substring(11);
                investigarCenario(alvo);
                break;

            case "inventario":
                getLider().verInventario();
                break;

            case "status":
                exibirStatus();
                break;

            case "continuar":
                if (emboscadaVencida) {
                    GameLog.print("\nVocê segue a trilha dos Goblins mata adentro...");
                    GameLog.print("PARABÉNS! Demo concluída.");

                    this.estadoJogo = EstadoJogo.FIM_DE_JOGO;
                } else {
                    GameLog.print("Você não pode sair com inimigos por perto!");
                }
                break;

            case "atacar":
                GameLog.print("Não há ninguém para atacar agora.");
                break;

            default:
                throw new JogoException("Comando desconhecido: '" + acao + "'");
        }
    }

    /**
     * Nova lógica de combate baseada em turnos de grupo.
     */
    private void processarCombate(String comando) throws JogoException {

        List<Jogador> vivos = grupoJogadores.getMembrosVivos();
        if (vivos.isEmpty()) {
            GameLog.print("Todos os heróis caíram...");
            return;
        }

        if (indiceJogadorAtual >= vivos.size()) indiceJogadorAtual = 0;

        Jogador jogadorAtual = vivos.get(indiceJogadorAtual);
        String[] partes = comando.toLowerCase().split(" ");
        String acao = partes[0];

        if (acao.equals("atacar")) {
            int indiceAlvo = 0;
            if (partes.length > 1) {
                try {
                    indiceAlvo = Integer.parseInt(partes[1]) - 1;
                } catch (NumberFormatException e) {
                    throw new JogoException("Use 'atacar [número]'. Ex: atacar 1");
                }
            }

            if (indiceAlvo < 0 || indiceAlvo >= monstrosAtuais.size()) {
                throw new JogoException("Alvo inválido.");
            }

            Monstro alvo = monstrosAtuais.get(indiceAlvo);
            if (!alvo.isEstaVivo()) throw new JogoException("Esse monstro já está morto.");

            jogadorAtual.atacar(alvo);

            verificarMortesMonstros();
            if (monstrosAtuais.isEmpty()) return;

            proximoTurnoJogador();

        } else if (acao.equals("fugir")) {
            GameLog.print(jogadorAtual.getNome() + " tenta fugir, mas está cercado!");
            proximoTurnoJogador(); // Perde o turno tentando fugir
        } else {
            GameLog.print("Comando de combate inválido. Use: 'atacar [n]' ou 'usar [item]'.");
        }
    }

    private void proximoTurnoJogador() {
        indiceJogadorAtual++;
        List<Jogador> vivos = grupoJogadores.getMembrosVivos();

        if (indiceJogadorAtual >= vivos.size()) {
            indiceJogadorAtual = 0;
            turnoDosMonstros();
        } else {
            anunciarTurnoJogador();
        }
    }

    private void anunciarTurnoJogador() {
        List<Jogador> vivos = grupoJogadores.getMembrosVivos();
        if (vivos.isEmpty()) return;

        if (indiceJogadorAtual >= vivos.size()) indiceJogadorAtual = 0;
        Jogador atual = vivos.get(indiceJogadorAtual);

        GameLog.print("\n========================================");
        GameLog.print(" VEZ DE " + atual.getNome().toUpperCase() + " (HP " + atual.getPontosDeVida() + ")");
        GameLog.print("========================================");
        listarMonstros();
        GameLog.print("----------------------------------------");
        GameLog.print(" OPÇÕES:");
        GameLog.print("  atacar [n]   - Atacar inimigo N");
        GameLog.print("  usar [item]  - Usar item do inventário");
        GameLog.print("  fugir        - Tentar fugir");
        GameLog.print("----------------------------------------");
        GameLog.print("Qual sua ação?");
    }


    private void listarMonstros() {
        for (int i = 0; i < monstrosAtuais.size(); i++) {
            Monstro m = monstrosAtuais.get(i);
            String status = m.isEstaVivo() ? " (HP " + m.getPontosDeVida() + ")" : " [MORTO]";

            GameLog.print(" [" + (i+1) + "] " + m.getNome() + status);
        }
    }

    private void turnoDosMonstros() {
        GameLog.print("\n--- TURNO DOS INIMIGOS ---");
        List<Jogador> vivos = grupoJogadores.getMembrosVivos();

        for (Monstro m : monstrosAtuais) {
            if (m.isEstaVivo() && !vivos.isEmpty()) {

                int indexAlvo = Dados.rolar(vivos.size()) - 1;
                Jogador alvo = vivos.get(indexAlvo);
                m.atacar(alvo);
            }
        }

        if (grupoJogadores.todoMundoMorreu()) {
            GameLog.print("\nSEU GRUPO FOI DIZIMADO.");
            this.estadoJogo = EstadoJogo.FIM_DE_JOGO;
        } else {

            anunciarTurnoJogador();
        }
    }

    private void verificarMortesMonstros() {

        boolean todosMortos = true;
        for(Monstro m : monstrosAtuais) {
            if(m.isEstaVivo()) todosMortos = false;
        }

        if (todosMortos) {
            GameLog.print("\n*** VITÓRIA! ***");
            GameLog.print("Todos os inimigos foram derrotados!");

            for (Monstro m : monstrosAtuais) {
                processarLoot(m);
            }

            this.estadoJogo = EstadoJogo.EXPLORANDO;
            this.emboscadaVencida = true;
            monstrosAtuais.clear();

            GameLog.print("\nO caminho está seguro.");
            GameLog.print("Digite 'continuar' para encerrar a demo.");
        }
    }

    private void processarLoot(Monstro m) {
        List<Item> drops = m.getLoot();
        if (!drops.isEmpty()) {
            GameLog.print("Loot de " + m.getNome() + ":");
            for (Item item : drops) {
                getLider().getInventario().adicionarItem(item);
            }
        }
    }

    private void exibirStatus() {
        GameLog.print("--- GRUPO ---");
        for (Jogador j : grupoJogadores.getMembros()) {
            String status = j.isEstaVivo() ? "HP " + j.getPontosDeVida() : "INCONSCIENTE";
            GameLog.print(j.getNome() + ": " + status);
        }
        GameLog.print("-------------");
    }

    public EstadoJogo getEstadoJogo() {
        return estadoJogo;
    }
}