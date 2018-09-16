/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.av2r_assistance.voiture.composants;

import com.mycompany.av2r_assistance.voiture.composants.basic.Disque;

/**
 *
 * @author 362294
 */
public class Roue extends Disque {

    private Double RPM = new Double(0);  // Tour par minute 

    public Roue(Double rayon) {
        super(rayon);
    }

    public Double getRPM() {
        return RPM;
    }

    public void setRPM(Double RPM) {
        this.RPM = RPM;
    }

    public Boolean isRoueEnMouvement() 
    {
        return getRPM() != 0;
    }
}
