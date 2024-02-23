package alp.project.Interface;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public interface ObserveurDeCapteurAsync {
    // Lance le schedule et introduit du delai.
    public Future<Void> update(Capteur capteur);
    
    // Récupère la valeur du capteur.
    public Future<Optional<Integer>> getValue(Capteur capteur);

    // Associe un observeur et un scheduler au canal.
    public void attach(ObserveurDeCapteur afficheur, ScheduledExecutorService scheduler);
}
