package alp.project.Implementation;

import java.util.List;
import java.util.Optional;

import alp.project.Interface.AlgoDiffusion;
import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class DiffusionNoStrategy implements AlgoDiffusion {
    int field;
    private Capteur capteur;
    private List<ObserveurDeCapteurAsync> observeursDeCapteurAsync;

    public DiffusionNoStrategy(Capteur capteur) {
        this.capteur = capteur;
        observeursDeCapteurAsync = capteur.getObserveurs();
    }

    @Override
    public void updateObservers() {
        observeursDeCapteurAsync = capteur.getObserveurs();
    }

    @Override
    public void execute(int value) {
            // on passe à la valeur suivante
            field = value;
            
            // on prévient tous les observeurs
            for (ObserveurDeCapteurAsync observeurDeCapteurAsync : observeursDeCapteurAsync) {
                observeurDeCapteurAsync.update(capteur);
            }
    }

    @Override
    public Optional<Integer> getValue(ObserveurDeCapteurAsync canal) {
        return Optional.of(field);
    }
}
