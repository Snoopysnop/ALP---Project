package alp.project.Implementation;

import java.util.Optional;
import java.util.concurrent.Callable;

import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class GetValue implements Callable<Optional<Integer>> {
    public Capteur capteur;
    public ObserveurDeCapteurAsync observeurDeCapteurAsync;

    public GetValue(Capteur capteur, ObserveurDeCapteurAsync observeurDeCapteurAsync) {
        this.capteur = capteur;
        this.observeurDeCapteurAsync = observeurDeCapteurAsync;
    }

    @Override
    public Optional<Integer> call() {
        return capteur.getValue(observeurDeCapteurAsync);
    }
}
