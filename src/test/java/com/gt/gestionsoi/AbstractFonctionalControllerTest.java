package com.gt.gestionsoi;

import com.gt.gestionsoi.controller.*;
import com.gt.gestionsoi.repository.*;
import com.gt.gestionsoi.service.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe de base des classes de test pour les contrôleurs
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 */
public abstract class AbstractFonctionalControllerTest extends AbstractControllerTest {

    @Autowired
    protected JournalRepository journalRepository;
    @Autowired
    protected IJournalService journalService;
    @Autowired
    protected IPrevisionService previsionService;
    @Autowired
    protected PrevisionRepository previsionRepository;
    @Autowired
    protected VisionRepository visionRepository;
    @Autowired
    protected IVisionService visionService;
    @Autowired
    protected ProjetRepository projetRepository;
    @Autowired
    protected IProjetService projetService;
    @Autowired
    protected ProcessusRepository processusRepository;
    @Autowired
    protected IProcessusService processusService;
    @Autowired
    protected PrincipeValeurRepository principeValeurRepository;
    @Autowired
    protected IPrincipeValeurService principeValeurService;
    @Autowired
    protected ObjectifRepository objectifRepository;
    @Autowired
    protected IObjectifService objectifService;
    @Autowired
    protected CategorieRepository categorieRepository;
    @Autowired
    protected ICategorieService categorieService;
    @Autowired
    protected IProfilService profilService;
    @Autowired
    protected IUtilisateurService utilisateurService;
    @Autowired
    protected ProfilRepository profilRepository;
    @Autowired
    protected PermissionRepository permissionRepository;
    @Autowired
    protected UtilisateurRepository utilisateurRepository;

    public void setUp() {
        JournalController journalController = new JournalController(journalService);

        PrevisionController previsionController = new PrevisionController(previsionService);

        VisionController visionController = new VisionController(visionService);

        ProjetController projetController = new ProjetController(projetService);

        ProcessusController processusController = new ProcessusController(processusService);

        PrincipeValeurController principeValeurController = new PrincipeValeurController(principeValeurService);

        ObjectifController objectifController = new ObjectifController(objectifService);

        CategorieController categorieController = new CategorieController(categorieService);

        ProfilController profilController = new ProfilController(profilService);
        profilController.setProfilRepository(profilRepository);
        profilController.setPermissionRepository(permissionRepository);

        UtilisateurController utilisateurController = new UtilisateurController(utilisateurService);
        utilisateurController.setUtilisateurRepository(utilisateurRepository);

        super.setup(profilController, utilisateurController, categorieController, objectifController,
                principeValeurController, processusController, projetController, visionController,
                journalController, previsionController);
    }

    public void tearDown() {
        principeValeurRepository.deleteAll();
        objectifRepository.deleteAll();
        categorieRepository.deleteAll();
        utilisateurRepository.deleteAll();
        profilRepository.deleteAll();
        permissionRepository.deleteAll();
    }

}
