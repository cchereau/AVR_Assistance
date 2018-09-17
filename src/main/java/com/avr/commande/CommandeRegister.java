/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avr.commande;

/**
 *
 * @author 362294
 */
public interface CommandeRegister {

    //methods to register and unregister observers
    public void register(CommandeObserver obj);

    public void unregister(CommandeObserver obj);

    //method to notify observers of change
    public void notifyObservers();

    //method to get updates from Action
    public Object getUpdate(CommandeObserver obj);

}
