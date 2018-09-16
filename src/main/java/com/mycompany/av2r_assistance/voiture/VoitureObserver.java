/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.av2r_assistance.voiture;

/**
 *
 * @author 362294
 */
public interface VoitureObserver {
    //method to update the observer, used by subject

    public void setEtatVoiture();

    //attach with subject to observe
    public void attacheObsVoiture(VoitureRegister sub);
}
