package alp.project.Interface;

import java.util.List;
import java.util.Optional;

import alp.project.Implementation.Strategy;

public interface Capteur {
    // Associe le capteur à un Observeur de Capteur.
    void attach(ObserveurDeCapteurAsync observeurDeCapteurAsync);

    // Récupère la liste des observeurs associés au capteur.
    List<ObserveurDeCapteurAsync> getObserveurs();

    // Passe à la valeur suivante.
    void tick();

    // Bloque le capteur, cela empêche la lecture de nouvelle valeur tant que la capteur est bloqué.
    void lock();

    // Débloque le capteur.
    void unlock();

    // Retourne vrai si le capteur est bloqué?
    boolean isLocked();

    Optional<Integer> getValue(ObserveurDeCapteurAsync observeurDeCapteurAsync);

    void setStrategy(Strategy strategy);
}
