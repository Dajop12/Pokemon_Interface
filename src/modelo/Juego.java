package modelo;
import java.util.Collections;

public class Juego {

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActivo;
    private Jugador jugadorRival;

    private boolean energiaUsada;
    private boolean soporteUsado;
    private boolean primerTurnoJ1;
    private boolean primerTurnoJ2;
    private boolean partidaTerminada;
    private boolean esperandoReemplazo;
    private String ganador;
    private String mensajeFinPartida;

    public Juego(String nombreJ1, int mazoJ1, String nombreJ2, int mazoJ2) {
        this.jugador1 = new Jugador(nombreJ1, mazoJ1);
        this.jugador2 = new Jugador(nombreJ2, mazoJ2);
        this.jugadorActivo = jugador1;
        this.jugadorRival = jugador2;

        this.energiaUsada = false;
        this.soporteUsado = false;
        this.primerTurnoJ1 = true;
        this.primerTurnoJ2 = true;
        this.partidaTerminada = false;
        this.esperandoReemplazo = false;
        this.ganador = null;
        this.mensajeFinPartida = "";

        repartirManoInicial(jugador1);
        repartirManoInicial(jugador2);
    }

    private void repartirManoInicial(Jugador jugador) {
        int cantidadPokemon = 0;

        while (cantidadPokemon < 2) {
            cantidadPokemon = 0;
            for (int i = 0; i < 5; i++) {
                jugador.robarCarta();
            }
            for (int i = 0; i < jugador.getMano().size(); i++) {
                Carta carta = jugador.getMano().get(i);
                if (carta instanceof Pokemon && !(carta instanceof EvolucionPokemon)) {
                    cantidadPokemon++;
                    if (cantidadPokemon >= 2) {
                        break;
                    }
                }
            }
            if (cantidadPokemon < 2) {
                jugador.getMazo().getCartas().addAll(jugador.getMano());
                jugador.getMano().clear();
                Collections.shuffle(jugador.getMazo().getCartas());
            }
        }
    }

    public String elegirPokemonInicial(Jugador jugador, int eleccion) {
        if (jugador.getPokemonActivo() != null) {
            return "Ya tienes un Pokemon activo inicial";
        }
        if (eleccion >= 0 && eleccion < jugador.getMano().size()) {
            Carta cartaElegida = jugador.getMano().get(eleccion);

            if (cartaElegida instanceof Pokemon && !(cartaElegida instanceof EvolucionPokemon)) {
                Pokemon pokeInicial = (Pokemon) cartaElegida;
                pokeInicial.setEntroEsteTurno(true);
                jugador.setPokemonActivo(pokeInicial);
                jugador.getMano().remove(eleccion);
                return jugador.getNombre() + " ha lanzado a " + jugador.getPokemonActivo().getNombre() + " | " + jugador.getPokemonActivo().tipo;
            } else {
                return "Debes escoger a un Pokemon basico";
            }
        } else {
            return "Numero invalido";
        }
    }

    public String iniciarPrimerTurno() {
        if (jugador1.getPokemonActivo() == null || jugador2.getPokemonActivo() == null) {
            return "Ambos jugadores deben escoger su Pokemon activo";
        }
        jugadorActivo = jugador1;
        jugadorRival = jugador2;
        energiaUsada = false;
        soporteUsado = false;
        jugadorActivo.robarCarta();
        return "Turno de " + jugadorActivo.getNombre();
    }

    private int calcularDano(Pokemon atacante, Pokemon defensor, int danoBase) {
        String tipoAtacante = atacante.getTipo();
        String tipoDefensor = defensor.getTipo();
        boolean debilidad = false;

        if (tipoDefensor.equals("Agua") && tipoAtacante.equals("Electrico")) {
            debilidad = true;
        } else if (tipoDefensor.equals("Fuego") && tipoAtacante.equals("Agua")) {
            debilidad = true;
        }

        if (debilidad == true) {
            return danoBase + 20;
        }
        return danoBase;
    }

    public String bajarPokemonABanca(int indiceBanca) {
        if (partidaTerminada == true) {
            return "La partida ya termino";
        }
        if (esperandoReemplazo == true) {
            return "El rival debe elegir un Pokemon de su banca";
        }
        if (jugadorActivo.getBanca().size() >= 2) {
            return "Tu banca ya esta llena";
        }
        if (indiceBanca >= 0 && indiceBanca < jugadorActivo.getMano().size()) {
            Carta carta = jugadorActivo.getMano().get(indiceBanca);
            if (carta instanceof Pokemon && !(carta instanceof EvolucionPokemon)) {
                Pokemon pokeBanca = (Pokemon) jugadorActivo.getMano().remove(indiceBanca);
                pokeBanca.setEntroEsteTurno(true);
                jugadorActivo.getBanca().add(pokeBanca);
                return "Bajaste a " + pokeBanca.getNombre() + " a la banca";
            } else if (carta instanceof EvolucionPokemon) {
                return "Una carta de evolucion solo entra evolucionando";
            } else {
                return "Esa carta no es un Pokemon";
            }
        } else {
            return "Numero fuera de rango";
        }
    }

    public String adjuntarEnergia(int energiaSeleccion) {
        if (partidaTerminada == true) {
            return "La partida ya termino";
        }
        if (esperandoReemplazo == true) {
            return "El rival debe elegir un Pokemon de su banca";
        }
        if (energiaUsada == true) {
            return "Ya añadiste tu energia de este turno";
        }
        Pokemon pokeActivo = jugadorActivo.getPokemonActivo();
        if (energiaSeleccion == 0) {
            pokeActivo.agregarEnergia(1);
            energiaUsada = true;
            return "Energia añadida a " + pokeActivo.getNombre();
        } else if (energiaSeleccion >= 1 && energiaSeleccion <= jugadorActivo.getBanca().size()) {
            jugadorActivo.getBanca().get(energiaSeleccion - 1).agregarEnergia(1);
            energiaUsada = true;
            return "Energia añadida a " + jugadorActivo.getBanca().get(energiaSeleccion - 1).getNombre();
        } else {
            return "Numero fuera de rango";
        }
    }

    public String evolucionarPokemon(int indiceMano, int destino) {
        if (partidaTerminada == true) {
            return "La partida ya termino";
        }
        if (esperandoReemplazo == true) {
            return "El rival debe elegir un Pokemon de su banca";
        }
        if ((jugadorActivo == jugador1 && primerTurnoJ1 == true) || (jugadorActivo == jugador2 && primerTurnoJ2 == true)) {
            return "No puedes evolucionar en tu primer turno";
        }
        if (indiceMano < 0 || indiceMano >= jugadorActivo.getMano().size()) {
            return "Numero fuera de rango";
        }
        Carta cartaElegida = jugadorActivo.getMano().get(indiceMano);
        if (!(cartaElegida instanceof EvolucionPokemon)) {
            return "Esa carta no es una evolucion";
        }
        EvolucionPokemon cartaEvo = (EvolucionPokemon) cartaElegida;

        Pokemon objetivo = null;
        if (destino == 0) {
            objetivo = jugadorActivo.getPokemonActivo();
        } else if (destino >= 1 && destino <= jugadorActivo.getBanca().size()) {
            objetivo = jugadorActivo.getBanca().get(destino - 1);
        } else {
            return "Numero fuera de rango";
        }

        if (objetivo.isEntroEsteTurno() == true) {
            return "No puedes evolucionar un Pokemon que entro en este turno";
        }
        if (objetivo.isEvolucionoEsteTurno() == true) {
            return "Este Pokemon ya evoluciono en este turno";
        }
        if (!objetivo.getNombre().equals(cartaEvo.getEvolucionaDe())) {
            return cartaEvo.getNombre() + " solo evoluciona de " + cartaEvo.getEvolucionaDe();
        }

        String nombreAnterior = objetivo.getNombre();
        objetivo.evolucionar(cartaEvo);
        jugadorActivo.getMano().remove(indiceMano);
        return nombreAnterior + " evoluciono a " + objetivo.getNombre();
    }

    public String jugarEntrenador(int indiceCarta) {
        if (partidaTerminada == true) {
            return "La partida ya termino";
        }
        if (esperandoReemplazo == true) {
            return "El rival debe elegir un Pokemon de su banca";
        }
        if (indiceCarta >= 0 && indiceCarta < jugadorActivo.getMano().size()) {
            Carta cartaElegida = jugadorActivo.getMano().get(indiceCarta);
            if (cartaElegida instanceof Entrenador) {
                Entrenador cartaEntrenador = (Entrenador) cartaElegida;
                if (cartaEntrenador.getTipo().equals("Soporte") && soporteUsado == true) {
                    return "Ya jugaste una carta de Soporte en este turno";
                } else {
                    if (cartaEntrenador.getTipo().equals("Soporte")) {
                        soporteUsado = true;
                    }
                    jugadorActivo.getMano().remove(indiceCarta);
                    return cartaEntrenador.activar(jugadorActivo);
                }
            } else {
                return "No es una carta de tipo Entrenador";
            }
        } else {
            return "Numero fuera de rango";
        }
    }

    public String retirarPokemon(int indiceCambio) {
        if (partidaTerminada == true) {
            return "La partida ya termino";
        }
        if (esperandoReemplazo == true) {
            return "El rival debe elegir un Pokemon de su banca";
        }
        if (jugadorActivo.getBanca().isEmpty()) {
            return "No tienes Pokemon en la banca para cambiar";
        }
        Pokemon pokeActivo = jugadorActivo.getPokemonActivo();
        int costoRetirada = pokeActivo.getCostoRetiradaActual();
        if (pokeActivo.getEnergias() < costoRetirada) {
            return "No tienes suficiente energia para retirar necesitas " + costoRetirada;
        }
        if (indiceCambio >= 0 && indiceCambio < jugadorActivo.getBanca().size()) {
            pokeActivo.quitarEnergia(costoRetirada);
            Pokemon nuevoActivo = jugadorActivo.getBanca().get(indiceCambio);
            jugadorActivo.getBanca().remove(indiceCambio);
            jugadorActivo.getBanca().add(pokeActivo);
            jugadorActivo.setPokemonActivo(nuevoActivo);
            return "Retiraste a " + pokeActivo.getNombre() + " Ahora peleas con " + nuevoActivo.getNombre();
        } else {
            return "Numero fuera de rango";
        }
    }

    public String atacar(int ataqueElegido) {
        if (partidaTerminada == true) {
            return "La partida ya termino";
        }
        if (esperandoReemplazo == true) {
            return "El rival debe elegir un Pokemon de su banca";
        }
        if (jugadorActivo == jugador1 && primerTurnoJ1 == true) {
            return "No puedes atacar en el primer turno";
        }

        Pokemon pokeActivo = jugadorActivo.getPokemonActivo();
        int costoNecesario = 0;
        int danoBase = 0;
        String nombreAtaque = "";
        boolean ataqueValido = false;

        if (ataqueElegido == 1) {
            costoNecesario = pokeActivo.getCostoEnergia1();
            danoBase = pokeActivo.getDanoAtaque1();
            nombreAtaque = pokeActivo.getNombreAtaque1();
            ataqueValido = true;
        } else if (ataqueElegido == 2 && pokeActivo.getNombreAtaque2() != null) {
            costoNecesario = pokeActivo.getCostoEnergia2();
            danoBase = pokeActivo.getDanoAtaque2();
            nombreAtaque = pokeActivo.getNombreAtaque2();
            ataqueValido = true;
        }

        if (!ataqueValido) {
            return "Ataque invalido";
        }
        if (pokeActivo.getEnergias() < costoNecesario) {
            return "No tienes suficientes energias para usar " + nombreAtaque;
        }

        String mensaje = pokeActivo.getNombre() + " uso " + nombreAtaque;
        int danoTotal = calcularDano(pokeActivo, jugadorRival.getPokemonActivo(), danoBase);
        if (danoTotal > danoBase) {
            mensaje = mensaje + " | Debilidad provocada";
        }

        int psActuales2 = jugadorRival.getPokemonActivo().getPs();
        jugadorRival.getPokemonActivo().setPs(psActuales2 - danoTotal);
        mensaje = mensaje + " | El rival recibe " + danoTotal + " de dano y le quedan " + jugadorRival.getPokemonActivo().getPs() + " ps";

        if (jugadorRival.getPokemonActivo().getPs() <= 0) {
            mensaje = mensaje + " | " + jugadorRival.getPokemonActivo().getNombre() + " se ha debilitado";

            int premios = 1;
            if (jugadorRival.getPokemonActivo().getNombre().endsWith("ex")) {
                premios = 2;
                mensaje = mensaje + " | Derrotaste a un Pokemon-ex Obtienes 2 puntos";
            } else {
                mensaje = mensaje + " | Obtienes 1 punto";
            }

            for (int i = 0; i < premios; i++) {
                jugadorActivo.incrementarRondasGanadas();
            }

            mensaje = mensaje + " | Puntos de premio de " + jugadorActivo.getNombre() + " " + jugadorActivo.getRondasGanadas() + "/3";

            if (jugadorActivo.getRondasGanadas() >= 3) {
                partidaTerminada = true;
                ganador = jugadorActivo.getNombre();
                mensajeFinPartida = jugadorActivo.getNombre() + " HA GANADO LA PARTIDA";
                return mensaje + " | " + mensajeFinPartida;
            }

            if (jugadorRival.getBanca().isEmpty()) {
                partidaTerminada = true;
                ganador = jugadorActivo.getNombre();
                mensajeFinPartida = jugadorRival.getNombre() + " no tiene Pokemon en la banca " + jugadorActivo.getNombre() + " HA GANADO LA PARTIDA";
                return mensaje + " | " + mensajeFinPartida;
            }

            jugadorRival.setPokemonActivo(null);
            esperandoReemplazo = true;
            return mensaje + " | " + jugadorRival.getNombre() + " elige un Pokemon de tu banca para seguir jugando";
        }

        String turnoSiguiente = terminarTurno();
        return mensaje + " | " + turnoSiguiente;
    }

    public String elegirReemplazoRival(int reemplazo) {
        if (esperandoReemplazo == false) {
            return "No hay reemplazo pendiente";
        }
        if (reemplazo >= 0 && reemplazo < jugadorRival.getBanca().size()) {
            jugadorRival.setPokemonActivo(jugadorRival.getBanca().get(reemplazo));
            jugadorRival.getBanca().remove(reemplazo);
            esperandoReemplazo = false;
            String mensaje = jugadorRival.getNombre() + " juega con " + jugadorRival.getPokemonActivo().getNombre();
            String turnoSiguiente = terminarTurno();
            return mensaje + " | " + turnoSiguiente;
        } else {
            return "Numero invalido";
        }
    }

    public String terminarTurno() {
        if (partidaTerminada == true) {
            return mensajeFinPartida;
        }
        if (esperandoReemplazo == true) {
            return "El rival debe elegir un Pokemon de su banca";
        }

        String nombreAnterior = jugadorActivo.getNombre();

        if (jugadorActivo == jugador1) {
            primerTurnoJ1 = false;
            jugadorActivo = jugador2;
            jugadorRival = jugador1;
        } else {
            primerTurnoJ2 = false;
            jugadorActivo = jugador1;
            jugadorRival = jugador2;
        }

        energiaUsada = false;
        soporteUsado = false;

        jugadorActivo.getPokemonActivo().reiniciarReduccionRetirada();
        jugadorActivo.getPokemonActivo().setEntroEsteTurno(false);
        jugadorActivo.getPokemonActivo().setEvolucionoEsteTurno(false);

        for (int i = 0; i < jugadorActivo.getBanca().size(); i++) {
            jugadorActivo.getBanca().get(i).setEntroEsteTurno(false);
            jugadorActivo.getBanca().get(i).setEvolucionoEsteTurno(false);
        }

        jugadorActivo.robarCarta();
        return nombreAnterior + " termina su turno | Turno de " + jugadorActivo.getNombre();
    }

    public Jugador getJugador1() { return jugador1; }
    public Jugador getJugador2() { return jugador2; }
    public Jugador getJugadorActivo() { return jugadorActivo; }
    public Jugador getJugadorRival() { return jugadorRival; }
    public boolean isEnergiaUsada() { return energiaUsada; }
    public boolean isSoporteUsado() { return soporteUsado; }
    public boolean isEsPrimerTurnoActual() {
        if (jugadorActivo == jugador1) {
            return primerTurnoJ1;
        }
        return primerTurnoJ2;
    }
    public boolean isPartidaTerminada() { return partidaTerminada; }
    public boolean isEsperandoReemplazo() { return esperandoReemplazo; }
    public String getGanador() { return ganador; }
    public String getMensajeFinPartida() { return mensajeFinPartida; }
}