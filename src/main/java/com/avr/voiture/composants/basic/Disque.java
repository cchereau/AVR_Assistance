/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avr.voiture.composants.basic;

import static java.lang.Math.PI;

/**
 *
 * @author 362294
 */
public class Disque {

    Double rayon;

    public Disque(Double rayon) {
        this.rayon = rayon;
    }

    public Double getRayon() {
        return rayon;
    }

    public void setRayon(Double rayon) {
        this.rayon = rayon;
    }

    public Double getPerimetre() {
        return 2 * PI * rayon;
    }

}
