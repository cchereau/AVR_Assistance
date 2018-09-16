/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.av2r_assistance.voiture.composants;
import com.mycompany.av2r_assistance.Constante;
/**
 *
 * @author 362294
 */
public class Thermometre implements Composants
{

    private final int seuilNormal;
    private final int seuilChaud;
    private final int seuilSurchauffe;
    private int temperature;
    
    public Thermometre(int seuilNormal, int seuilChaud, int seuilSurchauffe)
    {
        this.seuilNormal=seuilNormal;
        this.seuilChaud=seuilChaud;
        this.seuilSurchauffe=seuilSurchauffe;
    }
    
    public Constante.CTE_ETAT_TEMPERATURE getEtatTemperature()
    {
        if(temperature<seuilNormal)
            return Constante.CTE_ETAT_TEMPERATURE.FROID;
        else if (temperature<seuilChaud)
            return Constante.CTE_ETAT_TEMPERATURE.NORMAL;
        else if(temperature<seuilSurchauffe)
            return Constante.CTE_ETAT_TEMPERATURE.CHAUD;
        else return Constante.CTE_ETAT_TEMPERATURE.SURCHAUFFE;
    }

    @Override
    public void setAction(int valeur) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getValeur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
