package alp.project.Implementation;

import java.util.concurrent.Callable;

import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteur;

public class Update implements Callable<Void> {
    public ObserveurDeCapteur afficheur;
    public Capteur capteur;

    public Update(ObserveurDeCapteur afficheur, Capteur capteur) {
        this.afficheur = afficheur;
        this.capteur = capteur;
    }

    @Override
    public Void call() {
        afficheur.update(capteur);
        return null;
    }
}
