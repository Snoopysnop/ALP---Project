package alp.project.Implementation;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;


public class Canal implements ObserveurDeCapteurAsync {
    private ScheduledExecutorService scheduler;
    private Afficheur afficheur;

    public void attach(Afficheur afficheur, ScheduledExecutorService scheduler) {
        this.afficheur = afficheur;
        this.scheduler = scheduler;
    }

    @Override
    public Future<Integer> getValue(Capteur capteur) {
        GetValue getValue = new GetValue(capteur, this);
        return scheduler.submit(getValue);
    }

    @Override
    public Future<Void> update(Capteur capteur) {
        Update update = new Update(afficheur, capteur);
        int delay = (int) (Math.random() * 1000 + 500);
        return scheduler.schedule(update, delay, TimeUnit.MILLISECONDS);
    }
}
