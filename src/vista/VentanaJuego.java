package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import modelo.Carta;
import modelo.Entrenador;
import modelo.EvolucionPokemon;
import modelo.Juego;
import modelo.Jugador;
import modelo.Pokemon;

public class VentanaJuego extends JFrame {

    private Juego juego;
    private int fasePreparacion;
    private int indiceCartaSeleccionada;
    private boolean manoOculta;

    private JLabel textoTurnoActual;
    private JLabel textoPuntosJ1;
    private JLabel textoPuntosJ2;
    private JLabel textoMazoJ1;
    private JLabel textoMazoJ2;
    private JLabel textoEstadoEnergia;

    private JLabel textoRivalActivoInfo;
    private JProgressBar barraRivalActivo;
    private JLabel textoRivalBanca1Info;
    private JProgressBar barraRivalBanca1;
    private JLabel textoRivalBanca2Info;
    private JProgressBar barraRivalBanca2;

    private JLabel textoJugadorActualActivoInfo;
    private JProgressBar barraJugadorActualActivo;
    private JLabel textoJugadorActualBanca1Info;
    private JProgressBar barraJugadorActualBanca1;
    private JLabel textoJugadorActualBanca2Info;
    private JProgressBar barraJugadorActualBanca2;

    private JButton botonElegirInicial;
    private JButton botonBajarBanca;
    private JButton botonAdjuntarEnergia;
    private JButton botonEvolucionar;
    private JButton botonUsarEntrenador;
    private JButton botonRetirar;
    private JButton botonAtacar;
    private JButton botonTerminarTurno;
    private JButton botonReemplazarDebilitado;

    private JTextArea areaRegistro;
    private JPanel panelCartasMano;
    private JLabel textoCartaSeleccionada;

    public VentanaJuego(Juego juego) {
        this.juego = juego;
        this.fasePreparacion = 1;
        this.indiceCartaSeleccionada = -1;
        this.manoOculta = false;

        setTitle("Arena de Batalla!");
        setSize(1120, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        panelSuperior();
        panelCentral();
        inicializarPanelDerecho();
        panelInferior();

        agregarRegistro("Preparando la partida");
        agregarRegistro(juego.getJugador1().getNombre() + " debes seleccionar un pokemon de la mano para jugar");
        actualizarVista();
    }

    private void panelSuperior() {
        JPanel panelSuperior = new JPanel(new GridLayout(2, 3, 10, 4));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Estado de la Partida"));

        textoTurnoActual = new JLabel("Fase de Preparacion");
        textoTurnoActual.setFont(new Font("Arial", Font.BOLD, 13));

        textoPuntosJ1 = new JLabel("Puntos J1 | 0/3");
        textoPuntosJ2 = new JLabel("Puntos J2 | 0/3");
        textoMazoJ1 = new JLabel("Mazo J1 | 0 cartas");
        textoMazoJ2 = new JLabel("Mazo J2 | 0 cartas");
        textoEstadoEnergia = new JLabel("Energia del turno | No disponible");

        panelSuperior.add(textoTurnoActual);
        panelSuperior.add(textoPuntosJ1);
        panelSuperior.add(textoPuntosJ2);
        panelSuperior.add(textoEstadoEnergia);
        panelSuperior.add(textoMazoJ1);
        panelSuperior.add(textoMazoJ2);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private void panelCentral() {
        JPanel panelCampo = new JPanel(new GridLayout(2, 1, 8, 8));

        JPanel panelRival = new JPanel(new GridLayout(1, 3, 8, 8));
        panelRival.setBorder(BorderFactory.createTitledBorder("Campo del Rival"));

        JPanel infoRivalActivo = crearTarjetaPokemon("Pokemon Activo Rival");
        textoRivalActivoInfo = (JLabel) infoRivalActivo.getComponent(1);
        barraRivalActivo = (JProgressBar) infoRivalActivo.getComponent(2);

        JPanel infoRivalBanca1 = crearTarjetaPokemon("Banca Rival 1");
        textoRivalBanca1Info = (JLabel) infoRivalBanca1.getComponent(1);
        barraRivalBanca1 = (JProgressBar) infoRivalBanca1.getComponent(2);

        JPanel infoRivalBanca2 = crearTarjetaPokemon("Banca Rival 2");
        textoRivalBanca2Info = (JLabel) infoRivalBanca2.getComponent(1);
        barraRivalBanca2 = (JProgressBar) infoRivalBanca2.getComponent(2);

        panelRival.add(infoRivalActivo);
        panelRival.add(infoRivalBanca1);
        panelRival.add(infoRivalBanca2);

        JPanel panelJugadorActual = new JPanel(new GridLayout(1, 3, 8, 8));
        panelJugadorActual.setBorder(BorderFactory.createTitledBorder("Tu Campo"));

        JPanel infoJugadorActualActivo = crearTarjetaPokemon("Tu Pokemon Activo");
        textoJugadorActualActivoInfo = (JLabel) infoJugadorActualActivo.getComponent(1);
        barraJugadorActualActivo = (JProgressBar) infoJugadorActualActivo.getComponent(2);

        JPanel infoJugadorActualBanca1 = crearTarjetaPokemon("Tu Banca 1");
        textoJugadorActualBanca1Info = (JLabel) infoJugadorActualBanca1.getComponent(1);
        barraJugadorActualBanca1 = (JProgressBar) infoJugadorActualBanca1.getComponent(2);

        JPanel infoJugadorActualBanca2 = crearTarjetaPokemon("Tu Banca 2");
        textoJugadorActualBanca2Info = (JLabel) infoJugadorActualBanca2.getComponent(1);
        barraJugadorActualBanca2 = (JProgressBar) infoJugadorActualBanca2.getComponent(2);

        panelJugadorActual.add(infoJugadorActualActivo);
        panelJugadorActual.add(infoJugadorActualBanca1);
        panelJugadorActual.add(infoJugadorActualBanca2);


        panelCampo.add(panelRival);
        panelCampo.add(panelJugadorActual);

        add(panelCampo, BorderLayout.CENTER);
    }

    private JPanel crearTarjetaPokemon(String titulo) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel textoTitulo = new JLabel(titulo);
        textoTitulo.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel textoInfo = new JLabel("<html>Vacio</html>");
        textoInfo.setFont(new Font("Arial", Font.PLAIN, 12));

        JProgressBar barraPs = new JProgressBar(0, 100);
        barraPs.setValue(0);
        barraPs.setStringPainted(true);
        barraPs.setString("0/0 PS");

        tarjeta.add(textoTitulo);
        tarjeta.add(textoInfo);
        tarjeta.add(barraPs);

        return tarjeta;
    }

    private void inicializarPanelDerecho() {
        JPanel panelDerecho = new JPanel(new BorderLayout(6, 6));
        panelDerecho.setPreferredSize(new Dimension(340, 0));

        JPanel panelBotones = new JPanel(new GridLayout(9, 1, 4, 4));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));

        botonElegirInicial = new JButton("Elegir Pokemon Activo Inicial");
        botonBajarBanca = new JButton("Bajar Pokemon a Banca");
        botonAdjuntarEnergia = new JButton("Adjuntar Energia");
        botonEvolucionar = new JButton("Evolucionar Pokemon");
        botonUsarEntrenador = new JButton("Usar Carta Entrenador");
        botonRetirar = new JButton("Retirar Pokemon Activo");
        botonAtacar = new JButton("Atacar");
        botonReemplazarDebilitado = new JButton("Elegir Reemplazo de Banca");
        botonTerminarTurno = new JButton("Terminar Turno");

        panelBotones.add(botonElegirInicial);
        panelBotones.add(botonBajarBanca);
        panelBotones.add(botonAdjuntarEnergia);
        panelBotones.add(botonEvolucionar);
        panelBotones.add(botonUsarEntrenador);
        panelBotones.add(botonRetirar);
        panelBotones.add(botonAtacar);
        panelBotones.add(botonReemplazarDebilitado);
        panelBotones.add(botonTerminarTurno);

        panelDerecho.add(panelBotones, BorderLayout.NORTH);

        areaRegistro = new JTextArea();
        areaRegistro.setEditable(false);
        areaRegistro.setLineWrap(true);
        areaRegistro.setWrapStyleWord(true);
        JScrollPane deslizadorRegistro = new JScrollPane(areaRegistro);
        deslizadorRegistro.setBorder(BorderFactory.createTitledBorder("Registro de Eventos"));

        panelDerecho.add(deslizadorRegistro, BorderLayout.CENTER);

        add(panelDerecho, BorderLayout.EAST);

        listenerAcciones();
    }

    private void panelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBorder(BorderFactory.createTitledBorder("Tu Mano (Click para seleccionar)"));
        panelInferior.setPreferredSize(new Dimension(0, 155));

        textoCartaSeleccionada = new JLabel("Carta seleccionada: Ninguna", SwingConstants.LEFT);
        textoCartaSeleccionada.setFont(new Font("Arial", Font.BOLD, 12));
        panelInferior.add(textoCartaSeleccionada, BorderLayout.NORTH);

        panelCartasMano = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        JScrollPane deslizadorMano = new JScrollPane(panelCartasMano);
        panelInferior.add(deslizadorMano, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void listenerAcciones() {
        botonElegirInicial.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionElegirInicial();
            }
        });

        botonBajarBanca.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionBajarBanca();
            }
        });

        botonAdjuntarEnergia.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionAdjuntarEnergia();
            }
        });

        botonEvolucionar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionEvolucionar();
            }
        });

        botonUsarEntrenador.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionUsarEntrenador();
            }
        });

        botonRetirar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionRetirar();
            }
        });

        botonAtacar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionAtacar();
            }
        });

        botonReemplazarDebilitado.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionReemplazarDebilitado();
            }
        });

        botonTerminarTurno.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                accionTerminarTurno();
            }
        });
    }

    private Jugador obtenerJugadorVisible() {
        if (fasePreparacion == 1) {
            return juego.getJugador1();
        }
        if (fasePreparacion == 2) {
            return juego.getJugador2();
        }
        if (juego.isEsperandoReemplazo()) {
            return juego.getJugadorRival();
        }
        return juego.getJugadorActivo();
    }

    private Jugador obtenerRivalVisible() {
        if (fasePreparacion == 1) {
            return juego.getJugador2();
        }
        if (fasePreparacion == 2) {
            return juego.getJugador1();
        }
        if (juego.isEsperandoReemplazo()) {
            return juego.getJugadorActivo();
        }
        return juego.getJugadorRival();
    }

    private void mostrarTransicionTurno(String nombreSiguienteJugador) {
        manoOculta = true;
        actualizarVista();

        JOptionPane.showMessageDialog(
            this,
            "Turno de " + nombreSiguienteJugador + " presiona Continuar",
            "Cambio de Turno",
            JOptionPane.INFORMATION_MESSAGE
        );

        manoOculta = false;
        indiceCartaSeleccionada = -1;
        actualizarVista();
    }

    private void accionElegirInicial() {
        if (indiceCartaSeleccionada == -1) {
            mostrarAviso("Primero haz click en un Pokemon basico de tu mano");
            return;
        }

        Jugador jugadorActual = obtenerJugadorVisible();
        String resultado = juego.elegirPokemonInicial(jugadorActual, indiceCartaSeleccionada);
        agregarRegistro(resultado);

        if (jugadorActual.getPokemonActivo() != null) {
            indiceCartaSeleccionada = -1;
            if (fasePreparacion == 1) {
                fasePreparacion = 2;
                agregarRegistro(juego.getJugador2().getNombre() + " debe seleccionar su Pokemon activo inicial");
                mostrarTransicionTurno(juego.getJugador2().getNombre());
            } else if (fasePreparacion == 2) {
                fasePreparacion = 0;
                String inicio = juego.iniciarPrimerTurno();
                agregarRegistro(inicio);
                mostrarTransicionTurno(juego.getJugadorActivo().getNombre());
            }
        } else {
            mostrarAviso(resultado);
        }
    }

    private void accionBajarBanca() {
        if (indiceCartaSeleccionada == -1) {
            mostrarAviso("Selecciona primero un Pokemon basico de tu mano");
            return;
        }
        String resultado = juego.bajarPokemonABanca(indiceCartaSeleccionada);
        agregarRegistro(resultado);
        indiceCartaSeleccionada = -1;
        actualizarVista();
    }

    private void accionAdjuntarEnergia() {
        if (juego.isEnergiaUsada()) {
            mostrarAviso("Ya adjuntaste tu energia disponible en este turno");
            return;
        }

        Jugador jugador = juego.getJugadorActivo();
        int totalOpciones = 1 + jugador.getBanca().size();
        String[] opciones = new String[totalOpciones];
        opciones[0] = "Activo: " + jugador.getPokemonActivo().getNombre();

        for (int i = 0; i < jugador.getBanca().size(); i++) {
            opciones[i + 1] = "Banca " + (i + 1) + ": " + jugador.getBanca().get(i).getNombre();
        }

        int seleccion = JOptionPane.showOptionDialog(
            this,
            "Elige a que Pokemon deseas adjuntar la energia",
            "Adjuntar Energia",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (seleccion >= 0) {
            String resultado = juego.adjuntarEnergia(seleccion);
            agregarRegistro(resultado);
            actualizarVista();
        }
    }

    private void accionEvolucionar() {
        if (juego.isEsPrimerTurnoActual()) {
            mostrarAviso("No puedes evolucionar en tu primer turno");
            return;
        }
        if (indiceCartaSeleccionada == -1) {
            mostrarAviso("Selecciona primero una carta de evolucion de tu mano");
            return;
        }

        Jugador jugador = juego.getJugadorActivo();
        int totalOpciones = 1 + jugador.getBanca().size();
        String[] opciones = new String[totalOpciones];
        opciones[0] = "Activo: " + jugador.getPokemonActivo().getNombre();

        for (int i = 0; i < jugador.getBanca().size(); i++) {
            opciones[i + 1] = "Banca " + (i + 1) + ": " + jugador.getBanca().get(i).getNombre();
        }

        int destino = JOptionPane.showOptionDialog(
            this,
            "Selecciona el Pokemon en campo que deseas evolucionar",
            "Evolucionar Pokemon",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (destino >= 0) {
            String resultado = juego.evolucionarPokemon(indiceCartaSeleccionada, destino);
            agregarRegistro(resultado);
            indiceCartaSeleccionada = -1;
            actualizarVista();
        }
    }

    private void accionUsarEntrenador() {
        if (indiceCartaSeleccionada == -1) {
            mostrarAviso("Selecciona primero una carta de Entrenador de tu mano");
            return;
        }
        String resultado = juego.jugarEntrenador(indiceCartaSeleccionada);
        agregarRegistro(resultado);
        indiceCartaSeleccionada = -1;
        actualizarVista();
    }

    private void accionRetirar() {
        Jugador jugador = juego.getJugadorActivo();
        if (jugador.getBanca().isEmpty()) {
            mostrarAviso("No tienes Pokemon en la banca para realizar el cambio");
            return;
        }

        Pokemon activo = jugador.getPokemonActivo();
        int costo = activo.getCostoRetiradaActual();
        if (activo.getEnergias() < costo) {
            mostrarAviso(activo.getNombre() + " necesita " + costo + " energias para retirarse");
            return;
        }

        String[] opciones = new String[jugador.getBanca().size()];
        for (int i = 0; i < jugador.getBanca().size(); i++) {
            Pokemon p = jugador.getBanca().get(i);
            opciones[i] = p.getNombre() + " (" + p.getPs() + "/" + p.getPsMaximos() + " PS)";
        }

        int eleccion = JOptionPane.showOptionDialog(
            this,
            "Selecciona el Pokemon de tu banca que pasara a activo",
            "Retirar Pokemon",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (eleccion >= 0) {
            String resultado = juego.retirarPokemon(eleccion);
            agregarRegistro(resultado);
            indiceCartaSeleccionada = -1;
            actualizarVista();
        }
    }

    private void accionAtacar() {
        Jugador jugador = juego.getJugadorActivo();
        if (juego.isEsPrimerTurnoActual() && jugador == juego.getJugador1()) {
            mostrarAviso("El jugador que empieza no puede atacar en su primer turno");
            return;
        }

        Pokemon activo = jugador.getPokemonActivo();
        int numeroAtaques = 1;
        if (activo.getNombreAtaque2() != null) {
            numeroAtaques = 2;
        }

        String[] opciones = new String[numeroAtaques];
        opciones[0] = activo.getNombreAtaque1() + " (Costo: " + activo.getCostoEnergia1() + " | Dano: " + activo.getDanoAtaque1() + ")";
        if (numeroAtaques == 2) {
            opciones[1] = activo.getNombreAtaque2() + " (Costo: " + activo.getCostoEnergia2() + " | Dano: " + activo.getDanoAtaque2() + ")";
        }

        int eleccion = JOptionPane.showOptionDialog(
            this,
            "Selecciona el ataque de " + activo.getNombre(),
            "Atacar",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (eleccion >= 0) {
            String resultado = juego.atacar(eleccion + 1);
            agregarRegistro(resultado);
            indiceCartaSeleccionada = -1;

            if (juego.isPartidaTerminada()) {
                actualizarVista();
                mostrarFinDePartida();
                return;
            }

            if (juego.isEsperandoReemplazo()) {
                agregarRegistro("El rival debe elegir un Pokemon de su banca para continuar");
                mostrarTransicionTurno(juego.getJugadorRival().getNombre());
                return;
            }

            mostrarTransicionTurno(juego.getJugadorActivo().getNombre());
        }
    }

    private void accionReemplazarDebilitado() {
        Jugador rival = juego.getJugadorRival();
        if (rival.getBanca().isEmpty()) {
            return;
        }

        String[] opciones = new String[rival.getBanca().size()];
        for (int i = 0; i < rival.getBanca().size(); i++) {
            Pokemon p = rival.getBanca().get(i);
            opciones[i] = p.getNombre() + " (" + p.getPs() + "/" + p.getPsMaximos() + " PS)";
        }

        int eleccion = JOptionPane.showOptionDialog(
            this,
            rival.getNombre() + " selecciona el Pokemon de tu banca que pasara a activo",
            "Reemplazar Pokemon Debilitado",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (eleccion >= 0) {
            String resultado = juego.elegirReemplazoRival(eleccion);
            agregarRegistro(resultado);
            indiceCartaSeleccionada = -1;
            mostrarTransicionTurno(juego.getJugadorActivo().getNombre());
        }
    }

    private void accionTerminarTurno() {
        String resultado = juego.terminarTurno();
        agregarRegistro(resultado);
        indiceCartaSeleccionada = -1;
        mostrarTransicionTurno(juego.getJugadorActivo().getNombre());
    }

    private void actualizarVista() {
        Jugador j1 = juego.getJugador1();
        Jugador j2 = juego.getJugador2();

        textoPuntosJ1.setText("Puntos " + j1.getNombre() + ": " + j1.getRondasGanadas() + "/3");
        textoPuntosJ2.setText("Puntos " + j2.getNombre() + ": " + j2.getRondasGanadas() + "/3");
        textoMazoJ1.setText("Mazo " + j1.getNombre() + ": " + j1.getMazo().getCartas().size() + " cartas");
        textoMazoJ2.setText("Mazo " + j2.getNombre() + ": " + j2.getMazo().getCartas().size() + " cartas");

        Jugador visible = obtenerJugadorVisible();
        Jugador rival = obtenerRivalVisible();

        if (fasePreparacion > 0) {
            textoTurnoActual.setText("Fase: Preparacion - Elige " + visible.getNombre());
            textoEstadoEnergia.setText("Energia del turno: Pendiente");
        } else if (juego.isEsperandoReemplazo()) {
            textoTurnoActual.setText("Reemplazo obligatorio: " + visible.getNombre());
            textoEstadoEnergia.setText("Energia del turno: Esperando reemplazo");
        } else {
            textoTurnoActual.setText("Turno actual: " + visible.getNombre());
            if (juego.isEnergiaUsada()) {
                textoEstadoEnergia.setText("Energia del turno: Usada");
            } else {
                textoEstadoEnergia.setText("Energia del turno: Disponible");
            }
        }

        actualizarTarjetaPokemon(rival.getPokemonActivo(), textoRivalActivoInfo, barraRivalActivo);
        Pokemon rBanca1 = null;
        if (rival.getBanca().size() >= 1) {
            rBanca1 = rival.getBanca().get(0);
        }
        Pokemon rBanca2 = null;
        if (rival.getBanca().size() >= 2) {
            rBanca2 = rival.getBanca().get(1);
        }
        actualizarTarjetaPokemon(rBanca1, textoRivalBanca1Info, barraRivalBanca1);
        actualizarTarjetaPokemon(rBanca2, textoRivalBanca2Info, barraRivalBanca2);

        actualizarTarjetaPokemon(visible.getPokemonActivo(), textoJugadorActualActivoInfo, barraJugadorActualActivo);
        Pokemon pBanca1 = null;
        if (visible.getBanca().size() >= 1) {
            pBanca1 = visible.getBanca().get(0);
        }
        Pokemon pBanca2 = null;
        if (visible.getBanca().size() >= 2) {
            pBanca2 = visible.getBanca().get(1);
        }
        actualizarTarjetaPokemon(pBanca1, textoJugadorActualBanca1Info, barraJugadorActualBanca1);
        actualizarTarjetaPokemon(pBanca2, textoJugadorActualBanca2Info, barraJugadorActualBanca2);

        actualizarEstadoBotones();
        actualizarManoVisual(visible);
    }

    private void actualizarTarjetaPokemon(Pokemon pokemon, JLabel textoInfo, JProgressBar barra) {
        if (pokemon == null) {
            textoInfo.setText("<html>Sin Pokemon</html>");
            barra.setMaximum(100);
            barra.setValue(0);
            barra.setString("0/0 PS");
            return;
        }

        String etapa = "Basico";
        if (pokemon.getEtapa() != null) {
            etapa = pokemon.getEtapa();
        }

        String texto = "<html><b>" + pokemon.getNombre() + "</b> [" + etapa + "]<br>"
            + "Tipo: " + pokemon.getTipo() + " | Energias: " + pokemon.getEnergias() + "<br>"
            + "Retirada: " + pokemon.getCostoRetiradaActual() + "</html>";

        textoInfo.setText(texto);
        barra.setMaximum(pokemon.getPsMaximos());
        int psMostrar = pokemon.getPs();
        if (psMostrar < 0) {
            psMostrar = 0;
        }
        barra.setValue(psMostrar);
        barra.setString(psMostrar + "/" + pokemon.getPsMaximos() + " PS");
    }

    private void actualizarEstadoBotones() {
        boolean enPreparacion = fasePreparacion > 0;
        boolean esperandoReemplazo = juego.isEsperandoReemplazo();
        boolean terminada = juego.isPartidaTerminada();

        botonElegirInicial.setEnabled(enPreparacion && !terminada);
        botonReemplazarDebilitado.setEnabled(esperandoReemplazo && !terminada);

        boolean turnoNormal = !enPreparacion && !esperandoReemplazo && !terminada;

        botonBajarBanca.setEnabled(turnoNormal && juego.getJugadorActivo().getBanca().size() < 2);
        botonAdjuntarEnergia.setEnabled(turnoNormal && !juego.isEnergiaUsada());
        botonEvolucionar.setEnabled(turnoNormal && !juego.isEsPrimerTurnoActual());
        botonUsarEntrenador.setEnabled(turnoNormal);
        botonRetirar.setEnabled(turnoNormal && !juego.getJugadorActivo().getBanca().isEmpty());

        boolean puedeAtacar = turnoNormal;
        if (juego.isEsPrimerTurnoActual() && juego.getJugadorActivo() == juego.getJugador1()) {
            puedeAtacar = false;
        }
        botonAtacar.setEnabled(puedeAtacar);
        botonTerminarTurno.setEnabled(turnoNormal);
    }

    private void actualizarManoVisual(Jugador jugador) {
        panelCartasMano.removeAll();

        if (manoOculta) {
            textoCartaSeleccionada.setText("Mano oculta durante el cambio de turno");
            panelCartasMano.revalidate();
            panelCartasMano.repaint();
            return;
        }

        List<Carta> mano = jugador.getMano();

        if (indiceCartaSeleccionada >= 0 && indiceCartaSeleccionada < mano.size()) {
            textoCartaSeleccionada.setText("Carta seleccionada: " + mano.get(indiceCartaSeleccionada).getNombre());
        } else {
            indiceCartaSeleccionada = -1;
            textoCartaSeleccionada.setText("Carta seleccionada: Ninguna");
        }

        for (int i = 0; i < mano.size(); i++) {
            Carta carta = mano.get(i);
            final int indiceActual = i;

            String texto = formatearTextoCarta(carta);
            JButton botonCarta = new JButton(texto);
            botonCarta.setPreferredSize(new Dimension(170, 95));

            if (i == indiceCartaSeleccionada) {
                botonCarta.setBackground(new Color(200, 230, 255));
            }

            botonCarta.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent e) {
                    indiceCartaSeleccionada = indiceActual;
                    actualizarVista();
                }
            });

            panelCartasMano.add(botonCarta);
        }

        panelCartasMano.revalidate();
        panelCartasMano.repaint();
    }

    private String formatearTextoCarta(Carta carta) {
        if (carta instanceof EvolucionPokemon) {
            EvolucionPokemon evo = (EvolucionPokemon) carta;
            return "<html><center><b>" + evo.getNombre() + "</b><br>"
                + evo.getEtapa() + " (" + evo.getTipo() + ")<br>"
                + "De: " + evo.getEvolucionaDe() + "<br>"
                + "PS: " + evo.getPsMaximos() + "</center></html>";
        }
        if (carta instanceof Pokemon) {
            Pokemon poke = (Pokemon) carta;
            return "<html><center><b>" + poke.getNombre() + "</b><br>"
                + "Basico (" + poke.getTipo() + ")<br>"
                + "PS: " + poke.getPsMaximos() + "</center></html>";
        }
        if (carta instanceof Entrenador) {
            Entrenador ent = (Entrenador) carta;
            return "<html><center><b>" + ent.getNombre() + "</b><br>"
                + "Entrenador (" + ent.getTipo() + ")</center></html>";
        }
        return carta.getNombre();
    }

    private void agregarRegistro(String texto) {
        areaRegistro.append("- " + texto + "\n");
        areaRegistro.setCaretPosition(areaRegistro.getDocument().getLength());
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarFinDePartida() {
        String[] opciones = {"Jugar otra partida", "Salir"};
        int respuesta = JOptionPane.showOptionDialog(
            this,
            "Felicidades " + juego.getGanador() + "\n" + juego.getMensajeFinPartida(),
            "Fin de la Partida",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (respuesta == 0) {
            VentanaInicio nuevaInicio = new VentanaInicio();
            nuevaInicio.setVisible(true);
            dispose();
        } else {
            System.exit(0);
        }
    }
}