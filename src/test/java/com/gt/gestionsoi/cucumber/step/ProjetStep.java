package com.gt.gestionsoi.cucumber.step;

import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.AbstractFonctionalControllerTest;
import com.gt.gestionsoi.entity.Projet;
import com.gt.gestionsoi.entity.Projet;
import com.gt.gestionsoi.filtreform.ProjetFormulaireDeFiltre;
import com.gt.gestionsoi.util.CustomMockMvc;
import com.gt.gestionsoi.util.TestUtil;
import com.gt.gestionsoi.util.UrlConstants;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsqu;
import org.hamcrest.CoreMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration
public class ProjetStep extends AbstractFonctionalControllerTest {

    private static final String DEFAULT_LIBELLE = "nom";
    private static final String UPDATE_LIBELLE = "nomUpdate";
    private CustomMockMvc.CustomResultActions perform;
    private Projet projet;

    public static Projet getProjet() {
        Projet projet = new Projet();
        projet.setLibelle(DEFAULT_LIBELLE);
        return projet;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Etantdonné("^qu'aucun projet n'était pas enregistré$")
    public void qu_aucune_projet_n_était_enregistrée() {
        projetRepository.deleteAll();
    }

    @Etantdonné("^qu'on dispose d'un projet valide à enregistrer$")
    public void qu_on_dispose_d_une_projet_valide_à_enregistrer() {
        this.projet = ProjetStep.getProjet();
    }

    @Lorsqu("^on ajoute le projet$")
    public void on_ajoute_la_projet() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                post(UrlConstants.SLASH + UrlConstants.Projet.PROJET_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(projet)));
    }

    @Alors("^(\\d+) projet est créé$")
    public void projet_est_créé(int arg1) {
        assertThat(projetRepository.count()).isEqualTo(arg1);
    }

    @Alors("^le projet créé est celui soumis à l'ajout$")
    public void le_projet_créé_est_celui_soumis_à_l_ajout() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.libelle")
                        .value(ProjetStep.DEFAULT_LIBELLE));
    }

    @Etantdonné("^qu'un projet était déjà enregistré$")
    public void qu_un_projet_était_déjà_enregistré() {
        projetRepository.deleteAll();
        this.projet = this.projetRepository
                .save(ProjetStep.getProjet());
        assertThat(projetRepository.findAll()).hasSize(1);
    }

    @Etantdonné("^qu'on dispose d'un projet avec le même libellé à enregistrer$")
    public void qu_on_dispose_d_un_projet_avec_le_même_nom_à_enregistrer() {
        this.projet = ProjetStep.getProjet();
        this.projet.setLibelle(DEFAULT_LIBELLE);
    }

    @Alors("^(\\d+) projet demeure créé$")
    public void projet_demeure_créé(int arg1) {
        assertThat(projetRepository.count()).isEqualTo(arg1);
    }

    @Etantdonné("^qu'on dispose d'un projet dont le libellé n'est pas renseigné$")
    public void qu_on_dispose_d_un_projet_dont_le_nom_n_est_pas_renseigné() {
        this.projet = ProjetStep.getProjet();
        this.projet.setLibelle(null);
    }

    @Etantdonné("^qu'on récupère cet projet pour le modifier$")
    public void qu_on_récupère_cette_projet_pour_le_modifier() {
        this.projet.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on modifie le projet$")
    public void on_modifie_le_projet() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                put(UrlConstants.SLASH + UrlConstants.Projet.PROJET_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(projet)));
    }

    @Etantdonné("^qu'on récupère cet projet pour le modifier en ne renseignant pas le libellé$")
    public void qu_on_récupère_ce_projet_pour_le_modifier_en_ne_renseignant_pas_le_libelle() {
        this.projet.setLibelle(null);
    }

    @Etantdonné("^que deux projet étaient déjà enregistrés$")
    public void que_deux_projet_étaient_déjà_enregistrés() {
        projetRepository.deleteAll();
        this.projet = this.projetRepository
                .save(ProjetStep.getProjet());
        Projet projet1 = ProjetStep.getProjet();
        projet1.setLibelle(UPDATE_LIBELLE);
        this.projetRepository.save(projet1);
        assertThat(projetRepository.findAll()).hasSize(2);
    }

    @Etantdonné("^qu'on récupère cet projet pour modifier son libellé par une valeur qui existe déjà$")
    public void qu_on_récupère_ce_projet_pour_modifier_son_nom_par_une_valeur_qui_existe_déjà() {
        this.projet.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on récupère cet projet$")
    public void on_récupère_ce_projet() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(UrlConstants.SLASH + UrlConstants.Projet.PROJET_ID,
                        this.projet.getIdentifiant()));
    }

    @Alors("^on obtient le projet déjà enregistré$")
    public void on_obtient_le_projet_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.identifiant")
                        .value(this.projet.getIdentifiant()))
                .andExpect(jsonPath("$.data.libelle")
                        .value(ProjetStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on récupère la liste des projets$")
    public void on_récupère_la_liste_des_projet() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(String.format("%s?page=0&size=10",
                        UrlConstants.SLASH + UrlConstants.Projet.PROJET_RACINE)));
    }

    @Alors("^on a une liste contenant le projet déjà enregistré$")
    public void on_a_une_liste_contenant_le_projet_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(this.projet.getIdentifiant()))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(ProjetStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on supprime le projet$")
    public void on_supprime_le_projet() throws CustomException {
        this.perform = restSampleMockMvc.perform(delete(UrlConstants.SLASH
                + UrlConstants.Projet.PROJET_ID, this.projet.getIdentifiant())
                .accept(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Lorsqu("^on recherche des projets en fonction d'un critère$")
    public void on_recherche_des_projet_en_fonction_d_un_critère() throws IOException, CustomException {
        Projet projet = getProjet();
        ProjetFormulaireDeFiltre projetFormulaireDeFiltre = new ProjetFormulaireDeFiltre();
        projetFormulaireDeFiltre.setProjet(projet);
        projetFormulaireDeFiltre.setPage(0);
        projetFormulaireDeFiltre.setSize(10);
        String searchUrl = UrlConstants.SLASH + UrlConstants.Projet.PROJET_RECHERCHE;
        this.perform = restSampleMockMvc.perform(post(searchUrl)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projetFormulaireDeFiltre)));
    }

    @Alors("^les projets récupérés doivent respecter le critère défini$")
    public void les_projet_récupérés_doivent_respecter_le_critère_défini() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(CoreMatchers.hasItem(this.projet.getIdentifiant())))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(CoreMatchers.hasItem(ProjetStep.DEFAULT_LIBELLE)));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération d'ajout de le projet$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDAjoutDuLaProjet() throws CustomException {
        this.perform.andExpect(status().isCreated());
    }

    @Alors("^on obtient le message sur le projet \"([^\"]*)\"$")
    public void onObtientLeMessageSurLaProjet(String arg0) throws CustomException {
        this.perform.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(arg0));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de modification de le projet$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeModificationDeLaProjet() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de suppression de le projet$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeSuppressionDeLaProjet() throws CustomException {
        this.perform.andExpect(status().isOk());
    }
}
