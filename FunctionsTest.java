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
	void PRUEBAARITMETICAS() {
		Functions funciones = new Functions();
		assertEquals("18" , funciones.aritmetricas("(+ (/ 6 3) (* 2 8))"));
	
	}
	
	@Test
	void PRUEBAPREDICADO() {
		Functions funciones = new Functions();
		assertEquals("true" , funciones.predicados("(atom a)"));
	
	}
	
	@Test
	void PRUEBASETQ() {
		Functions funciones = new Functions();
		funciones.setq("(setq x 5)");
		assertEquals("7" , funciones.aritmetricas("(+ x 2)"));
	
	}
	
	@Test
	void PRUEBAQUOTE() {
		Functions funciones = new Functions();
		funciones.setq("(setq x 5)");
		assertEquals("false" , funciones.predicados("(equal (quote(x)) 5)"));
	
	}
	
	@Test
	void PRUEBADEFUN() {
		Functions funciones = new Functions();
		funciones.create_function("(defun suma (a b) \n(+ a b))");
		assertEquals("7" , funciones.defun("(suma (5 2))"));
	
	}
	
	
	@Test
	void PRUEBAIF() {
		Functions funciones = new Functions();
		assertEquals("10" , funciones.ifcond("(if \n(> 5 10) \n5 \n10)"));
	
	}
	
	
}

