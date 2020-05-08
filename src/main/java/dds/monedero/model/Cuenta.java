package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class Cuenta {
	
	  private double saldo = 0;
	  private List<Deposito> depositos = new ArrayList<>();
	  private List<Extraccion> extracciones = new ArrayList<>();
	
	  public Cuenta() {
		  saldo = 0;
	  }
	
	  public Cuenta(double montoInicial) {
		  saldo = montoInicial;
	  }
	
	  public void setDepositos(List<Deposito> depositos) {
		  this.depositos = depositos;
	  }
	  
	  public void setExtracciones(List<Extraccion> extracciones) {
		  this.extracciones = extracciones;
	  }
	
	  public void poner(double cuanto) {
		  validarNoNegativo(cuanto);
	
		  if (getDepositos().stream().count() >= 3) {
			  throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
		  }
	
		  agregarDeposito(new Deposito(LocalDate.now(), cuanto));
	  }
	
	  public void sacar(double cuanto) {
		  validarNoNegativo(cuanto);
		  if (getSaldo() - cuanto < 0) {
			  throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
		  }
		  double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
		  double limite = 1000 - montoExtraidoHoy;
		  if (cuanto > limite) {
			  throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
	          + " diarios, límite: " + limite);
		  }
		  agregarExtraccion(new Extraccion(LocalDate.now(), cuanto));
	  }
	  
	  private void validarNoNegativo(double cuanto) {
			if (cuanto <= 0) {
				throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
		    }
		}
	
	  public void agregarDeposito(Deposito deposito) {
		  depositos.add(deposito);
	  }
	  
	  public void agregarExtraccion(Extraccion extraccion) {
		  extracciones.add(extraccion);
	  }
	  
	
	  public double getMontoExtraidoA(LocalDate fecha) {
		  return getExtracciones().stream()
	        .filter(movimiento -> movimiento.esDeLaFecha(fecha))
	        .mapToDouble(Movimiento::getMonto)
	        .sum();
	  }
	
	  public List<Deposito> getDepositos() {
		  return depositos;
	  }
	  
	  public List<Extraccion> getExtracciones() {
		  return extracciones;
	  }
	
	  public double getSaldo() {
		  return saldo;
	  }
	
	  public void setSaldo(double saldo) {
		  this.saldo = saldo;
	  }
	
}
