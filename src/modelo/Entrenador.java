package modelo;

import java.util.List;

import java.util.Random;



public class Entrenador extends Carta implements Activable {

private String efecto;



public Entrenador(String nombre, String tipo, String efecto) {

super(nombre, tipo);

this.efecto = efecto;

}



public String getEfecto() {

return efecto;

}



@Override

public String activar(Jugador jugador) {

String nombreCarta = getNombre();



if (nombreCarta.equals("Investigacion del Profesor")) {

for (int i = 0; i < 2; i++) {

jugador.robarCarta();

}

return "Robaste dos cartas del mazo";



} else if (nombreCarta.equals("Pocion")) {

Pokemon activo = jugador.getPokemonActivo();

if (activo != null) {

int nuevaVida = activo.getPs() + 20;

if (nuevaVida > activo.getPsMaximos()) {

nuevaVida = activo.getPsMaximos();

}

activo.setPs(nuevaVida);

return activo.getNombre() + " fue curado. Ahora tiene " + activo.getPs() + "/" + activo.getPsMaximos() + " ps";

}



} else if (nombreCarta.equals("Pokeball")) {

List<Carta> cartasMazo = jugador.getMazo().getCartas();



for (int i = 0; i < cartasMazo.size(); i++) {

Carta cartaP = cartasMazo.get(i);

if (cartaP instanceof Pokemon && !(cartaP instanceof EvolucionPokemon)) {

jugador.getMano().add(cartaP);

cartasMazo.remove(i);

jugador.getMazo().barajar();

return "Usaste una Pokeball. Añadiste a " + cartaP.getNombre() + " a tu mano";

}

}



return "No quedan Pokemon basicos en tu mazo";



} else if (nombreCarta.equals("Turbo Velocidad")) {

Pokemon activo = jugador.getPokemonActivo();

if (activo != null) {

activo.reducirRetirada();

return "Costo de retirada de " + activo.getNombre() + " se redujo en 1 | Costo actual: " + activo.getCostoRetiradaActual();

}



} else if (nombreCarta.equals("Misty")) {

Pokemon activo = jugador.getPokemonActivo();

if (activo != null && activo.getTipo().equals("Agua")) {

Random random = new Random();

int energiasGanadas = 0;



while (energiasGanadas < 3 && random.nextBoolean()) {

energiasGanadas++;

}



activo.agregarEnergia(energiasGanadas);

return "Se añadieron " + energiasGanadas + " energias Agua a " + activo.getNombre();

} else {

return "Tu Pokemon activo no es de tipo Agua";

}

}



return "Se activo la carta";

}

} 