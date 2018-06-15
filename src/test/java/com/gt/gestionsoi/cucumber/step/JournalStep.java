package com.gt.gestionsoi.cucumber.step;

import com.gt.base.exception.CustomException;
import com.gt.gestionsoi.AbstractFonctionalControllerTest;
import com.gt.gestionsoi.entity.Journal;
import com.gt.gestionsoi.filtreform.JournalFormulaireDeFiltre;
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
public class JournalStep extends AbstractFonctionalControllerTest {

    private static final String DEFAULT_DESCRIPTION = "nom";
    private static final String UPDATE_DESCRIPTION = "nomUpdate";
    private CustomMockMvc.CustomResultActions perform;
    private Journal journal;

    public static Journal getJournal() {
        Journal journal = new Journal();
        journal.setDescription(DEFAULT_DESCRIPTION);
        return journal;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Etantdonné("^qu'aucun journal n'était pas enregistré$")
    public void qu_aucune_journal_n_était_enregistrée() {
        journalRepository.deleteAll();
    }

    @Etantdonné("^qu'on dispose d'un journal valide à enregistrer$")
    public void qu_on_dispose_d_une_journal_valide_à_enregistrer() {
        this.journal = JournalStep.getJournal();
    }

    @Lorsqu("^on ajoute le journal$")
    public void on_ajoute_la_journal() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                post(UrlConstants.SLASH + UrlConstants.Journal.JOURNAL_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(journal)));
    }

    @Alors("^(\\d+) journal est créé$")
    public void journal_est_créé(int arg1) {
        assertThat(journalRepository.count()).isEqualTo(arg1);
    }

    @Alors("^le journal créé est celui soumis à l'ajout$")
    public void le_journal_créé_est_celui_soumis_à_l_ajout() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.description")
                        .value(JournalStep.DEFAULT_DESCRIPTION));
    }

    @Etantdonné("^qu'un journal était déjà enregistré$")
    public void qu_un_journal_était_déjà_enregistré() {
        journalRepository.deleteAll();
        this.journal = this.journalRepository
                .save(JournalStep.getJournal());
        assertThat(journalRepository.findAll()).hasSize(1);
    }

    @Alors("^(\\d+) journal demeure créé$")
    public void journal_demeure_créé(int arg1) {
        assertThat(journalRepository.count()).isEqualTo(arg1);
    }

    @Etantdonné("^qu'on récupère cet journal pour le modifier$")
    public void qu_on_récupère_cet_journal_pour_le_modifier() {
        this.journal.setDescription(UPDATE_DESCRIPTION);
    }

    @Lorsqu("^on modifie le journal$")
    public void on_modifie_le_journal() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                put(UrlConstants.SLASH + UrlConstants.Journal.JOURNAL_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(journal)));
    }

    @Etantdonné("^que deux journal étaient déjà enregistrés$")
    public void que_deux_journal_étaient_déjà_enregistrés() {
        journalRepository.deleteAll();
        this.journal = this.journalRepository
                .save(JournalStep.getJournal());
        Journal journal1 = JournalStep.getJournal();
        journal1.setDescription(UPDATE_DESCRIPTION);
        this.journalRepository.save(journal1);
        assertThat(journalRepository.findAll()).hasSize(2);
    }

    @Etantdonné("^qu'on récupère cet journal pour modifier son libellé par une valeur qui existe déjà$")
    public void qu_on_récupère_ce_journal_pour_modifier_son_nom_par_une_valeur_qui_existe_déjà() {
        this.journal.setDescription(UPDATE_DESCRIPTION);
    }

    @Lorsqu("^on récupère cet journal$")
    public void on_récupère_ce_journal() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(UrlConstants.SLASH + UrlConstants.Journal.JOURNAL_ID,
                        this.journal.getIdentifiant()));
    }

    @Alors("^on obtient le journal déjà enregistré$")
    public void on_obtient_le_journal_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.identifiant")
                        .value(this.journal.getIdentifiant()))
                .andExpect(jsonPath("$.data.description")
                        .value(JournalStep.DEFAULT_DESCRIPTION));
    }

    @Lorsqu("^on récupère la liste des lignes du journal$")
    public void on_récupère_la_liste_des_journal() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(String.format("%s?page=0&size=10",
                        UrlConstants.SLASH + UrlConstants.Journal.JOURNAL_RACINE)));
    }

    @Alors("^on a une liste contenant les lignes du journal déjà enregistré$")
    public void on_a_une_liste_contenant_le_journal_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(this.journal.getIdentifiant()))
                .andExpect(jsonPath("$.data.content.[*].description")
                        .value(JournalStep.DEFAULT_DESCRIPTION));
    }

    @Lorsqu("^on supprime le journal$")
    public void on_supprime_le_journal() throws CustomException {
        this.perform = restSampleMockMvc.perform(delete(UrlConstants.SLASH
                + UrlConstants.Journal.JOURNAL_ID, this.journal.getIdentifiant())
                .accept(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Lorsqu("^on recherche des journaux en fonction d'un critère$")
    public void on_recherche_des_journal_en_fonction_d_un_critère() throws IOException, CustomException {
        Journal journal = getJournal();
        JournalFormulaireDeFiltre journalFormulaireDeFiltre = new JournalFormulaireDeFiltre();
        journalFormulaireDeFiltre.setJournal(journal);
        journalFormulaireDeFiltre.setPage(0);
        journalFormulaireDeFiltre.setSize(10);
        String searchUrl = UrlConstants.SLASH + UrlConstants.Journal.JOURNAL_RECHERCHE;
        this.perform = restSampleMockMvc.perform(post(searchUrl)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(journalFormulaireDeFiltre)));
    }

    @Alors("^les journaux récupérés doivent respecter le critère défini$")
    public void les_journal_récupérés_doivent_respecter_le_critère_défini() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(CoreMatchers.hasItem(this.journal.getIdentifiant())))
                .andExpect(jsonPath("$.data.content.[*].description")
                        .value(CoreMatchers.hasItem(JournalStep.DEFAULT_DESCRIPTION)));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération d'ajout de le journal$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDAjoutDuLaJournal() throws CustomException {
        this.perform.andExpect(status().isCreated());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de modification de le journal$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeModificationDeLaJournal() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de suppression de le journal$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeSuppressionDeLaJournal() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Et("^qu'on dispose d'un journal non valide à enregistrer$")
    public void quOnDisposeDUnJournalNonValideÀEnregistrer() {
        this.journal = JournalStep.getJournal();
        this.journal.setDescription(null);
    }

    @Alors("^on obtient une réponse confirmant l'échec de l'opération d'ajout de le journal$")
    public void onObtientUneRéponseConfirmantLÉchecDeLOpérationDAjoutDeLeJournal() throws CustomException {
        this.perform.andExpect(status().isInternalServerError());
    }

    @Et("^qu'on récupère cet journal pour le modifier avec des informations non valide$")
    public void quOnRécupèreCetJournalPourLeModifierAvecDesInformationsNonValide() {
        this.journal.setDescription(null);
    }

    @Alors("^on obtient une réponse confirmant l'échec de l'opération de modification de le journal$")
    public void onObtientUneRéponseConfirmantLÉchecDeLOpérationDeModificationDeLeJournal() throws CustomException {
        this.perform.andExpect(status().isInternalServerError());
    }
}
