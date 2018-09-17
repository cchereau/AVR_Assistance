/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avr.voiture.composants;

import com.avr.Constante;

/**
 *
 * @author 362294
 */
public class Direction implements Composants {

    private int positionDirection = 0;

    public Direction() {
    }

    public Constante.CTE_ETAT_DIRECTION getEtatDirection() {
        if (positionDirection == 0) 
            return Constante.CTE_ETAT_DIRECTION.NEUTRE;
        else if (positionDirection<0)
            return Constante.CTE_ETAT_DIRECTION.TURN_LEFT;
        else if(positionDirection>0)
           return Constante.CTE_ETAT_DIRECTION.TURN_RIGHT;
         else 
            return Constante.CTE_ETAT_DIRECTION.UNDEFINED;
    }

    @Override
    public void setAction(int valeur) {
        positionDirection+=valeur;
    }

    @Override
    public int getValeur() {
        return positionDirection;
    }

}
