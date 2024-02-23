package alp.project.Interface;

import java.util.List;

public interface ObserveurDeCapteur {
    // Récupère la valeur du canal.
    public void update(Capteur capteur);

    // Retourne la liste des valeurs lues par l'afficheur.
    public List<Integer> getHistoric();
}
