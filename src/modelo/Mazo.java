package modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {
    private List<Carta> cartas;

    public Mazo() {
        this.cartas = new ArrayList<Carta>();
        iniciarMazoFuegoElectrico();
        barajar();
    }

    public Mazo(int opcionMazo) {
        this.cartas = new ArrayList<Carta>();
        if (opcionMazo == 2) {
            iniciarMazoAguaIncoloro();
        } else {
            iniciarMazoFuegoElectrico();
        }
        barajar();
    }

    private void iniciarMazoFuegoElectrico() {
        cartas.add(new Pokemon("Pikachu", "Electrico", 60, 1, "Mordisco", 1, 20));
        cartas.add(new Pokemon("Pikachu", "Electrico", 60, 1, "Mordisco", 1, 20));
        cartas.add(new EvolucionPokemon("Raichu", "Electrico", 100, 1, "Trueno", 2, 60, "Fase 1", "Pikachu"));

        cartas.add(new Pokemon("Charmander", "Fuego", 60, 1, "Ascuas", 1, 30));
        cartas.add(new Pokemon("Charmander", "Fuego", 60, 1, "Ascuas", 1, 30));
        cartas.add(new EvolucionPokemon("Charmeleon", "Fuego", 90, 2, "Garra Ignea", 2, 50, "Fase 1", "Charmander"));
        cartas.add(new EvolucionPokemon("Charizard", "Fuego", 150, 2, "Giro Fuego", 4, 120, "Fase 2", "Charmeleon"));

        cartas.add(new Pokemon("Zapdos ex", "Electrico", 130, 1,
                "Picotazo", 1, 30,
                "Tormenta Electrica", 3, 100));

        cartas.add(new Pokemon("Moltres ex", "Fuego", 130, 1,
                "Aleteo", 1, 30,
                "Danza Infernal", 3, 70));

        cartas.add(new Pokemon("Mewtwo ex", "Psiquico", 150, 2,
                "Ataque Psiquico", 2, 50,
                "Psicorrayo", 3, 150));

        for (int i = 0; i < 2; i++) {
            cartas.add(new Entrenador("Investigacion del Profesor", "Soporte", "Roba 2 cartas del mazo"));
            cartas.add(new Entrenador("Pocion", "Objeto", "Cura 20 PS al Pokemon activo propio"));
            cartas.add(new Entrenador("Pokeball", "Objeto", "Busca en tu mazo 1 Pokemon basico y ponlo en tu mano"));
            cartas.add(new Entrenador("Turbo Velocidad", "Objeto", "Reduce en 1 el costo de retirada del Pokemon activo este turno"));
            cartas.add(new Entrenador("Misty", "Soporte", "Adjunta hasta 3 energia Agua a uno de tus Pokemon de Agua"));
        }
    }

    private void iniciarMazoAguaIncoloro() {
        cartas.add(new Pokemon("Squirtle", "Agua", 60, 1, "Placaje", 1, 20));
        cartas.add(new Pokemon("Squirtle", "Agua", 60, 1, "Placaje", 1, 20));
        cartas.add(new EvolucionPokemon("Wartortle", "Agua", 80, 1, "Salpicadura", 2, 40, "Fase 1", "Squirtle"));
        cartas.add(new EvolucionPokemon("Blastoise", "Agua", 150, 3, "Hidrobomba", 3, 100, "Fase 2", "Wartortle"));

        cartas.add(new Pokemon("Magikarp", "Agua", 30, 1, "Chapoteo", 1, 10));
        cartas.add(new EvolucionPokemon("Gyarados", "Agua", 150, 4, "Hiperrayo", 4, 100, "Fase 1", "Magikarp"));

        cartas.add(new Pokemon("Jigglypuff", "Incoloro", 70, 1, "Golpe", 1, 20));
        cartas.add(new EvolucionPokemon("Wigglytuff", "Incoloro", 110, 2, "Bofeton", 2, 50, "Fase 1", "Jigglypuff"));

        cartas.add(new Pokemon("Snorlax", "Incoloro", 150, 3, "Golpe Cuerpo", 3, 100));

        cartas.add(new Pokemon("Articuno ex", "Agua", 130, 1,
                "Viento Helado", 1, 30,
                "Ventisca", 3, 100));

        for (int i = 0; i < 2; i++) {
            cartas.add(new Entrenador("Investigacion del Profesor", "Soporte", "Roba 2 cartas del mazo"));
            cartas.add(new Entrenador("Pocion", "Objeto", "Cura 20 PS al Pokemon activo propio"));
            cartas.add(new Entrenador("Pokeball", "Objeto", "Busca en tu mazo 1 Pokemon basico y ponlo en tu mano"));
            cartas.add(new Entrenador("Turbo Velocidad", "Objeto", "Reduce en 1 el costo de retirada del Pokemon activo este turno"));
            cartas.add(new Entrenador("Misty", "Soporte", "Adjunta hasta 3 energia Agua a uno de tus Pokemon de Agua"));
        }
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public List<Carta> getCartas() {
        return cartas;
    }
}