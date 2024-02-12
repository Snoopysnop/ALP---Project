package alp.project.Interface;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import alp.project.Implementation.Afficheur;

public interface ObserveurDeCapteurAsync {
    public Future<Void> update(Capteur capteur);

    public Future<Integer> getValue(Capteur capteur);

    public void attach(Afficheur afficheur, ScheduledExecutorService scheduler);
}
