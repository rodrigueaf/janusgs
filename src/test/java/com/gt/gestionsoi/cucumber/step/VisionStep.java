package com.gt.gestionsoi.cucumber.step;

import com.gt.base.exception.CustomException;
import com.gt.gestionsoi.AbstractFonctionalControllerTest;
import com.gt.gestionsoi.entity.Vision;
import com.gt.gestionsoi.filtreform.VisionFormulaireDeFiltre;
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
public class VisionStep extends AbstractFonctionalControllerTest {

    private static final String DEFAULT_LIBELLE = "nom";
    private static final String UPDATE_LIBELLE = "nomUpdate";
    private CustomMockMvc.CustomResultActions perform;
    private Vision vision;

    public static Vision getVision() {
        Vision vision = new Vision();
        vision.setLibelle(DEFAULT_LIBELLE);
        return vision;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Etantdonné("^qu'aucune vision n'était pas enregistrée$")
    public void qu_aucune_vision_n_était_enregistrée() {
        visionRepository.deleteAll();
    }

    @Etantdonné("^qu'on dispose d'une vision valide à enregistrer$")
    public void qu_on_dispose_d_une_vision_valide_à_enregistrer() {
        this.vision = VisionStep.getVision();
    }

    @Lorsqu("^on ajoute la vision$")
    public void on_ajoute_la_vision() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                post(UrlConstants.SLASH + UrlConstants.Vision.VISION_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(vision)));
    }

    @Alors("^(\\d+) vision est créée$")
    public void vision_est_créé(int arg1) {
        assertThat(visionRepository.count()).isEqualTo(arg1);
    }

    @Alors("^la vision créée est celui soumise à l'ajout$")
    public void le_vision_créé_est_celui_soumis_à_l_ajout() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.libelle")
                        .value(VisionStep.DEFAULT_LIBELLE));
    }

    @Etantdonné("^qu'une vision était déjà enregistrée$")
    public void qu_un_vision_était_déjà_enregistré() {
        visionRepository.deleteAll();
        this.vision = this.visionRepository
                .save(VisionStep.getVision());
        assertThat(visionRepository.findAll()).hasSize(1);
    }

    @Etantdonné("^qu'on dispose d'une vision avec le même libellé à enregistrer$")
    public void qu_on_dispose_d_un_vision_avec_le_même_nom_à_enregistrer() {
        this.vision = VisionStep.getVision();
        this.vision.setLibelle(DEFAULT_LIBELLE);
    }

    @Alors("^(\\d+) vision demeure créée$")
    public void vision_demeure_créé(int arg1) {
        assertThat(visionRepository.count()).isEqualTo(arg1);
    }

    @Etantdonné("^qu'on dispose d'une vision dont le libellé n'est pas renseigné$")
    public void qu_on_dispose_d_un_vision_dont_le_nom_n_est_pas_renseigné() {
        this.vision = VisionStep.getVision();
        this.vision.setLibelle(null);
    }

    @Etantdonné("^qu'on récupère cette vision pour le modifier$")
    public void qu_on_récupère_cette_vision_pour_le_modifier() {
        this.vision.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on modifie la vision$")
    public void on_modifie_le_vision() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                put(UrlConstants.SLASH + UrlConstants.Vision.VISION_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(vision)));
    }

    @Etantdonné("^qu'on récupère cette vision pour le modifier en ne renseignant pas le libellé$")
    public void qu_on_récupère_ce_vision_pour_le_modifier_en_ne_renseignant_pas_le_libelle() {
        this.vision.setLibelle(null);
    }

    @Etantdonné("^que deux visions étaient déjà enregistrées$")
    public void que_deux_visions_étaient_déjà_enregistrés() {
        visionRepository.deleteAll();
        this.vision = this.visionRepository
                .save(VisionStep.getVision());
        Vision vision1 = VisionStep.getVision();
        vision1.setLibelle(UPDATE_LIBELLE);
        this.visionRepository.save(vision1);
        assertThat(visionRepository.findAll()).hasSize(2);
    }

    @Etantdonné("^qu'on récupère cette vision pour modifier son libellé par une valeur qui existe déjà$")
    public void qu_on_récupère_ce_vision_pour_modifier_son_nom_par_une_valeur_qui_existe_déjà() {
        this.vision.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on récupère cette vision$")
    public void on_récupère_ce_vision() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(UrlConstants.SLASH + UrlConstants.Vision.VISION_ID,
                        this.vision.getIdentifiant()));
    }

    @Alors("^on obtient la vision déjà enregistrée$")
    public void on_obtient_le_vision_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.identifiant")
                        .value(this.vision.getIdentifiant()))
                .andExpect(jsonPath("$.data.libelle")
                        .value(VisionStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on récupère la liste des visions$")
    public void on_récupère_la_liste_des_visions() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(String.format("%s?page=0&size=10",
                        UrlConstants.SLASH + UrlConstants.Vision.VISION_RACINE)));
    }

    @Alors("^on a une liste contenant la vision déjà enregistrée$")
    public void on_a_une_liste_contenant_le_vision_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(this.vision.getIdentifiant()))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(VisionStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on supprime la vision$")
    public void on_supprime_le_vision() throws CustomException {
        this.perform = restSampleMockMvc.perform(delete(UrlConstants.SLASH
                + UrlConstants.Vision.VISION_ID, this.vision.getIdentifiant())
                .accept(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Lorsqu("^on recherche des visions en fonction d'un critère$")
    public void on_recherche_des_visions_en_fonction_d_un_critère() throws IOException, CustomException {
        Vision vision = getVision();
        VisionFormulaireDeFiltre visionFormulaireDeFiltre = new VisionFormulaireDeFiltre();
        visionFormulaireDeFiltre.setVision(vision);
        visionFormulaireDeFiltre.setPage(0);
        visionFormulaireDeFiltre.setSize(10);
        String searchUrl = UrlConstants.SLASH + UrlConstants.Vision.VISION_RECHERCHE;
        this.perform = restSampleMockMvc.perform(post(searchUrl)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(visionFormulaireDeFiltre)));
    }

    @Alors("^les visions récupérées doivent respecter le critère défini$")
    public void les_visions_récupérés_doivent_respecter_le_critère_défini() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(CoreMatchers.hasItem(this.vision.getIdentifiant())))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(CoreMatchers.hasItem(VisionStep.DEFAULT_LIBELLE)));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération d'ajout de la vision$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDAjoutDuLaVision() throws CustomException {
        this.perform.andExpect(status().isCreated());
    }

    @Alors("^on obtient le message sur la vision \"([^\"]*)\"$")
    public void onObtientLeMessageSurLaVision(String arg0) throws CustomException {
        this.perform.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(arg0));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de modification de la vision$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeModificationDeLaVision() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de suppression de la vision$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeSuppressionDeLaVision() throws CustomException {
        this.perform.andExpect(status().isOk());
    }
}
