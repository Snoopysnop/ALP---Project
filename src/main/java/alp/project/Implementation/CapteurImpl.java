package alp.project.Implementation;

import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class CapteurImpl implements Capteur {
    // Value
    int field;
    ObserveurDeCapteurAsync[] observeursDeCapteurAsync;

    @Override
    public void attach(ObserveurDeCapteurAsync[] observeurDeCapteurAsync) {
        this.observeursDeCapteurAsync = observeurDeCapteurAsync;
    }

    @Override
    public int getValue() {
        return field;
    }

    @Override
    public void tick() {
        field += 1;
        for (ObserveurDeCapteurAsync observeurDeCapteurAsync : observeursDeCapteurAsync) {
            observeurDeCapteurAsync.update(this);
        }
    }
}
