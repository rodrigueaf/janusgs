package com.gt.gestionsoi.util;

/**
 * Classe des constantes contenant les URL
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 18/09/2017
 */
public class UrlConstants {

    public static final String SLASH = "/";

    private UrlConstants() {
    }

    public static class PrincipeValeur {
        public static final String VALEUR_RACINE = "principes-valeurs";
        public static final String VALEUR_ID = "principes-valeurs/{valeurId}";
        public static final String VALEUR_RECHERCHE = "principes-valeurs/search";
    }

    public static class Prevision {
        public static final String PREVISION_RACINE = "previsions";
        public static final String PREVISION_ID = "previsions/{previsionId}";
        public static final String PREVISION_RECHERCHE = "previsions/search";
    }

    public static class Journal {
        public static final String JOURNAL_RACINE = "journaux";
        public static final String JOURNAL_ID = "journaux/{journalId}";
        public static final String JOURNAL_RECHERCHE = "journaux/search";
    }

    public static class Categorie {
        public static final String CATEGORIE_RACINE = "categories";
        public static final String CATEGORIE_ID = "categories/{categorieId}";
        public static final String CATEGORIE_RECHERCHE = "categories/search";
    }

    public static class Vision {
        public static final String VISION_RACINE = "visions";
        public static final String VISION_ID = "visions/{visionId}";
        public static final String VISION_RECHERCHE = "visions/search";
    }

    public static class Objectif {
        public static final String OBJECTIF_RACINE = "objectifs";
        public static final String OBJECTIF_ID = "objectifs/{objectifId}";
        public static final String OBJECTIF_RECHERCHE = "objectifs/search";
    }

    public static class Processus {
        public static final String PROCESSUS_RACINE = "processus";
        public static final String PROCESSUS_ID = "processus/{processusId}";
        public static final String PROCESSUS_RECHERCHE = "processus/search";
    }

    public static class Projet {
        public static final String PROJET_RACINE = "projets";
        public static final String PROJET_ID = "projets/{projetId}";
        public static final String PROJET_RECHERCHE = "projets/search";
    }

    public static class Profil {
        public static final String PROFIL_RACINE = "profils";
        public static final String PROFIL_ID = "profils/{profilId}";
        public static final String PROFIL_NOM = "profils/nom/{nom}";
        public static final String PROFIL_ID_STATE = "profils/{profilId}/states";
        public static final String PROFIL_RECHERCHE = "profils/search";
        public static final String PROFIL_PERMISSION = "profils/{nom}/permissions";
        public static final String DOMAIN_URL = "domains";

        private Profil() {
        }
    }

    public static class Utilisateur {
        public static final String UTILISATEUR_RACINE = "users";
        public static final String UTILISATEUR_LOGIN = "users/{login:" + Constants.LOGIN_REGEX + "}";
        public static final String UTILISATEUR_ID_STATE = "users/{userId}/states";
        public static final String UTILISATEUR_RECHERCHE = "users/search";
        public static final String UTILISATEUR_PERMISSION = "users/permissions";

        private Utilisateur() {
        }
    }

    public static class Version {
        public static final String VERSION_RACINE = "versions";
        public static final String VERSION_ID = "versions/{id}";

        private Version() {
        }
    }
}
