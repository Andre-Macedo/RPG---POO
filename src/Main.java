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

    //------------- Console --------------------

//    public static void main(String[] args) {
//
//
//        GameView view = new ConsoleView();
//
//        Jogador jogador = criarPersonagem(view);
//
//        GameEngine engine = new GameEngine(jogador);
//
//        GameController controller = new GameController(engine, view);
//
//        // REQUISITO: QUALIDADE DA TRAMA
//        view.exibirMensagem("\n#################################################");
//        view.exibirMensagem("#       AS MINAS PERDIDAS DE PHANDELVER         #");
//        view.exibirMensagem("#################################################");
//        view.exibirMensagem("\nVocê está na Trilha de Triboar, a meio dia de viagem de Phandalin.");
//        view.exibirMensagem("Você escolta uma carroça de suprimentos mineração.");
//        view.exibirMensagem("A estrada faz uma curva fechada e você vê algo à frente...");
//        view.exibirMensagem("\nDois cavalos mortos bloqueiam o caminho. O mato ao redor é denso.");
//        view.exibirMensagem("O silêncio é perturbador.");
//
//        view.exibirMensagem("\n--- O QUE VOCÊ DESEJA FAZER? ---");
//        view.exibirMensagem("  > investigar cavalos   (Teste de INT/Investigação)");
//        view.exibirMensagem("  > investigar arbustos  (Teste de WIS/Percepção)");
//        view.exibirMensagem("  > status               (Ver sua ficha)");
//        view.exibirMensagem("  > sair");
//        view.exibirMensagem("--------------------------------");
//
//
//        controller.iniciarJogo();
//    }
//
//    private static Jogador criarPersonagem(GameView view) {
//        view.exibirMensagem("--- CRIAÇÃO DE PERSONAGEM ---");
//        view.exibirMensagem("Qual é o nome do seu herói?");
//        String nome = view.obterEntradaUsuario();
//
//        view.exibirMensagem("\nEscolha sua classe:");
//        view.exibirMensagem("1 - Guerreiro (Forte e Resistente)");
//        view.exibirMensagem("2 - Ladino    (Ágil e Esperto)");
//        view.exibirMensagem("3 - Mago      (Inteligente e Frágil)");
//
//        String escolha = view.obterEntradaUsuario();
//        Atributos atributos;
//
//        switch (escolha) {
//            case "2": // Ladino
//                view.exibirMensagem("Classe selecionada: LADINO");
//                // STR 10, DEX 15, CON 12, INT 14, WIS 13, CHA 8
//                atributos = new Atributos(10, 15, 12, 14, 13, 8);
//                break;
//            case "3": // Mago
//                view.exibirMensagem("Classe selecionada: MAGO");
//                // STR 8, DEX 13, CON 12, INT 15, WIS 14, CHA 10
//                atributos = new Atributos(8, 13, 12, 15, 14, 10);
//                break;
//            case "1":
//            default: // Guerreiro (Padrão)
//                view.exibirMensagem("Classe selecionada: GUERREIRO");
//                // STR 15, DEX 12, CON 14, INT 10, WIS 13, CHA 8
//                atributos = new Atributos(15, 12, 14, 10, 13, 8);
//                break;
//        }
//
//        Jogador novoJogador = new Jogador(nome);
//        novoJogador.setAtributos(atributos);
//
//
//        return novoJogador;
//    }



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
                GameLog.print("\n=========================================");
                GameLog.print("   AS MINAS PERDIDAS DE PHANDELVER (RPG) ");
                GameLog.print("=========================================");
                GameLog.print("   1 - Iniciar Nova Aventura");
                GameLog.print("   2 - Sobre / Créditos");
                GameLog.print("   3 - Sair");
                GameLog.print("=========================================");
                GameLog.print("Escolha uma opção:");

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
                        GameLog.print("Opção inválida. Tente novamente.");
                }
            }
        }).start();
    }

    private static void exibirCreditos(GameView view) {
        GameLog.print("\n--- CRÉDITOS ---");
        GameLog.print("Desenvolvido para a disciplina de POO por André Macedo e Pedro Alves");
        GameLog.print("SENAC - Engenharia de Computação");
        GameLog.print("Baseado nas aventura introdutórias de D&D 5e.");
        GameLog.print("\nPressione [Enviar] para voltar.");
        view.obterEntradaUsuario();
    }

    private static void iniciarNovaAventura(GameView view) {

        Grupo grupo = criarGrupo(view);

        GameEngine engine = new GameEngine(grupo);
        GameController controller = new GameController(engine, view);

        view.exibirMensagem("\n#################################################");
        view.exibirMensagem("#       CAPÍTULO 1: AS FLECHAS GOBLIN           #");
        view.exibirMensagem("#################################################");

        view.exibirNarrativa("Há alguns dias, na cidade de Neverwinter, vocês encontraram Gundren Rockseeker.");
        view.exibirNarrativa("O anão estava eufórico. Ele dizia ter encontrado algo grande...");
        view.exibirNarrativa("'Algo que mudaria a fortuna de todos', ele disse, batendo na mesa da taverna.");

        view.exibirNarrativa("\nGundren contratou vocês para escoltar uma carroça de suprimentos até Phandalin.");
        view.exibirNarrativa("Ele partiu à frente, a cavalo, acompanhado do guerreiro Sildar Hallwinter.");
        view.exibirNarrativa("'Vejo vocês na cidade!', gritou ele ao partir.");

        view.exibirNarrativa("\nA viagem pela Estrada Alta foi tranquila... até agora.");
        view.exibirNarrativa("Vocês acabam de entrar na Trilha de Triboar.");
        view.exibirNarrativa("A floresta aqui é densa e o silêncio é perturbador.");

        view.exibirNarrativa("\nAo fazer uma curva fechada, a carroça para bruscamente.");
        view.exibirNarrativa("Dois cavalos mortos bloqueiam o caminho. Estão crivados de flechas pretas.");
        view.exibirNarrativa("Parecem ser os cavalos de Gundren e Sildar...");

        GameLog.print("\n--- COMANDOS DISPONÍVEIS ---");
        GameLog.print("\n--- O QUE VOCÊ DESEJA FAZER? ---");
        GameLog.print("  > investigar cavalos   (Teste de INT/Investigação)");
        GameLog.print("  > investigar arbustos  (Teste de WIS/Percepção)");
        GameLog.print("  > status               (Ver sua ficha)");
        GameLog.print("  > inventario           (Ver itens do líder)");
        GameLog.print("----------------------------");

        // Inicia o Loop do Jogo
        controller.iniciarJogo();

        // Quando controller.iniciarJogo() termina (Game Over ou Vitória da Demo),
        // o código volta para cá e depois retorna ao laço do Menu Principal.
        GameLog.print("\nPressione [Enviar] para voltar ao Menu Principal.");
        view.obterEntradaUsuario();
    }


    private static Jogador criarPersonagem(GameView view) {
        GameLog.print("Digite o nome do seu herói:");

        String nome = view.obterEntradaUsuario();

        GameLog.print("\nEscolha sua classe (digite o número):");
        GameLog.print("1 - Guerreiro");
        GameLog.print("2 - Ladino");
        GameLog.print("3 - Mago");

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

        GameLog.print("--- CRIAÇÃO DO LÍDER ---");
        Jogador lider = criarPersonagem(view);
        grupo.adicionarMembro(lider);

        GameLog.print("\nDeseja recrutar companheiros? (S/N)");
        String resp = view.obterEntradaUsuario();

        if (resp.equalsIgnoreCase("s")) {
            GameLog.print("Escolha seus aliados (digite os números separados, ex: 1 3)");
            GameLog.print("1 - Sildar (Guerreiro Humano)");
            GameLog.print("2 - Ellywick (Mago Gnomo)");
            GameLog.print("3 - Reidoth (Druida/Clérigo)");

            String selecao = view.obterEntradaUsuario();

            if (selecao.contains("1")) {
                Jogador sildar = new Jogador("Sildar Hallwinter");
                sildar.setAtributos(new Atributos(16, 10, 14, 10, 10, 12));
                grupo.adicionarMembro(sildar);
                GameLog.print("Sildar se juntou ao grupo.");
            }
            if (selecao.contains("2")) {
                Jogador mago = new Jogador("Ellywick");
                mago.setAtributos(new Atributos(8, 14, 12, 16, 12, 10));
                grupo.adicionarMembro(mago);
                GameLog.print("Ellywick se juntou ao grupo.");
            }
            // ... etc
        }

        return grupo;
    }
}