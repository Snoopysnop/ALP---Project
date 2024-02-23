package alp.project.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alp.project.Interface.AlgoDiffusion;
import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class DiffusionSequentielle implements AlgoDiffusion {
    int field;
    private Capteur capteur;
    private List<ObserveurDeCapteurAsync> observeursDeCapteurAsync;
    private List<ObserveurDeCapteurAsync> observeursAyantLu;

    public DiffusionSequentielle(Capteur capteur) {
        this.capteur = capteur;
        observeursDeCapteurAsync = capteur.getObserveurs();
        observeursAyantLu = new ArrayList<>();
    }

    public void updateObservers() {
        observeursDeCapteurAsync = capteur.getObserveurs();
    }

    public void execute(int value) {
        if (!capteur.isLocked()) {
            // on passe à la valeur suivante
            field = value;

            // on vérouille le capteur pour qu'on ne puisse pas changer la valeur à lire
            // avant que tous les observeurs aient lu la nouvelle valeur
            capteur.lock();

            // on prévient tous les observeurs
            for (ObserveurDeCapteurAsync observeurDeCapteurAsync : observeursDeCapteurAsync) {
                observeurDeCapteurAsync.update(capteur);
            }
        }
    }

    @Override
    public Optional<Integer> getValue(ObserveurDeCapteurAsync canal) {
        this.observeursAyantLu.add(canal);

        int currentField = field;

        // si tous les capteurs ont lu, on débloque le capteur
        if (this.observeursDeCapteurAsync.size() == this.observeursAyantLu.size()) {
            this.capteur.unlock();
            observeursAyantLu.clear();
        }
        return Optional.of(currentField);
    }
}
