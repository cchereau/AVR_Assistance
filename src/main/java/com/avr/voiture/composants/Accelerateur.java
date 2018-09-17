/* 

 */
package com.avr.voiture.composants;

import com.avr.Constante;

/**
 *
 * @author 362294
 */
public class Accelerateur implements Composants {

    int acceletration = 0;

    public Constante.CTE_ETAT_ACCELERATEUR getEtatAccelration() {
        if (acceletration == 0) {
            return Constante.CTE_ETAT_ACCELERATEUR.NEUTRE;
        } else if (acceletration < 0) {
            return Constante.CTE_ETAT_ACCELERATEUR.DECELERATION;
        } else if (acceletration > 0) {
            return Constante.CTE_ETAT_ACCELERATEUR.ACCELERATION;
        } else {
            return Constante.CTE_ETAT_ACCELERATEUR.UNDEFINED;
        }
    }

    @Override
    public void setAction(int valeur) {
        acceletration += valeur;
    }

    @Override
    public int getValeur() {
        return acceletration;
    }

}
