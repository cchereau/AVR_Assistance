/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
https://rom.developpez.com/java-listeners/
*/
package com.avr;

import com.avr.commande.Commande;
import javax.swing.JFrame;
import com.avr.commande.CommandeObserver;
import com.avr.Assistance.Assistance;
import com.avr.voiture.Voiture;
/**
 *
 * @author 362294
 */
public class AV2R /*extends JPanel*/ {

    CommandeObserver actionObserver;
    static Voiture voiture;
    static Commande pilotage;
    static Assistance assistance;

    public AV2R() {
        voiture = new Voiture();
        pilotage = new Commande();
        assistance = new Assistance(voiture);
    }

    public void startAssisance() {
        // ajout du module d'assistance
        assistance.attachObsCommande(pilotage);
        assistance.attacheObsVoiture(voiture);

        // enregistre dans commande l'assiatnce comme destinataire des messages
        pilotage.register(assistance);
        voiture.register(assistance);

        // lancement du Thread de contrôle de la voiture
        voiture.setPriority(Thread.MAX_PRIORITY);
        voiture.start();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
        //creation de la voiture
        Voiture voiture = new Voiture();
        voiture.setPriority(Thread.MAX_PRIORITY);
        voiture.start();

        // creation du module de commande et ajout d'un listner
        Commande pilotage = new Commande();

        // ajout du module d'assistance
        Assistance assistance = new Assistance(voiture);
        assistance.attacheCommande(pilotage);
        assistance.attacheEtatVoiture(voiture);

        // enregistre dans commande l'assiatnce comme destinataire des messages
        pilotage.register(assistance);
        voiture.register(assistance);
         */
        AV2R av2r = new AV2R();
        av2r.startAssisance();

        // creation d'une JFRame
        JFrame mainFrame = new JFrame("Test");
        mainFrame.setVisible(true);
        //mainFrame.add(av2r);
        mainFrame.setSize(500, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addKeyListener(pilotage);
        mainFrame.setFocusable(true);
        mainFrame.setVisible(true);

    }

}
