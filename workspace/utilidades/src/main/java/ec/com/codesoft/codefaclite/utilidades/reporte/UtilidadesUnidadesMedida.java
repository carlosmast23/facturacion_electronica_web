/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.utilidades.reporte;

/**
 *
 * @author DellWin10
 */
public abstract class UtilidadesUnidadesMedida {
    
    public static final float COEFICIENTE_CM_A_IN=2.54f;
    
    public static float convertirPixelesACm(int pixeles,int ppp)
    {
        return (float)(pixeles*COEFICIENTE_CM_A_IN)/(float)(ppp);
    }
    
    public static float convertirCmAPixeles(float cm,int ppp)
    {
        return (float)(cm*ppp)/(float)(COEFICIENTE_CM_A_IN);
    }
    
}
