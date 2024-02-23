package alp.project.Implementation;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteur;
import alp.project.Interface.ObserveurDeCapteurAsync;


public class Canal implements ObserveurDeCapteurAsync {
    private ScheduledExecutorService scheduler;
    private ObserveurDeCapteur afficheur;

    @Override
    public void attach(ObserveurDeCapteur afficheur, ScheduledExecutorService scheduler) {
        this.afficheur = afficheur;
        this.scheduler = scheduler;
    }

    @Override
    public Future<Optional<Integer>> getValue(Capteur capteur) {
        GetValue getValue = new GetValue(capteur, this);
        return scheduler.submit(getValue);
    }

    @Override
    public Future<Void> update(Capteur capteur) {
        Update update = new Update(afficheur, capteur);

        // delay pour introduire de la latence
        int delay = (int) (Math.random() * 1000 + 500);
        return scheduler.schedule(update, delay, TimeUnit.MILLISECONDS);
    }
}
