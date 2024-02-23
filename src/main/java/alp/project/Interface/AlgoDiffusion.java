package alp.project.Interface;

import java.util.Optional;

public interface AlgoDiffusion {
    // Execute la stratégie à chaque nouvelle valeur de capteur.
    public void execute(int value);

    // Retourne la valeur courante ou rien s'il la valeur a déjà été lue par l'observeur.
    public Optional<Integer> getValue(ObserveurDeCapteurAsync canal);

    // Met à jour les observeurs liés aux canaux.
    public void updateObservers();
}
