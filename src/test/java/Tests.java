import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import alp.project.Implementation.Afficheur;
import alp.project.Implementation.Canal;
import alp.project.Implementation.CapteurImpl;
import alp.project.Implementation.Strategy;
import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class Tests {
    private final int NB_AFFICHEUR = 3;

    private Capteur capteur;
    private ObserveurDeCapteurAsync canal1, canal2, canal3;
    private Afficheur afficheur1, afficheur2, afficheur3;
    private ScheduledExecutorService scheduler;

    @BeforeEach
    public void setUp() {
        capteur = new CapteurImpl(Strategy.NoStrategy);

        canal1 = new Canal();
        capteur.attach(canal1);
        afficheur1 = new Afficheur(canal1);

        canal2 = new Canal();
        capteur.attach(canal2);
        afficheur2 = new Afficheur(canal2);

        canal3 = new Canal();
        capteur.attach(canal3);
        afficheur3 = new Afficheur(canal3);

        int nbThread = 3 * NB_AFFICHEUR + 3;
        scheduler = Executors.newScheduledThreadPool(nbThread);

        canal1.attach(afficheur1, scheduler);
        canal2.attach(afficheur2, scheduler);
        canal3.attach(afficheur3, scheduler);
    }

    @Test
    public void TestNoStrategy() throws InterruptedException {
        capteur.setStrategy(Strategy.NoStrategy);

        for (int i = 0; i < 10; i++) {
            capteur.tick();
            Thread.sleep(500);
        }

        scheduler.awaitTermination(10L, TimeUnit.SECONDS);

        System.out.println(afficheur1.toString());
        System.out.println(afficheur2.toString());
        System.out.println(afficheur3.toString());
    }

    @Test
    public void TestDiffusionAtomique() throws InterruptedException {
        capteur.setStrategy(Strategy.DiffusionAtomique);

        for (int i = 0; i < 10; i++) {
            capteur.tick();
            Thread.sleep(500);
        }

        scheduler.awaitTermination(10L, TimeUnit.SECONDS);

        System.out.println(afficheur1.toString());
        System.out.println(afficheur2.toString());
        System.out.println(afficheur3.toString());
    }
}