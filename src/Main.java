import controller.GameController;
import model.Atributos;
import model.GameEngine;
import model.Grupo;
import model.Jogador;
import utils.GameLog;
import view.ConsoleView;
import view.GameView;
import view.GuiView;

public class Main {

    /**

     Intgrantes do Grupo: André Macedo, Gustavo Fernandes e Pedro Alves.

    */




    // --------------- Interface Gráfica --------------

    public static void main(String[] args) {
        // 1. Inicia a Interface Gráfica
        GameView view = new GuiView();
        // Conecta o Log à View para que os prints do sistema apareçam na janela
        GameLog.setView(view);

        // 2. Thread principal da aplicação
        new Thread(() -> {
            boolean rodando = true;

            while (rodando) {
                // --- MENU PRINCIPAL ---
                GameLog.printRapido("\n=========================================");
                GameLog.printRapido("   AS MINAS PERDIDAS DE PHANDELVER (RPG) ");
                GameLog.printRapido("=========================================");
                GameLog.printRapido("   1 - Iniciar Nova Aventura");
                GameLog.printRapido("   2 - Sobre / Créditos");
                GameLog.printRapido("   3 - Sair");
                GameLog.printRapido("=========================================");
                GameLog.printRapido("Escolha uma opção:");

                String opcao = view.obterEntradaUsuario();

                switch (opcao) {
                    case "1":
                        iniciarNovaAventura(view);
                        break;
                    case "2":
                        exibirCreditos(view);
                        break;
                    case "3":
                        GameLog.print("Saindo do jogo... Até logo!");
                        try { Thread.sleep(2000); } catch (InterruptedException e) {}
                        System.exit(0);
                        break;
                    default:
                        GameLog.printRapido("Opção inválida. Tente novamente.");
                }
            }
        }).start();
    }

    private static void exibirCreditos(GameView view) {
        GameLog.printRapido("\n--- CRÉDITOS ---");
        GameLog.printRapido("Desenvolvido para a disciplina de POO por André Macedo, Gustavo Fernandes e Pedro Alves");
        GameLog.printRapido("SENAC - Engenharia de Computação");
        GameLog.printRapido("Baseado nas aventuras introdutórias de D&D 5e.");
        view.exibirMensagem("\nDigite [voltar] para retornar ao menu.");

        view.obterEntradaUsuario();

    }

    private static void iniciarNovaAventura(GameView view) {

        Grupo grupo = criarGrupo(view);

        GameEngine engine = new GameEngine(grupo);
        GameController controller = new GameController(engine, view);

        GameLog.printRapido("\n#################################################");
        GameLog.printRapido("#       CAPÍTULO 1: AS FLECHAS GOBLIN           #");
        GameLog.printRapido("#################################################");

        GameLog.narrar("Há alguns dias, na cidade de Neverwinter, vocês encontraram Gundren Rockseeker.");
        GameLog.narrar("O anão estava eufórico. Ele dizia ter encontrado algo grande...");
        GameLog.narrar("'Algo que mudaria a fortuna de todos', ele disse, batendo na mesa da taverna.");

        GameLog.narrar("\nGundren contratou vocês para escoltar uma carroça de suprimentos até Phandalin.");
        GameLog.narrar("Ele partiu à frente, a cavalo, acompanhado do guerreiro Sildar Hallwinter.");
        GameLog.narrar("'Vejo vocês na cidade!', gritou ele ao partir.");

        GameLog.narrar("\nA viagem pela Estrada Alta foi tranquila... até agora.");
        GameLog.narrar("Vocês acabam de entrar na Trilha de Triboar.");
        GameLog.narrar("A floresta aqui é densa e o silêncio é perturbador.");

        GameLog.narrar("\nAo fazer uma curva fechada, a carroça para bruscamente.");
        GameLog.narrar("Dois cavalos mortos bloqueiam o caminho. Estão crivados de flechas pretas.");
        GameLog.narrar("Parecem ser os cavalos de Gundren e Sildar...");

        GameLog.printRapido("\n--- COMANDOS DISPONÍVEIS ---");
        GameLog.printRapido("\n--- O QUE VOCÊ DESEJA FAZER? ---");
        GameLog.printRapido("  > investigar cavalos   (Teste de INT/Investigação)");
        GameLog.printRapido("  > investigar arbustos  (Teste de WIS/Percepção)");
        GameLog.printRapido("  > status               (Ver sua ficha)");
        GameLog.printRapido("  > inventario           (Ver itens do líder)");
        GameLog.printRapido("----------------------------");

        controller.iniciarJogo();

        GameLog.print("\nPressione [Enviar] para voltar ao Menu Principal.");
        view.obterEntradaUsuario();
    }


    private static Jogador criarPersonagem(GameView view) {
        GameLog.printRapido("Digite o nome do seu herói:");

        String nome = view.obterEntradaUsuario();

        GameLog.printRapido("\nEscolha sua classe (digite o número):");
        GameLog.printRapido("1 - Guerreiro");
        GameLog.printRapido("2 - Ladino");
        GameLog.printRapido("3 - Mago");

        String escolha = view.obterEntradaUsuario();
        Atributos atributos;

        switch (escolha) {
            case "2":
                GameLog.print("Classe selecionada: LADINO");
                atributos = new Atributos(10, 15, 12, 14, 13, 8);
                break;
            case "3":
                GameLog.print("Classe selecionada: MAGO");
                atributos = new Atributos(8, 13, 12, 15, 14, 10);
                break;
            case "1":
            default:
                GameLog.print("Classe selecionada: GUERREIRO");
                atributos = new Atributos(15, 12, 14, 10, 13, 8);
                break;
        }

        Jogador novoJogador = new Jogador(nome);
        novoJogador.setAtributos(atributos);
        return novoJogador;
    }

    private static Grupo criarGrupo(GameView view) {
        Grupo grupo = new Grupo();

        GameLog.printRapido("--- CRIAÇÃO DO LÍDER ---");
        Jogador lider = criarPersonagem(view);
        grupo.adicionarMembro(lider);

        GameLog.printRapido("\nDeseja recrutar companheiros? (S/N)");
        String resp = view.obterEntradaUsuario();

        if (resp.equalsIgnoreCase("s")) {
            GameLog.printRapido("Escolha seus aliados (digite os números separados, ex: 1 3)");
            GameLog.printRapido("1 - Aragorn (Guerreiro Humano)");
            GameLog.printRapido("2 - Ellywick (Mago Gnomo)");
            GameLog.printRapido("3 - Reidoth (Druida/Clérigo)");

            String selecao = view.obterEntradaUsuario();

            if (selecao.contains("1")) {
                Jogador sildar = new Jogador("Sildar Hallwinter");
                sildar.setAtributos(new Atributos(16, 10, 14, 10, 10, 12));
                grupo.adicionarMembro(sildar);
                GameLog.printRapido("Sildar se juntou ao grupo.");
            }
            if (selecao.contains("2")) {
                Jogador mago = new Jogador("Ellywick");
                mago.setAtributos(new Atributos(8, 14, 12, 16, 12, 10));
                grupo.adicionarMembro(mago);
                GameLog.printRapido("Ellywick se juntou ao grupo.");
            }

            if (selecao.contains("3")) {
                Jogador reidoth = new Jogador("Reidoth");
                reidoth.setAtributos(new Atributos(12, 10, 14, 12, 16, 10));
                grupo.adicionarMembro(reidoth);
                GameLog.printRapido("Reidoth se juntou ao grupo.");
            }

        }

        return grupo;
    }
}