import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import alp.project.Interface.ObserveurDeCapteur;
import alp.project.Interface.ObserveurDeCapteurAsync;

public class Tests {
    private final int NB_AFFICHEUR = 3;

    private Capteur capteur;
    private ObserveurDeCapteurAsync canal1, canal2, canal3;
    private ObserveurDeCapteur afficheur1, afficheur2, afficheur3;
    private ScheduledExecutorService scheduler;

    @BeforeEach
    public void setUp() {
        capteur = new CapteurImpl(Strategy.DiffusionNoStrategy);

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
        capteur.setStrategy(Strategy.DiffusionNoStrategy);

        for (int i = 0; i < 10; i++) {
            capteur.tick();
            Thread.sleep(500);
        }

        scheduler.awaitTermination(5L, TimeUnit.SECONDS);

        List<Integer> historicAfficheur1 = afficheur1.getHistoric();
        List<Integer> historicAfficheur2 = afficheur2.getHistoric();
        List<Integer> historicAfficheur3 = afficheur3.getHistoric();

        assertFalse(historicAfficheur1.isEmpty());
        assertFalse(historicAfficheur2.isEmpty());
        assertFalse(historicAfficheur3.isEmpty());
    }

    @Test
    public void TestDiffusionAtomique() throws InterruptedException {
        capteur.setStrategy(Strategy.DiffusionAtomique);
        List<Integer> expectedResults = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            capteur.tick();
            expectedResults.add(i + 1);
            Thread.sleep(500);
        }

        scheduler.awaitTermination(10L, TimeUnit.SECONDS);

        List<Integer> historicAfficheur1 = afficheur1.getHistoric();
        List<Integer> historicAfficheur2 = afficheur2.getHistoric();
        List<Integer> historicAfficheur3 = afficheur3.getHistoric();
        assertIterableEquals(expectedResults, historicAfficheur1);
        assertIterableEquals(expectedResults, historicAfficheur2);
        assertIterableEquals(expectedResults, historicAfficheur3);
    }

    @Test
    public void TestDiffusionSequentielle() throws InterruptedException {
        capteur.setStrategy(Strategy.DiffusionSequentielle);

        for (int i = 0; i < 10; i++) {
            capteur.tick();
            Thread.sleep(500);
        }

        scheduler.awaitTermination(10L, TimeUnit.SECONDS);

        List<Integer> historicAfficheur1 = afficheur1.getHistoric();
        List<Integer> historicAfficheur2 = afficheur2.getHistoric();
        List<Integer> historicAfficheur3 = afficheur3.getHistoric();
        assertIterableEquals(historicAfficheur1, historicAfficheur2);
        assertIterableEquals(historicAfficheur1, historicAfficheur3);

        Set<Integer> noDuplicates = new HashSet<>(historicAfficheur1);
        assertTrue(historicAfficheur1.size() == noDuplicates.size());
    }

    @Test
    public void TestDiffusionEpoque() throws InterruptedException {
        capteur.setStrategy(Strategy.DiffusionEpoque);

        for (int i = 0; i < 10; i++) {
            capteur.tick();
            Thread.sleep(500);
        }

        scheduler.awaitTermination(10L, TimeUnit.SECONDS);

        List<Integer> historicAfficheur1 = afficheur1.getHistoric();
        List<Integer> historicAfficheur2 = afficheur2.getHistoric();
        List<Integer> historicAfficheur3 = afficheur3.getHistoric();

        for (int i = 1; i < historicAfficheur1.size(); i++) {
            assertTrue(historicAfficheur1.get(i) > historicAfficheur1.get(i - 1));
        }

        for (int i = 1; i < historicAfficheur2.size(); i++) {
            assertTrue(historicAfficheur1.get(i) > historicAfficheur1.get(i - 1));
        }

        for (int i = 1; i < historicAfficheur2.size(); i++) {
            assertTrue(historicAfficheur1.get(i) > historicAfficheur1.get(i - 1));
        }

        Set<Integer> noDuplicates1 = new HashSet<>(historicAfficheur1);
        Set<Integer> noDuplicates2 = new HashSet<>(historicAfficheur2);
        Set<Integer> noDuplicates3 = new HashSet<>(historicAfficheur3);
        assertTrue(historicAfficheur1.size() == noDuplicates1.size());
        assertTrue(historicAfficheur2.size() == noDuplicates2.size());
        assertTrue(historicAfficheur3.size() == noDuplicates3.size());
    }
}
