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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private List<Integer> valeursDuCapteur;

    private Logger logger = Logger.getLogger("");

    @BeforeEach
    public void setUp() {
        logger.setLevel(Level.INFO);
        valeursDuCapteur = new ArrayList<>();

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
            valeursDuCapteur.add(i + 1);
            Thread.sleep(500);
        }

        scheduler.awaitTermination(5L, TimeUnit.SECONDS);

        logger.info("Résultat NoStrategy Afficheur1 : " + afficheur1.toString());
        logger.info("Résultat NoStrategy Afficheur2 : " + afficheur2.toString());
        logger.info("Résultat NoStrategy Afficheur3 : " + afficheur3.toString());

        List<Integer> historicAfficheur1 = afficheur1.getHistoric();
        List<Integer> historicAfficheur2 = afficheur2.getHistoric();
        List<Integer> historicAfficheur3 = afficheur3.getHistoric();

        assertTrue(valeursDuCapteur.containsAll(historicAfficheur1));
        assertTrue(valeursDuCapteur.containsAll(historicAfficheur2));
        assertTrue(valeursDuCapteur.containsAll(historicAfficheur3));

        assertFalse(historicAfficheur1.isEmpty());
        assertFalse(historicAfficheur2.isEmpty());
        assertFalse(historicAfficheur3.isEmpty());
    }

    @Test
    public void TestDiffusionAtomique() throws InterruptedException {
        capteur.setStrategy(Strategy.DiffusionAtomique);

        for (int i = 0; i < 10; i++) {
            capteur.tick();
            valeursDuCapteur.add(i + 1);
            Thread.sleep(500);
        }

        scheduler.awaitTermination(10L, TimeUnit.SECONDS);

        logger.info("Résultat DiffusionAtomique Afficheur1 : " + afficheur1.toString());
        logger.info("Résultat DiffusionAtomique Afficheur2 : " + afficheur2.toString());
        logger.info("Résultat DiffusionAtomique Afficheur3 : " + afficheur3.toString());

        List<Integer> historicAfficheur1 = afficheur1.getHistoric();
        List<Integer> historicAfficheur2 = afficheur2.getHistoric();
        List<Integer> historicAfficheur3 = afficheur3.getHistoric();

        assertTrue(valeursDuCapteur.containsAll(historicAfficheur1));
        assertTrue(valeursDuCapteur.containsAll(historicAfficheur2));
        assertTrue(valeursDuCapteur.containsAll(historicAfficheur3));

        assertIterableEquals(valeursDuCapteur, historicAfficheur1);
        assertIterableEquals(valeursDuCapteur, historicAfficheur2);
        assertIterableEquals(valeursDuCapteur, historicAfficheur3);
    }

    @Test
    public void TestDiffusionSequentielle() throws InterruptedException {
        capteur.setStrategy(Strategy.DiffusionSequentielle);

        for (int i = 0; i < 10; i++) {
            capteur.tick();
            valeursDuCapteur.add(i + 1);
            Thread.sleep(500);
        }

        scheduler.awaitTermination(10L, TimeUnit.SECONDS);

        logger.info("Résultat DiffusionSequentielle Afficheur1 : " + afficheur1.toString());
        logger.info("Résultat DiffusionSequentielle Afficheur2 : " + afficheur2.toString());
        logger.info("Résultat DiffusionSequentielle Afficheur3 : " + afficheur3.toString());

        List<Integer> historicAfficheur1 = afficheur1.getHistoric();
        List<Integer> historicAfficheur2 = afficheur2.getHistoric();
        List<Integer> historicAfficheur3 = afficheur3.getHistoric();

        assertTrue(valeursDuCapteur.containsAll(historicAfficheur1));
        assertTrue(valeursDuCapteur.containsAll(historicAfficheur2));
        assertTrue(valeursDuCapteur.containsAll(historicAfficheur3));

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
            valeursDuCapteur.add(i + 1);
            Thread.sleep(500);
        }

        scheduler.awaitTermination(10L, TimeUnit.SECONDS);

        logger.info("Résultat DiffusionEpoque Afficheur1 : " + afficheur1.toString());
        logger.info("Résultat DiffusionEpoque Afficheur2 : " + afficheur2.toString());
        logger.info("Résultat DiffusionEpoque Afficheur3 : " + afficheur3.toString());

        List<Integer> historicAfficheur1 = afficheur1.getHistoric();
        List<Integer> historicAfficheur2 = afficheur2.getHistoric();
        List<Integer> historicAfficheur3 = afficheur3.getHistoric();

        assertTrue(valeursDuCapteur.containsAll(historicAfficheur1));
        assertTrue(valeursDuCapteur.containsAll(historicAfficheur2));
        assertTrue(valeursDuCapteur.containsAll(historicAfficheur3));

        for (int i = 1; i < historicAfficheur1.size(); i++) {
            assertTrue(historicAfficheur1.get(i) > historicAfficheur1.get(i - 1));
        }

        for (int i = 1; i < historicAfficheur2.size(); i++) {
            assertTrue(historicAfficheur1.get(i) > historicAfficheur1.get(i - 1));
        }

        for (int i = 1; i < historicAfficheur2.size(); i++) {
            assertTrue(historicAfficheur1.get(i) > historicAfficheur1.get(i - 1));
        }

        assertTrue(historicAfficheur1.size() == (new HashSet<>(historicAfficheur1)).size());
        assertTrue(historicAfficheur2.size() == (new HashSet<>(historicAfficheur2)).size());
        assertTrue(historicAfficheur3.size() == (new HashSet<>(historicAfficheur3)).size());
    }
}
