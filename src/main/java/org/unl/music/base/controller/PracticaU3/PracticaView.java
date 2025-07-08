package org.unl.music.base.controller.PracticaU3;
import javax.swing.*;
import java.awt.*;

public class PracticaView extends JFrame {
    private LaberintoPanel laberintoPanel;
    private int dimension = 30;  // Valor por defecto

    public PracticaView() {
        setTitle("Laberinto con Dijkstra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());

        // Crear Panel de Control (botones y entrada)
        JPanel controlPanel = new JPanel();
        JLabel label = new JLabel("Dimensión (30-100): ");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(30, 30, 100, 1));
        JButton generarBtn = new JButton("Generar");
        JButton resolverBtn = new JButton("Resolver");

        controlPanel.add(label);
        controlPanel.add(spinner);
        controlPanel.add(generarBtn);
        controlPanel.add(resolverBtn);
        add(controlPanel, BorderLayout.NORTH);

        // Crear Panel del Laberinto
        laberintoPanel = new LaberintoPanel();
        add(laberintoPanel, BorderLayout.CENTER);

        // Eventos
        generarBtn.addActionListener(e -> {
            dimension = (Integer) spinner.getValue();
            laberintoPanel.generarLaberinto(dimension);
        });

        resolverBtn.addActionListener(e -> {
            laberintoPanel.resolverLaberinto();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PracticaView gui = new PracticaView();
            gui.setVisible(true);
        });
    }
}
