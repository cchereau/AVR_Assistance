/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avr.Assistance;

/**
 *
 * @author 362294
 */
public interface AssistanceObserver {
    //method to update the observer, used by subject

    public void setAssistance();

    //attach with subject to observe
    public void attacheObsAssistance(AssistanceRegister sub);
}
