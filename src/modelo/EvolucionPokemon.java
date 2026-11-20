package modelo;

public class EvolucionPokemon extends Pokemon {
private String etapa;
private String evolucionaDe;
public EvolucionPokemon(String nombre, String tipo, int ps, int costoRetirada,
String nombreAtaque1, int costoEnergia1, int danoAtaque1,
String etapa, String evolucionaDe) {
super(nombre, tipo, ps, costoRetirada, nombreAtaque1, costoEnergia1, danoAtaque1);
this.etapa = etapa;
this.evolucionaDe = evolucionaDe;

}

@Override
public String getEtapa() {
return etapa;

}
public String getEvolucionaDe() {

return evolucionaDe;

}

}