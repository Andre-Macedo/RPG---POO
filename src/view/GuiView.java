package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiView extends JFrame implements GameView {

    private JTextArea areaTexto;
    private JTextField campoEntrada;
    private JButton botaoEnviar;

    private final Object lock = new Object();
    private String comandoDigitado = null;

    public GuiView() {
        super("RPG: As Minas Perdidas de Phandelver");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);

        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 16));
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);

        areaTexto.setBackground(new Color(40, 42, 54));
        areaTexto.setForeground(new Color(248, 248, 242));

        areaTexto.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setBorder(new EmptyBorder(10, 10, 10, 10));
        painelInferior.setBackground(new Color(68, 71, 90)); // Um cinza um pouco mais claro

        campoEntrada = new JTextField();
        campoEntrada.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoEntrada.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(98, 114, 164), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        botaoEnviar = new JButton("Enviar");
        botaoEnviar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoEnviar.setBackground(new Color(189, 147, 249)); // Roxo Drácula
        botaoEnviar.setForeground(Color.WHITE);
        botaoEnviar.setFocusPainted(false);
        botaoEnviar.setBorder(new EmptyBorder(10, 20, 10, 20));

        painelInferior.add(campoEntrada, BorderLayout.CENTER);

        JPanel espacador = new JPanel();
        espacador.setPreferredSize(new Dimension(10, 0));
        espacador.setOpaque(false);
        painelInferior.add(espacador, BorderLayout.EAST); // Truque de layout
        painelInferior.add(botaoEnviar, BorderLayout.LINE_END);

        add(painelInferior, BorderLayout.SOUTH);

        ActionListener acaoEnvio = e -> enviarComando();
        botaoEnviar.addActionListener(acaoEnvio);
        campoEntrada.addActionListener(acaoEnvio);

        setLocationRelativeTo(null);
        setVisible(true);
        campoEntrada.requestFocus();
    }

    private void enviarComando() {
        String texto = campoEntrada.getText();
        if (!texto.trim().isEmpty()) {

            exibirMensagem("\n> " + texto.toUpperCase());

            synchronized (lock) {
                comandoDigitado = texto;
                lock.notify();
            }
            campoEntrada.setText("");
        }
    }

    @Override
    public void exibirMensagem(String mensagem) {
        SwingUtilities.invokeLater(() -> {
            areaTexto.append(mensagem + "\n");
            areaTexto.setCaretPosition(areaTexto.getDocument().getLength());
        });
    }

    @Override
    public void exibirErro(String mensagem) {
        SwingUtilities.invokeLater(() -> {
            areaTexto.append("\n[!] " + mensagem + "\n");
        });
    }

    @Override
    public String obterEntradaUsuario() {
        synchronized (lock) {
            try {
                while (comandoDigitado == null) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String comando = comandoDigitado;
            comandoDigitado = null;
            return comando;
        }
    }

    @Override
    public void exibirNarrativa(String mensagem) {
        SwingUtilities.invokeLater(() -> areaTexto.append("\n"));

        for (char c : mensagem.toCharArray()) {
            SwingUtilities.invokeLater(() -> {
                areaTexto.append(String.valueOf(c));
                areaTexto.setCaretPosition(areaTexto.getDocument().getLength());
            });

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        SwingUtilities.invokeLater(() -> areaTexto.append("\n"));

        try { Thread.sleep(300); } catch (InterruptedException e) {}
    }
}