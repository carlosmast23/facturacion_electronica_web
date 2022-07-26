
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author
 */
public class TestOrdenar {

    public static void main(String[] args) {
        List<Fecha> datos = new ArrayList<Fecha>();
        datos.add(new Fecha("2000", "01"));
        datos.add(new Fecha("2000", "04"));
        datos.add(new Fecha("2001", "05"));

        Collections.sort(datos, new Comparator<Fecha>() {
            public int compare(Fecha o1, Fecha o2) {
                // TODO Auto-generated method stub
                Integer fechaAnioMes = Integer.parseInt(o1.anio) + Integer.parseInt(o1.mes);
                Integer fechaAnioMes2 = Integer.parseInt(o2.anio) + Integer.parseInt(o2.mes);

                return fechaAnioMes2.compareTo(fechaAnioMes);
            }
        });
        
        for (Fecha dato : datos) {
            System.out.println(dato);
        }
    }

    public static class Fecha {

        public Fecha(String anio, String mes) {
            this.anio = anio;
            this.mes = mes;
        }

        public String anio;
        public String mes;

        @Override
        public String toString() {
            return anio+"-"+mes;
        }
        
        
    }

    
    
    
}
