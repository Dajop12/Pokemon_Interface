package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.Juego;

public class VentanaInicio extends JFrame {

    private JTextField nombreJugador1;
    private JTextField nombreJugador2;
    private JComboBox<String> seleccionarMazo1;
    private JComboBox<String> seleccionarMazo2;
    private JButton botonIniciar;

    public VentanaInicio() {
        setTitle("Configuracion de Partida");
        setSize(520, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel textoTitulo = new JLabel("Simulador Pokemon TCG Pocket", SwingConstants.CENTER);
        textoTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        textoTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(textoTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 10, 12));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JLabel textoJ1 = new JLabel("Nombre Entrenador 1:");
        nombreJugador1 = new JTextField();

        JLabel textoMazo1 = new JLabel("Mazo Entrenador 1:");
        String[] opcionesMazos = {
            "1 - Mazo Electrico y Agua",
            "2 - Mazo Fuego y Agua",
            "3 - Mazo Evoluciones Mixto",
            "4 - Mazo Estrategico"
        };
        seleccionarMazo1 = new JComboBox<String>(opcionesMazos);

        JLabel textoJ2 = new JLabel("Nombre Entrenador 2:");
        nombreJugador2 = new JTextField();

        JLabel textoMazo2 = new JLabel("Mazo Entrenador 2:");
        seleccionarMazo2 = new JComboBox<String>(opcionesMazos);
        seleccionarMazo2.setSelectedIndex(1);

        panelFormulario.add(textoJ1);
        panelFormulario.add(nombreJugador1);
        panelFormulario.add(textoMazo1);
        panelFormulario.add(seleccionarMazo1);
        panelFormulario.add(textoJ2);
        panelFormulario.add(nombreJugador2);
        panelFormulario.add(textoMazo2);
        panelFormulario.add(seleccionarMazo2);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 10));
        botonIniciar = new JButton("Iniciar Partida");
        botonIniciar.setFont(new Font("Arial", Font.BOLD, 14));
        panelBoton.add(botonIniciar);

        add(panelBoton, BorderLayout.SOUTH);

        botonIniciar.addActionListener(new ActionListener() {
      
            public void actionPerformed(ActionEvent e) {
                iniciarPartida();
            }
        });
    }

    private void iniciarPartida() {
        String nombre1 = nombreJugador1.getText().trim();
        String nombre2 = nombreJugador2.getText().trim();

        if (nombre1.isEmpty() || nombre2.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Debes ingresar el nombre de ambos entrenadores para comenzar",
                "Datos incompletos",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (nombre1.equalsIgnoreCase(nombre2)) {
            JOptionPane.showMessageDialog(
                this,
                "Los dos entrenadores deben tener nombres diferentes",
                "Nombres repetidos",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int mazo1 = seleccionarMazo1.getSelectedIndex() + 1;
        int mazo2 = seleccionarMazo2.getSelectedIndex() + 1;

        Juego juego = new Juego(nombre1, mazo1, nombre2, mazo2);
        VentanaJuego ventanaJuego = new VentanaJuego(juego);
        ventanaJuego.setVisible(true);
        dispose();
    }
}