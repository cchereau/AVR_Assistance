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
public interface VoitureRegister {
    //methods to register and unregister observers

    public void register(VoitureObserver obj);

    public void unregister(VoitureObserver obj);

    //method to notify observers of change
    public void notifyObservers();

    //method to get updates from Action
    public Object getUpdate(VoitureObserver obj);

}
