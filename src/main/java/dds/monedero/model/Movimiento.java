package dds.monedero.model;

import java.time.LocalDate;

public abstract class Movimiento {
	  public LocalDate fecha;
	  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
	  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
	  public double monto;
	
	  public double getMonto() {
		  return monto;
	  }
	
	  public LocalDate getFecha() {
		  return fecha;
	  }
	
	  public boolean esDeLaFecha(LocalDate fecha) {
		  return this.fecha.equals(fecha);
	  }

	  public abstract double calcularValor(Cuenta cuenta);

}

