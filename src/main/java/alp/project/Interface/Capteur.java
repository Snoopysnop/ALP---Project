package alp.project.Interface;

import java.util.List;

import alp.project.Implementation.Strategy;

public interface Capteur {
    // Associe le capteur à un Observeur de Capteur
    void attach(ObserveurDeCapteurAsync observeurDeCapteurAsync);

    List<ObserveurDeCapteurAsync> getObserveurs();

    void tick();

    void lock();

    void unlock();

    boolean isLocked();

    int getValue(ObserveurDeCapteurAsync observeurDeCapteurAsync);

    void setStrategy(Strategy strategy);
}
