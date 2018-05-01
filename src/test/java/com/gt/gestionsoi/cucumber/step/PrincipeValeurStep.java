package com.gt.gestionsoi.cucumber.step;

import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.AbstractFonctionalControllerTest;
import com.gt.gestionsoi.entity.PrincipeValeur;
import com.gt.gestionsoi.filtreform.PrincipeValeurFormulaireDeFiltre;
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
public class PrincipeValeurStep extends AbstractFonctionalControllerTest {

    private static final String DEFAULT_LIBELLE = "nom";
    private static final String UPDATE_LIBELLE = "nomUpdate";
    private CustomMockMvc.CustomResultActions perform;
    private PrincipeValeur principeValeur;

    public static PrincipeValeur getPrincipeValeur() {
        PrincipeValeur principeValeur = new PrincipeValeur();
        principeValeur.setLibelle(DEFAULT_LIBELLE);
        return principeValeur;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Etantdonné("^qu'aucune valeur n'était pas enregistrée$")
    public void qu_aucune_valeur_n_était_enregistrée() {
        principeValeurRepository.deleteAll();
    }

    @Etantdonné("^qu'on dispose d'une valeur valide à enregistrer$")
    public void qu_on_dispose_d_une_valeur_valide_à_enregistrer() {
        this.principeValeur = PrincipeValeurStep.getPrincipeValeur();
    }

    @Lorsqu("^on ajoute la valeur$")
    public void on_ajoute_la_valeur() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                post(UrlConstants.SLASH + UrlConstants.PrincipeValeur.VALEUR_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(principeValeur)));
    }

    @Alors("^(\\d+) valeur est créée$")
    public void valeur_est_créé(int arg1) {
        assertThat(principeValeurRepository.count()).isEqualTo(arg1);
    }

    @Alors("^la valeur créée est celui soumise à l'ajout$")
    public void le_valeur_créé_est_celui_soumis_à_l_ajout() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.libelle")
                        .value(PrincipeValeurStep.DEFAULT_LIBELLE));
    }

    @Etantdonné("^qu'une valeur était déjà enregistrée$")
    public void qu_un_valeur_était_déjà_enregistré() {
        principeValeurRepository.deleteAll();
        this.principeValeur = this.principeValeurRepository
                .save(PrincipeValeurStep.getPrincipeValeur());
        assertThat(principeValeurRepository.findAll()).hasSize(1);
    }

    @Etantdonné("^qu'on dispose d'une valeur avec le même libellé à enregistrer$")
    public void qu_on_dispose_d_un_valeur_avec_le_même_nom_à_enregistrer() {
        this.principeValeur = PrincipeValeurStep.getPrincipeValeur();
        this.principeValeur.setLibelle(DEFAULT_LIBELLE);
    }

    @Alors("^(\\d+) valeur demeure créée$")
    public void valeur_demeure_créé(int arg1) {
        assertThat(principeValeurRepository.count()).isEqualTo(arg1);
    }

    @Etantdonné("^qu'on dispose d'une valeur dont le libellé n'est pas renseigné$")
    public void qu_on_dispose_d_un_valeur_dont_le_nom_n_est_pas_renseigné() {
        this.principeValeur = PrincipeValeurStep.getPrincipeValeur();
        this.principeValeur.setLibelle(null);
    }

    @Etantdonné("^qu'on récupère cette valeur pour le modifier$")
    public void qu_on_récupère_cette_valeur_pour_le_modifier() {
        this.principeValeur.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on modifie la valeur$")
    public void on_modifie_le_valeur() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                put(UrlConstants.SLASH + UrlConstants.PrincipeValeur.VALEUR_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(principeValeur)));
    }

    @Etantdonné("^qu'on récupère cette valeur pour le modifier en ne renseignant pas le libellé$")
    public void qu_on_récupère_ce_valeur_pour_le_modifier_en_ne_renseignant_pas_le_libelle() {
        this.principeValeur.setLibelle(null);
    }

    @Etantdonné("^que deux valeurs étaient déjà enregistrées$")
    public void que_deux_valeurs_étaient_déjà_enregistrés() {
        principeValeurRepository.deleteAll();
        this.principeValeur = this.principeValeurRepository
                .save(PrincipeValeurStep.getPrincipeValeur());
        PrincipeValeur principeValeur1 = PrincipeValeurStep.getPrincipeValeur();
        principeValeur1.setLibelle(UPDATE_LIBELLE);
        this.principeValeurRepository.save(principeValeur1);
        assertThat(principeValeurRepository.findAll()).hasSize(2);
    }

    @Etantdonné("^qu'on récupère cette valeur pour modifier son libellé par une valeur qui existe déjà$")
    public void qu_on_récupère_ce_valeur_pour_modifier_son_nom_par_une_valeur_qui_existe_déjà() {
        this.principeValeur.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on récupère cette valeur$")
    public void on_récupère_ce_valeur() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(UrlConstants.SLASH + UrlConstants.PrincipeValeur.VALEUR_ID,
                        this.principeValeur.getIdentifiant()));
    }

    @Alors("^on obtient la valeur déjà enregistrée$")
    public void on_obtient_le_valeur_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.identifiant")
                        .value(this.principeValeur.getIdentifiant()))
                .andExpect(jsonPath("$.data.libelle")
                        .value(PrincipeValeurStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on récupère la liste des valeurs$")
    public void on_récupère_la_liste_des_valeurs() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(String.format("%s?page=0&size=10",
                        UrlConstants.SLASH + UrlConstants.PrincipeValeur.VALEUR_RACINE)));
    }

    @Alors("^on a une liste contenant la valeur déjà enregistrée$")
    public void on_a_une_liste_contenant_le_valeur_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(this.principeValeur.getIdentifiant()))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(PrincipeValeurStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on supprime la valeur$")
    public void on_supprime_le_valeur() throws CustomException {
        this.perform = restSampleMockMvc.perform(delete(UrlConstants.SLASH
                + UrlConstants.PrincipeValeur.VALEUR_ID, this.principeValeur.getIdentifiant())
                .accept(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Lorsqu("^on recherche des valeurs en fonction d'un critère$")
    public void on_recherche_des_valeurs_en_fonction_d_un_critère() throws IOException, CustomException {
        PrincipeValeur principeValeur = getPrincipeValeur();
        PrincipeValeurFormulaireDeFiltre principeValeurFormulaireDeFiltre = new PrincipeValeurFormulaireDeFiltre();
        principeValeurFormulaireDeFiltre.setPrincipeValeur(principeValeur);
        principeValeurFormulaireDeFiltre.setPage(0);
        principeValeurFormulaireDeFiltre.setSize(10);
        String searchUrl = UrlConstants.SLASH + UrlConstants.PrincipeValeur.VALEUR_RECHERCHE;
        this.perform = restSampleMockMvc.perform(post(searchUrl)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(principeValeurFormulaireDeFiltre)));
    }

    @Alors("^les valeurs récupérées doivent respecter le critère défini$")
    public void les_valeurs_récupérés_doivent_respecter_le_critère_défini() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(CoreMatchers.hasItem(this.principeValeur.getIdentifiant())))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(CoreMatchers.hasItem(PrincipeValeurStep.DEFAULT_LIBELLE)));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération d'ajout de la valeur$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDAjoutDuLaPrincipeValeur() throws CustomException {
        this.perform.andExpect(status().isCreated());
    }

    @Alors("^on obtient le message sur la valeur \"([^\"]*)\"$")
    public void onObtientLeMessageSurLaPrincipeValeur(String arg0) throws CustomException {
        this.perform.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(arg0));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de modification de la valeur$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeModificationDeLaPrincipeValeur() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de suppression de la valeur$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeSuppressionDeLaPrincipeValeur() throws CustomException {
        this.perform.andExpect(status().isOk());
    }
}
