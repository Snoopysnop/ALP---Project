package alp.project.Implementation;

import java.util.concurrent.Callable;

import alp.project.Interface.Capteur;

public class Update implements Callable<Void> {
    public Afficheur afficheur;
    public Capteur capteur;

    public Update(Afficheur afficheur, Capteur capteur) {
        this.afficheur = afficheur;
        this.capteur = capteur;
    }

    @Override
    public Void call() {
        afficheur.update(capteur);
        return null;
    }
}
