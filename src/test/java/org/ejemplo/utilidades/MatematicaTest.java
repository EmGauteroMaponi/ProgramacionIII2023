package org.ejemplo.utilidades;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatematicaTest {

    @Test
    public void testMayorPrimeroMayor(){
        Double n1 = 1.00000001;
        Double n2 = 1.000000001;

        Double resultado = Matematica.mayor(n1,n2);

        assertEquals(n1, resultado);
    }

    @Test
    public void testMayorSegundoMayor(){
        Double n1 = 1.00000001;
        Double n2 = 1.0000001;

        Double resultado = Matematica.mayor(n1,n2);

        assertEquals(n2, resultado);
    }

    @Test
    public void test3esPrimo(){

        Boolean resultado = Matematica.esPrimo(3);

        assertTrue(resultado);
    }

    @Test
    public void test4noEsPrimo(){

        Boolean resultado = Matematica.esPrimo(4);

        assertFalse(resultado);
    }

}