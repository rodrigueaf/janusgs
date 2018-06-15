package com.gt.gestionsoi.cucumber.step;

import com.gt.base.exception.CustomException;
import com.gt.gestionsoi.AbstractFonctionalControllerTest;
import com.gt.gestionsoi.entity.Processus;
import com.gt.gestionsoi.filtreform.ProcessusFormulaireDeFiltre;
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
public class ProcessusStep extends AbstractFonctionalControllerTest {

    private static final String DEFAULT_LIBELLE = "nom";
    private static final String UPDATE_LIBELLE = "nomUpdate";
    private CustomMockMvc.CustomResultActions perform;
    private Processus processus;

    public static Processus getProcessus() {
        Processus processus = new Processus();
        processus.setLibelle(DEFAULT_LIBELLE);
        return processus;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Etantdonné("^qu'aucun processus n'était pas enregistré$")
    public void qu_aucune_processus_n_était_enregistrée() {
        processusRepository.deleteAll();
    }

    @Etantdonné("^qu'on dispose d'un processus valide à enregistrer$")
    public void qu_on_dispose_d_une_processus_valide_à_enregistrer() {
        this.processus = ProcessusStep.getProcessus();
    }

    @Lorsqu("^on ajoute le processus$")
    public void on_ajoute_la_processus() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                post(UrlConstants.SLASH + UrlConstants.Processus.PROCESSUS_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(processus)));
    }

    @Alors("^(\\d+) processus est créé$")
    public void processus_est_créé(int arg1) {
        assertThat(processusRepository.count()).isEqualTo(arg1);
    }

    @Alors("^le processus créé est celui soumis à l'ajout$")
    public void le_processus_créé_est_celui_soumis_à_l_ajout() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.libelle")
                        .value(ProcessusStep.DEFAULT_LIBELLE));
    }

    @Etantdonné("^qu'un processus était déjà enregistré$")
    public void qu_un_processus_était_déjà_enregistré() {
        processusRepository.deleteAll();
        this.processus = this.processusRepository
                .save(ProcessusStep.getProcessus());
        assertThat(processusRepository.findAll()).hasSize(1);
    }

    @Etantdonné("^qu'on dispose d'un processus avec le même libellé à enregistrer$")
    public void qu_on_dispose_d_un_processus_avec_le_même_nom_à_enregistrer() {
        this.processus = ProcessusStep.getProcessus();
        this.processus.setLibelle(DEFAULT_LIBELLE);
    }

    @Alors("^(\\d+) processus demeure créé$")
    public void processus_demeure_créé(int arg1) {
        assertThat(processusRepository.count()).isEqualTo(arg1);
    }

    @Etantdonné("^qu'on dispose d'un processus dont le libellé n'est pas renseigné$")
    public void qu_on_dispose_d_un_processus_dont_le_nom_n_est_pas_renseigné() {
        this.processus = ProcessusStep.getProcessus();
        this.processus.setLibelle(null);
    }

    @Etantdonné("^qu'on récupère cet processus pour le modifier$")
    public void qu_on_récupère_cette_processus_pour_le_modifier() {
        this.processus.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on modifie le processus$")
    public void on_modifie_le_processus() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                put(UrlConstants.SLASH + UrlConstants.Processus.PROCESSUS_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(processus)));
    }

    @Etantdonné("^qu'on récupère cet processus pour le modifier en ne renseignant pas le libellé$")
    public void qu_on_récupère_ce_processus_pour_le_modifier_en_ne_renseignant_pas_le_libelle() {
        this.processus.setLibelle(null);
    }

    @Etantdonné("^que deux processus étaient déjà enregistrés$")
    public void que_deux_processus_étaient_déjà_enregistrés() {
        processusRepository.deleteAll();
        this.processus = this.processusRepository
                .save(ProcessusStep.getProcessus());
        Processus processus1 = ProcessusStep.getProcessus();
        processus1.setLibelle(UPDATE_LIBELLE);
        this.processusRepository.save(processus1);
        assertThat(processusRepository.findAll()).hasSize(2);
    }

    @Etantdonné("^qu'on récupère cet processus pour modifier son libellé par une valeur qui existe déjà$")
    public void qu_on_récupère_ce_processus_pour_modifier_son_nom_par_une_valeur_qui_existe_déjà() {
        this.processus.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on récupère cet processus$")
    public void on_récupère_ce_processus() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(UrlConstants.SLASH + UrlConstants.Processus.PROCESSUS_ID,
                        this.processus.getIdentifiant()));
    }

    @Alors("^on obtient le processus déjà enregistré$")
    public void on_obtient_le_processus_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.identifiant")
                        .value(this.processus.getIdentifiant()))
                .andExpect(jsonPath("$.data.libelle")
                        .value(ProcessusStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on récupère la liste des processus$")
    public void on_récupère_la_liste_des_processus() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(String.format("%s?page=0&size=10",
                        UrlConstants.SLASH + UrlConstants.Processus.PROCESSUS_RACINE)));
    }

    @Alors("^on a une liste contenant le processus déjà enregistré$")
    public void on_a_une_liste_contenant_le_processus_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(this.processus.getIdentifiant()))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(ProcessusStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on supprime le processus$")
    public void on_supprime_le_processus() throws CustomException {
        this.perform = restSampleMockMvc.perform(delete(UrlConstants.SLASH
                + UrlConstants.Processus.PROCESSUS_ID, this.processus.getIdentifiant())
                .accept(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Lorsqu("^on recherche des processus en fonction d'un critère$")
    public void on_recherche_des_processus_en_fonction_d_un_critère() throws IOException, CustomException {
        Processus processus = getProcessus();
        ProcessusFormulaireDeFiltre processusFormulaireDeFiltre = new ProcessusFormulaireDeFiltre();
        processusFormulaireDeFiltre.setProcessus(processus);
        processusFormulaireDeFiltre.setPage(0);
        processusFormulaireDeFiltre.setSize(10);
        String searchUrl = UrlConstants.SLASH + UrlConstants.Processus.PROCESSUS_RECHERCHE;
        this.perform = restSampleMockMvc.perform(post(searchUrl)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(processusFormulaireDeFiltre)));
    }

    @Alors("^les processus récupérés doivent respecter le critère défini$")
    public void les_processus_récupérés_doivent_respecter_le_critère_défini() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(CoreMatchers.hasItem(this.processus.getIdentifiant())))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(CoreMatchers.hasItem(ProcessusStep.DEFAULT_LIBELLE)));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération d'ajout de le processus$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDAjoutDuLaProcessus() throws CustomException {
        this.perform.andExpect(status().isCreated());
    }

    @Alors("^on obtient le message sur le processus \"([^\"]*)\"$")
    public void onObtientLeMessageSurLaProcessus(String arg0) throws CustomException {
        this.perform.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(arg0));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de modification de le processus$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeModificationDeLaProcessus() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de suppression de le processus$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeSuppressionDeLaProcessus() throws CustomException {
        this.perform.andExpect(status().isOk());
    }
}
