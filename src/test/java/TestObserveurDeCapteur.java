import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import alp.project.Implementation.Afficheur;
import alp.project.Implementation.Canal;
import alp.project.Implementation.CapteurImpl;
import alp.project.Implementation.Update;
import alp.project.Interface.Capteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class TestObserveurDeCapteur {
    private Capteur capteur;
    private ObserveurDeCapteurAsync canal1, canal2, canal3;
    private Afficheur afficheur1, afficheur2, afficheur3;
    private ScheduledExecutorService scheduler;

    @BeforeEach
    public void setUp() {
        canal1 = new Canal();
        afficheur1 = new Afficheur(canal1);

        canal2 = new Canal();
        afficheur2 = new Afficheur(canal2);

        canal3 = new Canal();
        afficheur3 = new Afficheur(canal3);

        capteur = new CapteurImpl();

        ObserveurDeCapteurAsync[] observeursDeCapteurAsync = { canal1, canal2, canal3 };
        int nbThread = 3 * observeursDeCapteurAsync.length + 3;
        scheduler = Executors.newScheduledThreadPool(nbThread);

        canal1.attach(afficheur1, scheduler);
        canal2.attach(afficheur2, scheduler);
        canal3.attach(afficheur3, scheduler);

        capteur.attach(observeursDeCapteurAsync);
    }

    @Test
    public void Test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            capteur.tick();
            Thread.sleep(500);
        }

        scheduler.awaitTermination(5L, TimeUnit.SECONDS);

        System.out.println(afficheur1.toString());
        System.out.println(afficheur2.toString());
        System.out.println(afficheur3.toString());
    }
}
