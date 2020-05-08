package dds.monedero.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class MonederoTest {
  private Cuenta cuenta;

  @Before
  public void init() {
    cuenta = new Cuenta();
  }

  @Test
  public void Poner() {
    cuenta.poner(1500);    
    assertEquals(cuenta.getSaldo(), 1500, 0.01);
  }

  @Test(expected = MontoNegativoException.class)
  public void PonerMontoNegativo() {
    cuenta.poner(-1500);
  }

  @Test
  public void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    assertEquals(cuenta.getSaldo(), 3856, 0.01);
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void MasDeTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    cuenta.poner(245);
  }

  @Test(expected = SaldoMenorException.class)
  public void ExtraerMasQueElSaldo() {
    cuenta.setSaldo(90);
    cuenta.sacar(1001);
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void ExtraerMasDe1000() {
    cuenta.setSaldo(5000);
    cuenta.sacar(1001);
  }

  @Test(expected = MontoNegativoException.class)
  public void ExtraerMontoNegativo() {
    cuenta.sacar(-500);
  }
  
  @Test
  public void Extraer() {
	  cuenta.setSaldo(2000);
	  cuenta.sacar(100);
	  assertEquals(cuenta.getSaldo(), 1900, 0.01);
  } 
  
  @Test
  public void ExtraerTresVeces() {
	  cuenta.setSaldo(2000);
	  cuenta.sacar(100);
	  cuenta.sacar(400);
	  cuenta.sacar(500);
	  assertEquals(cuenta.getSaldo(), 1000, 0.01);
  }
  
  @Test
  public void CrearCuentaYSetearMovimientos() {
	  Cuenta cuentaNueva = new Cuenta(1000);

	  assertEquals(cuentaNueva.getSaldo(), 1000, 0.01);
  }
  
  @Test
  public void SetearDepositos() {
	  
	  Deposito deposito1 = new Deposito(LocalDate.now(), 500);
	  Deposito deposito2 = new Deposito(LocalDate.now(), 400);
	  
	  List<Deposito> depositos = Arrays.asList(deposito1,deposito2);
	  cuenta.setDepositos(depositos);
	  
	  assertEquals(cuenta.getDepositos(), depositos);

  }
  
  @Test
  public void SetearExtracciones() {

	  Extraccion extraccion1 = new Extraccion(LocalDate.now(), 200);
	  Extraccion extraccion2 = new Extraccion(LocalDate.now(), 800);
	  
	  List<Extraccion> extracciones = Arrays.asList(extraccion1, extraccion2);

	  cuenta.setExtracciones(extracciones);
	  
	  assertEquals(cuenta.getExtracciones(), extracciones);
  }  

}