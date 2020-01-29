package communication;

import felix.Felix;
import felix.controleur.ControleurFelix;
import felix.controleur.VueFelix;
import felix.vue.Fenetre;
import felix.vue.VueConnexion;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class FelixTestConnexionImpossible extends AFelixTest{
    private VueConnexion vueConnexion;
    private ControleurFelix controleurFelix;
    private String IP;
    private String port;

    /**
     * La fenêtre de la vue.
     */
private JFrameOperator fenetre;


    /**
     * Le nom de l'application à tester.
     */
    private static final String APPLICATION = "felix.Felix";

    /**
     * Les paramètres de lancement de l'application.
     */
    private static final String[] PARAMETRES = {""}; // "-b" en mode bouchonné, "" en mode collaboration avec Morix;

private static ClassReference application;


/**
 * Les champs texte pour l'adresse IP.
 */
private JTextFieldOperator texteIP,textePort;

private JTextFieldOperator texteInformation;

/**
 * Le bouton connecter de la vue.
 */
private JButtonOperator boutonConnecter;

@Before
public void setUp() throws Exception
        {
// Fixe les timeouts de Jemmy (http://wiki.netbeans.org/Jemmy_Operators_Environment#Timeouts),
// ici : 3s pour l'affichage d'une frame ou d'un composant (widget),
//       ou une attente de changement d'état (waitText par exemple).
final Integer timeout = 3000;
        JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", timeout);
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);

        FelixTestConnexionImpossible.application= new ClassReference(FelixTestConnexionImpossible.APPLICATION);

        this.controleurFelix = new ControleurFelix();
        Assert.assertNotNull(this.controleurFelix);


        // Création de la vue nécessaire aux tests.
        // La vue s'appuie sur le mock de contrôleur.
        this.vueConnexion = new VueConnexion(this.controleurFelix);
        Assert.assertNotNull(this.vueConnexion);

        // Affichage de la vue et accès à cette vue.
        this.vueConnexion.affiche();
        this.accesVue();
        }

/**
 * Fermeture de la vue connexion.
 *
 * <p>Code exécuté après chaque test.</p>
 *
 * @throws Exception toute exception.
 *
 */
@After
public void tearDown() throws Exception {
// 2 secondes d'observation par suspension du thread (objectif pédagogique).
final Long timeout = Long.valueOf(2000);
        Thread.sleep(timeout);
        if (this.vueConnexion != null) {
            this.vueConnexion.ferme();
        }
}

/**
 * Accès à la vue connexion.
 *
 * <p>Cette méthode s'appuie sur les index des widget pour y accéder.</p>
 *
 * <p>Cette méthode concerne l'accès à la fenêtre de la vue caisse,
 * avec titre adéquat et widgets attendus pour cette vue.</p>
 */
private void accesVue() {
        // Accès à la fenêtre de la vue de connexion (par son titre).
        try {
            this.fenetre = new JFrameOperator(Felix.IHM.getString("FENETRE_CONNEXION_TITRE"));
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("La fenêtre de la vue connexion n'est pas accessible : " + e.getMessage());
        }

        try {
        // Accès au champ de saisie d'une adresse IP(par son nom).
        this.texteIP = new JTextFieldOperator(this.fenetre, Felix.IHM.getString("FENETRE_CONNEXION_NOM_IP"));

        // Accès au champ de saisie du port(par son nom).
        this.textePort = new JTextFieldOperator(this.fenetre, Felix.IHM.getString("FENETRE_CONNEXION_NOM_PORT"));

        this.boutonConnecter = new JButtonOperator(this.fenetre,Felix.IHM.getString("FENETRE_CONNEXION_BOUTON_CONNECTER"));

        }
        catch (TimeoutExpiredException e) {
        Assert.fail("Problème d'accès à un composant de la vue connexion : " + e.getMessage());
        }
    }
    /**
     * Lancement d'une instance de Felix.
     *
     * @throws Exception toute exception.
     */
    private void lanceInstanceFelix() throws Exception
    {
        try {
            // Lancement d'une instance de l'application.
            FelixTestConnexionImpossible.application.startApplication(FelixTestConnexionImpossible.PARAMETRES);
        }
        catch (InvocationTargetException e) {
            Assert.fail("Problème d'invocation de l'application : " + e.getMessage());
            throw e;
        }
        catch (NoSuchMethodException e) {
            Assert.fail("Problème d'accès à la méthode invoquée : " + e.getMessage());
            throw e;
        }

        // Accès à l'interface de l'instance de Monix.
        accesVue();
    }

    @Test
    public void testAchatProduits() throws InterruptedException
    {
        // 1,5 seconde d'observation par suspension du thread
        // entre chaque action (objectif pédagogique).
        final Long timeout = Long.valueOf(2000);
        String erreur = Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE");

        texteIP.clickMouse();

        texteIP.clearText();

        Thread.sleep(timeout);

        texteIP.typeText(Felix.CONFIGURATION.getString("ADRESSE_CHAT"));

        Thread.sleep(timeout);

        textePort.clearText();

        Thread.sleep(timeout);

        textePort.enterText(Felix.CONFIGURATION.getString("PORT_CHAT"));

        Thread.sleep(timeout);

        boutonConnecter.doClick();
    }

    @Test
    public void testAffichageInstanciation() throws InterruptedException
    {
        // 1,5 seconde d'observation par suspension du thread
        // entre chaque action (objectif pédagogique).
        final Long timeout = Long.valueOf(2000);
        String erreur = Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE");

        texteIP.clickMouse();

        texteIP.clearText();

        Thread.sleep(timeout);

        texteIP.typeText("127.0.0.1");

        Thread.sleep(timeout);

        textePort.clearText();

        Thread.sleep(timeout);

        textePort.enterText("12345");

        Thread.sleep(timeout);

        boutonConnecter.doClick();
    }
}


