package com.gt.gestionsoi.cucumber.step;

import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.AbstractFonctionalControllerTest;
import com.gt.gestionsoi.entity.Objectif;
import com.gt.gestionsoi.filtreform.ObjectifFormulaireDeFiltre;
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
public class ObjectifStep extends AbstractFonctionalControllerTest {

    private static final String DEFAULT_LIBELLE = "nom";
    private static final String UPDATE_LIBELLE = "nomUpdate";
    private CustomMockMvc.CustomResultActions perform;
    private Objectif objectif;

    public static Objectif getObjectif() {
        Objectif objectif = new Objectif();
        objectif.setLibelle(DEFAULT_LIBELLE);
        return objectif;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Etantdonné("^qu'aucun objectif n'était pas enregistré$")
    public void qu_aucune_objectif_n_était_enregistrée() {
        objectifRepository.deleteAll();
    }

    @Etantdonné("^qu'on dispose d'un objectif valide à enregistrer$")
    public void qu_on_dispose_d_une_objectif_valide_à_enregistrer() {
        this.objectif = ObjectifStep.getObjectif();
    }

    @Lorsqu("^on ajoute l'objectif$")
    public void on_ajoute_la_objectif() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                post(UrlConstants.SLASH + UrlConstants.Objectif.OBJECTIF_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(objectif)));
    }

    @Alors("^(\\d+) objectif est créé$")
    public void objectif_est_créé(int arg1) {
        assertThat(objectifRepository.count()).isEqualTo(arg1);
    }

    @Alors("^l'objectif créé est celui soumis à l'ajout$")
    public void le_objectif_créé_est_celui_soumis_à_l_ajout() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.libelle")
                        .value(ObjectifStep.DEFAULT_LIBELLE));
    }

    @Etantdonné("^qu'un objectif était déjà enregistré$")
    public void qu_un_objectif_était_déjà_enregistré() {
        objectifRepository.deleteAll();
        this.objectif = this.objectifRepository
                .save(ObjectifStep.getObjectif());
        assertThat(objectifRepository.findAll()).hasSize(1);
    }

    @Etantdonné("^qu'on dispose d'un objectif avec le même libellé à enregistrer$")
    public void qu_on_dispose_d_un_objectif_avec_le_même_nom_à_enregistrer() {
        this.objectif = ObjectifStep.getObjectif();
        this.objectif.setLibelle(DEFAULT_LIBELLE);
    }

    @Alors("^(\\d+) objectif demeure créé$")
    public void objectif_demeure_créé(int arg1) {
        assertThat(objectifRepository.count()).isEqualTo(arg1);
    }

    @Etantdonné("^qu'on dispose d'un objectif dont le libellé n'est pas renseigné$")
    public void qu_on_dispose_d_un_objectif_dont_le_nom_n_est_pas_renseigné() {
        this.objectif = ObjectifStep.getObjectif();
        this.objectif.setLibelle(null);
    }

    @Etantdonné("^qu'on récupère cet objectif pour le modifier$")
    public void qu_on_récupère_cette_objectif_pour_le_modifier() {
        this.objectif.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on modifie l'objectif$")
    public void on_modifie_le_objectif() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                put(UrlConstants.SLASH + UrlConstants.Objectif.OBJECTIF_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(objectif)));
    }

    @Etantdonné("^qu'on récupère cet objectif pour le modifier en ne renseignant pas le libellé$")
    public void qu_on_récupère_ce_objectif_pour_le_modifier_en_ne_renseignant_pas_le_libelle() {
        this.objectif.setLibelle(null);
    }

    @Etantdonné("^que deux objectifs étaient déjà enregistrés$")
    public void que_deux_objectifs_étaient_déjà_enregistrés() {
        objectifRepository.deleteAll();
        this.objectif = this.objectifRepository
                .save(ObjectifStep.getObjectif());
        Objectif objectif1 = ObjectifStep.getObjectif();
        objectif1.setLibelle(UPDATE_LIBELLE);
        this.objectifRepository.save(objectif1);
        assertThat(objectifRepository.findAll()).hasSize(2);
    }

    @Etantdonné("^qu'on récupère cet objectif pour modifier son libellé par une valeur qui existe déjà$")
    public void qu_on_récupère_ce_objectif_pour_modifier_son_nom_par_une_valeur_qui_existe_déjà() {
        this.objectif.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on récupère cet objectif$")
    public void on_récupère_ce_objectif() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(UrlConstants.SLASH + UrlConstants.Objectif.OBJECTIF_ID,
                        this.objectif.getIdentifiant()));
    }

    @Alors("^on obtient l'objectif déjà enregistré$")
    public void on_obtient_le_objectif_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.identifiant")
                        .value(this.objectif.getIdentifiant()))
                .andExpect(jsonPath("$.data.libelle")
                        .value(ObjectifStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on récupère la liste des objectifs$")
    public void on_récupère_la_liste_des_objectifs() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(String.format("%s?page=0&size=10",
                        UrlConstants.SLASH + UrlConstants.Objectif.OBJECTIF_RACINE)));
    }

    @Alors("^on a une liste contenant l'objectif déjà enregistré$")
    public void on_a_une_liste_contenant_le_objectif_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(this.objectif.getIdentifiant()))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(ObjectifStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on supprime l'objectif$")
    public void on_supprime_le_objectif() throws CustomException {
        this.perform = restSampleMockMvc.perform(delete(UrlConstants.SLASH
                + UrlConstants.Objectif.OBJECTIF_ID, this.objectif.getIdentifiant())
                .accept(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Lorsqu("^on recherche des objectifs en fonction d'un critère$")
    public void on_recherche_des_objectifs_en_fonction_d_un_critère() throws IOException, CustomException {
        Objectif objectif = getObjectif();
        ObjectifFormulaireDeFiltre objectifFormulaireDeFiltre = new ObjectifFormulaireDeFiltre();
        objectifFormulaireDeFiltre.setObjectif(objectif);
        objectifFormulaireDeFiltre.setPage(0);
        objectifFormulaireDeFiltre.setSize(10);
        String searchUrl = UrlConstants.SLASH + UrlConstants.Objectif.OBJECTIF_RECHERCHE;
        this.perform = restSampleMockMvc.perform(post(searchUrl)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(objectifFormulaireDeFiltre)));
    }

    @Alors("^les objectifs récupérés doivent respecter le critère défini$")
    public void les_objectifs_récupérés_doivent_respecter_le_critère_défini() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(CoreMatchers.hasItem(this.objectif.getIdentifiant())))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(CoreMatchers.hasItem(ObjectifStep.DEFAULT_LIBELLE)));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération d'ajout de l'objectif$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDAjoutDuLaObjectif() throws CustomException {
        this.perform.andExpect(status().isCreated());
    }

    @Alors("^on obtient le message sur l'objectif \"([^\"]*)\"$")
    public void onObtientLeMessageSurLaObjectif(String arg0) throws CustomException {
        this.perform.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(arg0));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de modification de l'objectif$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeModificationDeLaObjectif() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de suppression de l'objectif$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeSuppressionDeLaObjectif() throws CustomException {
        this.perform.andExpect(status().isOk());
    }
}
