package alp.project.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alp.project.Interface.AlgoDiffusion;
import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class DiffusionEpoque implements AlgoDiffusion {
    int field;
    private Capteur capteur;
    private List<ObserveurDeCapteurAsync> observeursDeCapteurAsync;
    private List<ObserveurDeCapteurAsync> observeursAyantLu;

    public DiffusionEpoque(Capteur capteur) {
        this.capteur = capteur;
        observeursDeCapteurAsync = capteur.getObserveurs();
        observeursAyantLu = new ArrayList<>();
    }

    @Override
    public void updateObservers() {
        observeursDeCapteurAsync = capteur.getObserveurs();
    }

    @Override
    public void execute(int value) {
            // on passe à la valeur suivante
            field = value;
            
            observeursAyantLu.clear();
            
            // on prévient tous les observeurs
            for (ObserveurDeCapteurAsync observeurDeCapteurAsync : observeursDeCapteurAsync) {
                observeurDeCapteurAsync.update(capteur);
            }
    }

    @Override
    public Optional<Integer> getValue(ObserveurDeCapteurAsync canal) {        
        if(!observeursAyantLu.contains(canal)){
            observeursAyantLu.add(canal);
            return Optional.of(field);
        }

        // si la valeur courante a déjà été lue par le capteur, on ne retourne pas de valeur
        return Optional.empty();
    }
}
