package alp.project.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void setStrategy(Strategy strategy) {
        switch (strategy) {
            case DiffusionAtomique:
                this.algoDiffusion = new DiffusionAtomique(this);
                break;
            case DiffusionEpoque:
                this.algoDiffusion = new DiffusionEpoque(this);
                break;
            case DiffusionSequentielle:
                this.algoDiffusion = new DiffusionSequentielle(this);
                break;
            default:
                this.algoDiffusion = new DiffusionNoStrategy(this);
                break;
        }
    }

    @Override
    public List<ObserveurDeCapteurAsync> getObserveurs() {
        return observeursDeCapteurAsync;
    }

    @Override
    public void lock() {
        lock = true;
    }

    @Override
    public void unlock() {
        lock = false;
    }

    @Override
    public boolean isLocked() {
        return lock;
    }

    @Override
    public void tick() {
        value ++;
        algoDiffusion.execute(value);
    }

    @Override
    public Optional<Integer> getValue(ObserveurDeCapteurAsync observeurDeCapteurAsync) {
        return algoDiffusion.getValue(observeurDeCapteurAsync);
    }
}
