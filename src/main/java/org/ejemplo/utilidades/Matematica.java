package org.ejemplo.utilidades;

public class Matematica {
    public static Double mayor(Double n1, Double n2){
        Double m = n1;
        if (n2 > m ){
            m = n2;
        }
        return  m;
    }

    public static Boolean esPrimo(Integer n){
        int i, a = 0;
        for (i = 1; i < (n+1); i++){
            if (n % i == 0){
                a++;
            }
        }
        return (a == 2);
    }
}
