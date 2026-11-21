package vista;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaInicio ventana = new VentanaInicio();
                ventana.setVisible(true);
            }
        });
    }
}