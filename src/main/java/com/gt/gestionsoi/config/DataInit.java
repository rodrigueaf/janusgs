package com.gt.gestionsoi.config;

import com.gt.base.util.AuthoritiesConstants;
import com.gt.base.util.CustomResourceBundleMessageSource;
import com.gt.gestionsoi.entity.Permission;
import com.gt.gestionsoi.entity.Profil;
import com.gt.gestionsoi.entity.Utilisateur;
import com.gt.gestionsoi.repository.PermissionRepository;
import com.gt.gestionsoi.repository.ProfilRepository;
import com.gt.gestionsoi.repository.UtilisateurRepository;
import com.gt.gestionsoi.service.IUtilisateurService;
import com.gt.gestionsoi.util.Constants;
import com.gt.gestionsoi.util.PermissionsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Classe d'initialisaion de l'application
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 21/04/2017
 */
@Component
public class DataInit implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInit.class);

    private UtilisateurRepository utilisateurRepository;
    private PermissionRepository permissionRepository;
    private ProfilRepository profilRepository;
    private IUtilisateurService userService;
    private CustomResourceBundleMessageSource messageSource;

    public DataInit() {
        // do something later
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        LOGGER.info("Application initilized");

        initSecurity();
    }

    void initSecurity() {
        // Création d'un utilisateur par défaut
        if (utilisateurRepository.count() == 0) {
            // Création de la permision ADMIN
            Permission permissionAdmin = permissionRepository.save(new Permission(AuthoritiesConstants.ADMIN,
                    messageSource.getMessage("auth.admin")));
            // Création de la permision PERMISSION_JOURNAL
            Permission permissionJournal = permissionRepository.save(new Permission(PermissionsConstants.PERMISSION_JOURNAL,
                    messageSource.getMessage("auth.admin")));
            // Création de la permision PERMISSION_PREVISION
            Permission permissionPrevision = permissionRepository.save(new Permission(PermissionsConstants.PERMISSION_PREVISION,
                    messageSource.getMessage("auth.admin")));

            // Création du profil ADMIN
            Profil profilAdmin = profilRepository.findOneByNom(messageSource.getMessage(PermissionsConstants.PROFIL_ADMIN))
                    .orElseGet(() -> {
                        Profil p = new Profil(messageSource.getMessage(PermissionsConstants.PROFIL_ADMIN));
                        p.getPermissions().add(permissionAdmin);
                        p.getPermissions().add(permissionJournal);
                        p.getPermissions().add(permissionPrevision);
                        return profilRepository.save(p);
                    });

            // Création de l'utilisateur ADMIN
            Utilisateur utilisateur = new Utilisateur(Constants.NOM_DE_LUTILISATEUR_ADMIN,
                    Constants.MOT_DE_PASSE_DE_LUTILISATEUR_ADMIN,
                    Constants.NOM_DE_LUTILISATEUR_ADMIN,
                    Constants.NOM_DE_LUTILISATEUR_ADMIN,
                    Constants.EMAIL_DE_LUTILISATEUR_ADMIN);
            utilisateur.setProfil(profilAdmin);
            utilisateur.getPermissions().add(permissionAdmin);
            userService.creerUnUtilisateur(utilisateur);
        }
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Autowired
    public void setMessageSource(CustomResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setProfilRepository(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    @Autowired
    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Autowired
    public void setUserService(IUtilisateurService userService) {
        this.userService = userService;
    }

}
