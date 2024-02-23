package alp.project.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class Afficheur implements ObserveurDeCapteur {
    private int field;
    private ObserveurDeCapteurAsync canal;
    private List<Integer> historic;

    public Afficheur(ObserveurDeCapteurAsync canal1) {
        this.canal = canal1;
        historic = new ArrayList<>();
    }

    @Override
    public void update(Capteur capteur) {
        try {
            Optional<Integer> result = canal.getValue(capteur).get(100L,TimeUnit.MILLISECONDS);
            if(result.isPresent()) {
                field = result.get();
                historic.add(field);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getHistoric() {
        return historic;
    }

    @Override
    public String toString() {
        int sizeList = historic.size();

        if(sizeList == 0) {
            return "list is empty";
        }

        String res = "[";
        for (int i = 0; i < sizeList - 1; i++) {
            res += historic.get(i) + ",";
        }
        res += historic.get(sizeList - 1) + "]";
        return res;
    }
}
