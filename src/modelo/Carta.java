package modelo;

public abstract class Carta {
protected String nombre;
protected String tipo;

public Carta(String nombre, String tipo) {

this.nombre = nombre;

this.tipo = tipo;

}


public String getNombre() { return nombre; }

public String getTipo() { return tipo; }


public void setNombre(String nombre) {

this.nombre = nombre;

}



public void setTipo(String tipo) {

this.tipo = tipo;

}

} 