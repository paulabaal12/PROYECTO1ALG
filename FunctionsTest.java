import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FunctionsTest {

	@Test
	//PRUEBA DE EQUAL
	void pruebasequals() {
	Functions funciones = new Functions();

	//Double resultado = (Double) 
	assertEquals(true, funciones.equal(5, 5 ));
	
	}
	//PRUEBA DE ATOM
	@Test
	void pruebaatom() {
	Functions funciones = new Functions();
	assertEquals(true, funciones.atom(5));
	
	}
	//PRUEBA DE MAYOR QUE
	
	@Test
	void pruebamayorque() {
	Functions funciones = new Functions();
	assertEquals(true , funciones.greaterThan(8, 6));
	
	}
	//PRUEBA DE MENOR QUE
	
	@Test
	void pruebamenorque() {
	Functions funciones = new Functions();
	assertEquals(false , funciones.lessThan(8, 6));
	
	}
	
	@Test
	void PRUEBASUMA() {
	Functions funciones = new Functions();
	//assertEquals(false , funciones.aritmetricas(8, 6));
	
	}
	
	
}

