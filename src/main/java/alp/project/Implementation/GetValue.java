package alp.project.Implementation;

import java.util.concurrent.Callable;

import alp.project.Interface.Capteur;

public class GetValue implements Callable<Integer> {
    public Capteur capteur;

    public GetValue(Capteur capteur) {
        this.capteur = capteur;
    }

    @Override
    public Integer call() {
        return capteur.getValue();
    }
}
