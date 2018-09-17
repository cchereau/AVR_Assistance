/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avr.Assistance;

import com.avr.Constante;
import com.avr.voiture.Voiture;
import com.avr.commande.CommandeObserver;
import com.avr.commande.CommandeRegister;
import com.avr.voiture.VoitureRegister;
import com.avr.voiture.VoitureObserver;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chris
 */
public class Assistance implements CommandeObserver, VoitureObserver, AssistanceRegister {

    private final Voiture voiture;
    private CommandeRegister commande;
    private VoitureRegister etat;
    private Constante.CTE_AV2R_VOITURE assitanceVoiture;

    private final List<AssistanceObserver> observers;
    private final Object MUTEX = new Object();
    private boolean changed;

    public Assistance(Voiture voiture) {
        this.voiture = voiture;
        this.observers = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SetCOmmande permet d'affecter à la voiture une commande recu et vérifie en fonction de l'état de la voiture
    // jusu'à ce que la commande soit toalement effectuée
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void setCommande() {
        Constante.CTE_COMMANDE_VOITURE commandeVoiture = (Constante.CTE_COMMANDE_VOITURE) commande.getUpdate(this);

        switch (commandeVoiture) {
            case ACCELERATION:
                voiture.addActionVoiture(Constante.CTE_ACTION_VOITURE.VITESSE, +1);
                System.out.println("Consuming message ACTION:Acceleration");
                break;
            case DECELERATION:
                voiture.addActionVoiture(Constante.CTE_ACTION_VOITURE.VITESSE, -1);
                System.out.println("Consuming message ACTION:Freinage");
                break;

            case TURN_LEFT:
                voiture.addActionVoiture(Constante.CTE_ACTION_VOITURE.DIRECTION, +1);
                System.out.println("Consuming message ACTION:Tourner Gauche");
                break;

            case TURN_RIGHT:
                voiture.addActionVoiture(Constante.CTE_ACTION_VOITURE.DIRECTION, -1);
                System.out.println("Consuming message ACTION:Tourner Droite");
                break;

            case TOUT_DROIT:
                voiture.addActionVoiture(Constante.CTE_ACTION_VOITURE.DIRECTION_NEUTRE, 0);
                System.out.println("Consuming message ACTION:Roue Alignée");
                break;

            case STOP:
                voiture.addActionVoiture(Constante.CTE_ACTION_VOITURE.VITESSE_NULL, 0);
                System.out.println("Consuming message ACTION:STOP");
                break;
        }
    }

    @Override
    public void attachObsCommande(CommandeRegister commande) {
        this.commande = commande;
    }

    @Override
    public void attacheObsVoiture(VoitureRegister etat) {
        this.etat = etat;
    }

    @Override
    public void setEtatVoiture() {
        System.out.println("av2r.Assistance.setEtatVoiture()");
    }

    @Override
    public void register(AssistanceObserver obj) {
        if (obj == null) {
            throw new NullPointerException("Null Observer");
        }
        synchronized (MUTEX) {
            if (!observers.contains(obj)) {
                observers.add(obj);
            }
        }
    }

    @Override
    public void unregister(AssistanceObserver obj) {
        synchronized (MUTEX) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {
        List<AssistanceObserver> observersLocal;
        //synchronization is used to make sure any observer registered after message is received is not notified
        synchronized (MUTEX) {
            if (!changed) {
                return;
            }
            observersLocal = new ArrayList<>(this.observers);
            this.changed = false;
        }
        observersLocal.forEach((obj) -> {
            obj.setAssistance();
        });
    }

    @Override
    public Object getUpdate(AssistanceObserver obj) {
        return this.assitanceVoiture;
    }

}
