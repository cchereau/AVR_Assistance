/** ****************************************************************************
 * Class de gestion des commandes et des actions à suivres
 * Concernant psotion roue nous sommes dans le sens anti-horraire
 * +1 signifie que l'on tourne à gauche
 * -1 signifie que l'on troune à droite
 ****************************************************************************** */
// Le 16/08/2018 : Inspiration de la source https://www.journaldev.com/1739/observer-design-pattern-in-java
// pour utiliser la gestion des event et des listnets 
/** **************************************************************************** */
package com.avr.commande;

import com.avr.Constante;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chris
 */
public class Commande implements KeyListener, CommandeRegister {

    private Integer acceleration, positionRoue;
    private Constante.CTE_COMMANDE_VOITURE actionVoiture;
    private final List<CommandeObserver> observers;
    private final Object MUTEX = new Object();
    private boolean changed;

    public Commande() {
        acceleration = 0;
        positionRoue = 0;
        this.observers = new ArrayList<>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (KeyEvent.getKeyText(e.getKeyCode())) {
            case "Haut":
                acceleration += 1;
                actionVoiture = Constante.CTE_COMMANDE_VOITURE.ACCELERATION;
                System.out.println("Accélération compteur : " + acceleration.toString());
                break;
            case "Bas":
                acceleration -= 1;
                actionVoiture = Constante.CTE_COMMANDE_VOITURE.DECELERATION;
                System.out.println("Déceleration compteur : " + acceleration.toString());
                break;
            case "Gauche":
                positionRoue += 1;
                System.out.println("Gauche Pos Roue: " + positionRoue.toString());
                actionVoiture = Constante.CTE_COMMANDE_VOITURE.TURN_LEFT;
                break;
            case "Droite":
                positionRoue += -1;
                System.out.println("Droite Pos Roue: " + positionRoue.toString());
                actionVoiture = Constante.CTE_COMMANDE_VOITURE.TURN_RIGHT;
                break;
            case "S":
                acceleration = 0;
                System.out.println("Stop: " + positionRoue.toString());
                actionVoiture = Constante.CTE_COMMANDE_VOITURE.STOP;
                break;
            case "A":
                positionRoue = 0;
                System.out.println("Roue alignée " + positionRoue.toString());
                actionVoiture = Constante.CTE_COMMANDE_VOITURE.TOUT_DROIT;
                break;
        }
        postMessage(actionVoiture);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void register(CommandeObserver obj) {
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
    public void unregister(CommandeObserver obj) {
        synchronized (MUTEX) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {
        List<CommandeObserver> observersLocal;
        //synchronization is used to make sure any observer registered after message is received is not notified
        synchronized (MUTEX) {
            if (!changed) {
                return;
            }
            observersLocal = new ArrayList<>(this.observers);
            this.changed = false;
        }
        observersLocal.forEach((obj) -> {
            obj.setCommande();
        });

    }

    @Override
    public Object getUpdate(CommandeObserver obj) {
        return this.actionVoiture;
    }

    //method to post message to the topic
    public void postMessage(Constante.CTE_COMMANDE_VOITURE pAction) {
        this.actionVoiture = pAction;
        this.changed = true;
        notifyObservers();
    }

}
