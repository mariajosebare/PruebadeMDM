package com.example.pruebademdm;

/* public class validarCI {
    public static boolean esCIValida(String ci) {

        if(ci.length() != 7 && ci.length() != 8){
            return false;
        }else{
            try{
                Integer.parseInt(ci);
            }catch (NumberFormatException e){
                return false;
            }
        }
        int digVerificador = Integer.parseInt((ci.charAt(ci.length() - 1)) + "" ) ;
        int[] factores;
        if(ci.length() == 7){ // CI viejas
            factores = new int[]{9, 8, 7, 6, 3, 4};
        }else{
            factores = new int[]{2, 9, 8, 7, 6, 3, 4};
        }
        int suma = 0;
        for(int i=0; i int digito = Integer.parseInt(ci.charAt(i) + "" ) ;
        suma += digito * factores[ i ];
    }
    int resto = suma % 10;
    int checkdigit = 10 - resto;

    if(checkdigit == 10){
        return (digVerificador == 0);
    }else {
        return (checkdigit == digVerificador) ;
    }
}
}*/
