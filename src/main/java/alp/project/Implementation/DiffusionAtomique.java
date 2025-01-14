package alp.project.Implementation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import alp.project.Interface.AlgoDiffusion;
import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class DiffusionAtomique implements AlgoDiffusion {
    int field;
    Queue<Integer> fields = new LinkedList<>();
    private Capteur capteur;
    private List<ObserveurDeCapteurAsync> observeursDeCapteurAsync;
    private List<ObserveurDeCapteurAsync> observeursAyantLu;

    public DiffusionAtomique(Capteur capteur) {
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
        fields.add(value);

        if (!capteur.isLocked())
            nextValue();
    }

    @Override
    public Optional<Integer> getValue(ObserveurDeCapteurAsync canal) {
        this.observeursAyantLu.add(canal);

        int currentField = field;

        if (this.observeursDeCapteurAsync.size() == this.observeursAyantLu.size()) {
            // si tous les capteurs ont lu, on débloque le capteur
            this.capteur.unlock();
            observeursAyantLu.clear();

            // s'il nous reste des valeurs dans la file, on passe à la suivante
            if (!fields.isEmpty())
                nextValue();
        }

        return Optional.of(currentField);
    }

    private void nextValue() {
        // on passe à la valeur suivante
        field = fields.poll();

        // on vérouille le capteur pour qu'on ne puisse pas changer la valeur à lire
        // avant que tous les observeurs aient lu la nouvelle valeur
        capteur.lock();

        // on prévient tous les observeurs
        for (ObserveurDeCapteurAsync observeurDeCapteurAsync : observeursDeCapteurAsync) {
            observeurDeCapteurAsync.update(capteur);
        }
    }
}
