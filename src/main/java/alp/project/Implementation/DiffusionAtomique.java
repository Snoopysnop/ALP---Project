package alp.project.Implementation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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

    public void updateObservers() {
        observeursDeCapteurAsync = capteur.getObserveurs();
    }

    public void nouvelleValeur(int value) {
        fields.add(value);

        if (!capteur.isLocked()) {
            // on passe à la valeur suivante
            field = fields.poll();

            // on vérouille le capteur pour qu'on ne puisse pas changer la valeur à lire
            // avant que tous les observeurs ait lu la nouvelle valeur
            capteur.lock();

            // on prévient tous les observeurs
            for (ObserveurDeCapteurAsync observeurDeCapteurAsync : observeursDeCapteurAsync) {
                observeurDeCapteurAsync.update(capteur);
            }
        }
    }

    public int getValue(ObserveurDeCapteurAsync canal) {
        Objects.requireNonNull(canal, "le canal doit être non null");
        this.observeursAyantLu.add(canal);

        int currentField = field;

        if (this.observeursDeCapteurAsync.size() == this.observeursAyantLu.size()) {
            this.capteur.unlock();
            observeursAyantLu.clear();

            if (!fields.isEmpty()) {
                // on passe à la valeur suivante
                field = fields.poll();

                // on vérouille le capteur pour qu'on ne puisse pas changer la valeur à lire
                // avant que tous les observeurs ait lu la nouvelle valeur
                capteur.lock();

                // on prévient tous les observeurs
                for (ObserveurDeCapteurAsync observeurDeCapteurAsync : observeursDeCapteurAsync) {
                    observeurDeCapteurAsync.update(capteur);
                }
            }
        }

        return currentField;
    }
}
