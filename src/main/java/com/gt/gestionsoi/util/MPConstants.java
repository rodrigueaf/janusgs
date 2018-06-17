package com.gt.gestionsoi.util;

/**
 * Classe des constantes
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 18/09/2017
 */
public class MPConstants {

    /**
     * ***********  Profil ****************
     */

    public static final String LE_PROFIL_DOIT_PAS_AVOIR_ID = "error.profil.no.id";
    public static final String LE_NOM_DU_PROFIL_EXSTE_DEJA = "error.code.profil.nom.exist";
    public static final String LE_NOM_DU_PROFIL_OBLIGATOIRE = "error.code.profil.nom.required";
    public static final String PROFIL_INTROUVABLE = "error.profil.not.found";
    public static final String PROFIL_DEJA_ATTRIBUER = "error.profil.deja.attribuer";

    /**
     * ***********  Utilisateur ****************
     */

    public static final String UTILISATEUR_DOIT_PAS_AVOIR_ID = "msg.error.utilisateur.no.id";
    public static final String UTILISATEUR_INTROUVABLE = "error.user.not.found";
    public static final String LE_LOGIN_EXISTE_DEJA = "error.user.login.exist";
    public static final String LE_LOGIN_EST_OBLIGATOIRE = "error.user.login.required";
    public static final String LE_NOM_EST_OBLIGATOIRE = "error.user.nom.required";

    /**
     * ***********  Categorie ****************
     */

    public static final String LE_LIBELLE_DE_LA_CATEGORIE_OBLIGATOIRE = "error.categorie.libelle.obligatoire";
    public static final String LE_LIBELLE_DE_LA_CATEGORIE_EXIST = "error.categorie.libelle.exist";
    public static final String LA_CATEGORIE_A_DEJA_SOUS_CATEGORIE = "error.categorie.a.sous.categorie";
    public static final String LA_CATEGORIE_DEJA_ASSOCIE = "error.categorie.deja.associe";

    /**
     * ***********  Vision ****************
     */

    public static final String LE_LIBELLE_DE_LA_VISION_OBLIGATOIRE = "error.vision.libelle.obligatoire";
    public static final String LE_LIBELLE_DE_LA_VISION_EXIST = "error.vision.libelle.exist";

    /**
     * ***********  PrincipeValeur ****************
     */

    public static final String LE_LIBELLE_DE_LA_VALEUR_OBLIGATOIRE = "error.valeur.libelle.obligatoire";
    public static final String LE_LIBELLE_DE_LA_VALEUR_EXIST = "error.valeur.libelle.exist";

    /**
     * ***********  Objectif ****************
     */

    public static final String LE_LIBELLE_DE_L_OBJECTIF_OBLIGATOIRE = "error.objectif.libelle.obligatoire";
    public static final String LE_LIBELLE_DE_L_OBJECTIF_EXIST = "error.objectif.libelle.exist";

    /**
     * ***********  Processus ****************
     */

    public static final String LE_LIBELLE_DU_PROCESSUS_OBLIGATOIRE = "error.processus.libelle.obligatoire";
    public static final String LE_LIBELLE_DU_PROCESSUS_EXIST = "error.processus.libelle.exist";
    public static final String LE_PROCESSUS_DEJA_ASSOCIE = "error.processus.deja.associe";

    /**
     * ***********  Processus ****************
     */

    public static final String LE_LIBELLE_DU_PROJET_OBLIGATOIRE = "error.projet.libelle.obligatoire";
    public static final String LE_LIBELLE_DU_PROJET_EXIST = "error.projet.libelle.exist";
    public static final String LE_PROJET_DEJA_ASSOCIE = "error.projet.deja.associe";

    /**
     * ***********  Prevision ****************
     */

    public static final String LA_PREVISION_DEJA_ASSOCIE = "error.prevision.deja.associe";

    private MPConstants() {
    }
}
