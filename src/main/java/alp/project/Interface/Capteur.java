package alp.project.Interface;

public interface Capteur {
    // Associe le capteur à un Observeur de Capteur
    void attach(ObserveurDeCapteur observeurDeCapteur);

    // Récupère la valeur associée au Capteur
    int getValue();

    // 
    void tick();
}
