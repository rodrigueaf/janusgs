package com.gt.gestionsoi.cucumber.step;

import com.gt.base.exception.CustomException;
import com.gt.gestionsoi.AbstractFonctionalControllerTest;
import com.gt.gestionsoi.entity.Prevision;
import com.gt.gestionsoi.filtreform.PrevisionFormulaireDeFiltre;
import com.gt.gestionsoi.util.CustomMockMvc;
import com.gt.gestionsoi.util.TestUtil;
import com.gt.gestionsoi.util.UrlConstants;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Et;
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
public class PrevisionStep extends AbstractFonctionalControllerTest {

    private static final String DEFAULT_DESCRIPTION = "nom";
    private static final String UPDATE_DESCRIPTION = "nomUpdate";
    private CustomMockMvc.CustomResultActions perform;
    private Prevision prevision;

    public static Prevision getPrevision() {
        Prevision prevision = new Prevision();
        prevision.setDescription(DEFAULT_DESCRIPTION);
        return prevision;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Etantdonné("^qu'aucune prévision n'était pas enregistré$")
    public void qu_aucune_prevision_n_était_enregistrée() {
        previsionRepository.deleteAll();
    }

    @Etantdonné("^qu'on dispose d'une prévision valide à enregistrer$")
    public void qu_on_dispose_d_une_prevision_valide_à_enregistrer() {
        this.prevision = PrevisionStep.getPrevision();
    }

    @Lorsqu("^on ajoute la prévision$")
    public void on_ajoute_la_prevision() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                post(UrlConstants.SLASH + UrlConstants.Prevision.PREVISION_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(prevision)));
    }

    @Alors("^(\\d+) prévision est créée$")
    public void prevision_est_créée(int arg1) {
        assertThat(previsionRepository.count()).isEqualTo(arg1);
    }

    @Alors("^la prévision créée est celui soumise à l'ajout$")
    public void le_prevision_créée_est_celui_soumise_à_l_ajout() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.description")
                        .value(PrevisionStep.DEFAULT_DESCRIPTION));
    }

    @Etantdonné("^qu'une prévision était déjà enregistré$")
    public void qu_un_prevision_était_déjà_enregistré() {
        previsionRepository.deleteAll();
        this.prevision = this.previsionRepository
                .save(PrevisionStep.getPrevision());
        assertThat(previsionRepository.findAll()).hasSize(1);
    }

    @Alors("^(\\d+) prévision demeure créée$")
    public void prevision_demeure_créée(int arg1) {
        assertThat(previsionRepository.count()).isEqualTo(arg1);
    }

    @Etantdonné("^qu'on récupère cette prévision pour le modifier$")
    public void qu_on_récupère_cette_prevision_pour_le_modifier() {
        this.prevision.setDescription(UPDATE_DESCRIPTION);
    }

    @Lorsqu("^on modifie la prévision$")
    public void on_modifie_le_prevision() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                put(UrlConstants.SLASH + UrlConstants.Prevision.PREVISION_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(prevision)));
    }

    @Etantdonné("^que deux prévision étaient déjà enregistrés$")
    public void que_deux_prevision_étaient_déjà_enregistrés() {
        previsionRepository.deleteAll();
        this.prevision = this.previsionRepository
                .save(PrevisionStep.getPrevision());
        Prevision prevision1 = PrevisionStep.getPrevision();
        prevision1.setDescription(UPDATE_DESCRIPTION);
        this.previsionRepository.save(prevision1);
        assertThat(previsionRepository.findAll()).hasSize(2);
    }

    @Etantdonné("^qu'on récupère cette prévision pour modifier son libellé par une valeur qui existe déjà$")
    public void qu_on_récupère_ce_prevision_pour_modifier_son_nom_par_une_valeur_qui_existe_déjà() {
        this.prevision.setDescription(UPDATE_DESCRIPTION);
    }

    @Lorsqu("^on récupère cette prévision$")
    public void on_récupère_ce_prevision() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(UrlConstants.SLASH + UrlConstants.Prevision.PREVISION_ID,
                        this.prevision.getIdentifiant()));
    }

    @Alors("^on obtient la prévision déjà enregistré$")
    public void on_obtient_le_prevision_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.identifiant")
                        .value(this.prevision.getIdentifiant()))
                .andExpect(jsonPath("$.data.description")
                        .value(PrevisionStep.DEFAULT_DESCRIPTION));
    }

    @Lorsqu("^on récupère la liste des lignes de la prévision$")
    public void on_récupère_la_liste_des_prevision() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(String.format("%s?page=0&size=10",
                        UrlConstants.SLASH + UrlConstants.Prevision.PREVISION_RACINE)));
    }

    @Alors("^on a une liste contenant les lignes de la prévision déjà enregistré$")
    public void on_a_une_liste_contenant_le_prevision_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(this.prevision.getIdentifiant()))
                .andExpect(jsonPath("$.data.content.[*].description")
                        .value(PrevisionStep.DEFAULT_DESCRIPTION));
    }

    @Lorsqu("^on supprime la prévision$")
    public void on_supprime_le_prevision() throws CustomException {
        this.perform = restSampleMockMvc.perform(delete(UrlConstants.SLASH
                + UrlConstants.Prevision.PREVISION_ID, this.prevision.getIdentifiant())
                .accept(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Lorsqu("^on recherche des prévisions en fonction d'un critère$")
    public void on_recherche_des_prevision_en_fonction_d_un_critère() throws IOException, CustomException {
        Prevision prevision = getPrevision();
        PrevisionFormulaireDeFiltre previsionFormulaireDeFiltre = new PrevisionFormulaireDeFiltre();
        previsionFormulaireDeFiltre.setPrevision(prevision);
        previsionFormulaireDeFiltre.setPage(0);
        previsionFormulaireDeFiltre.setSize(10);
        String searchUrl = UrlConstants.SLASH + UrlConstants.Prevision.PREVISION_RECHERCHE;
        this.perform = restSampleMockMvc.perform(post(searchUrl)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(previsionFormulaireDeFiltre)));
    }

    @Alors("^les prévisions récupérées doivent respecter le critère défini$")
    public void les_prevision_récupérés_doivent_respecter_le_critère_défini() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(CoreMatchers.hasItem(this.prevision.getIdentifiant())))
                .andExpect(jsonPath("$.data.content.[*].description")
                        .value(CoreMatchers.hasItem(PrevisionStep.DEFAULT_DESCRIPTION)));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération d'ajout de la prévision$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDAjoutDuLaPrevision() throws CustomException {
        this.perform.andExpect(status().isCreated());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de modification de la prévision$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeModificationDeLaPrevision() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de suppression de la prévision$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeSuppressionDeLaPrevision() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Et("^qu'on dispose d'une prévision non valide à enregistrer$")
    public void quOnDisposeDUnPrevisionNonValideÀEnregistrer() {
        this.prevision = PrevisionStep.getPrevision();
        this.prevision.setDescription(null);
    }

    @Alors("^on obtient une réponse confirmant l'échec de l'opération d'ajout de la prévision$")
    public void onObtientUneRéponseConfirmantLÉchecDeLOpérationDAjoutDeLePrevision() throws CustomException {
        this.perform.andExpect(status().isInternalServerError());
    }

    @Et("^qu'on récupère cette prévision pour le modifier avec des informations non valide$")
    public void quOnRécupèreCetPrevisionPourLeModifierAvecDesInformationsNonValide() {
        this.prevision.setDescription(null);
    }

    @Alors("^on obtient une réponse confirmant l'échec de l'opération de modification de la prévision$")
    public void onObtientUneRéponseConfirmantLÉchecDeLOpérationDeModificationDeLePrevision() throws CustomException {
        this.perform.andExpect(status().isInternalServerError());
    }
}
