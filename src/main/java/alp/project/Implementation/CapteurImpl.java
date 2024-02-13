package alp.project.Implementation;

import java.util.ArrayList;
import java.util.List;

import alp.project.Interface.AlgoDiffusion;
import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class CapteurImpl implements Capteur {
    int value;
    boolean lock;
    AlgoDiffusion algoDiffusion;
    List<ObserveurDeCapteurAsync> observeursDeCapteurAsync;

    public CapteurImpl(Strategy strategy) {
        lock = false;
        observeursDeCapteurAsync = new ArrayList<>();
        setStrategy(strategy);
        value = 0;
    }

    @Override
    public void attach(ObserveurDeCapteurAsync observeurDeCapteurAsync) {
        observeursDeCapteurAsync.add(observeurDeCapteurAsync);
    }

    public void setStrategy(Strategy strategy) {
        switch (strategy) {
            case DiffusionAtomique:
                this.algoDiffusion = new DiffusionAtomique(this);
                break;
            case DiffusionEpoque:
                break;
            case DiffusionSequentielle:
                break;
            default:
                this.algoDiffusion = new DiffusionAtomique(this);
                break;
        }
    }

    @Override
    public List<ObserveurDeCapteurAsync> getObserveurs() {
        return observeursDeCapteurAsync;
    }

    public void lock() {
        lock = true;
    }

    public void unlock() {
        lock = false;
    }

    public boolean isLocked() {
        return lock;
    }

    @Override
    public void tick() {
        value += 1;
        algoDiffusion.nouvelleValeur(value);
    }

    @Override
    public int getValue(ObserveurDeCapteurAsync observeurDeCapteurAsync) {
        return algoDiffusion.getValue(observeurDeCapteurAsync);
    }
}
