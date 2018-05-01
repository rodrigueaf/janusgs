package com.gt.gestionsoi.cucumber.step;

import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.AbstractFonctionalControllerTest;
import com.gt.gestionsoi.entity.Categorie;
import com.gt.gestionsoi.filtreform.CategorieFormulaireDeFiltre;
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
public class CategorieStep extends AbstractFonctionalControllerTest {

    private static final String DEFAULT_LIBELLE = "nom";
    private static final String UPDATE_LIBELLE = "nomUpdate";
    private CustomMockMvc.CustomResultActions perform;
    private Categorie categorie;

    public static Categorie getCategorie() {
        Categorie categorie = new Categorie();
        categorie.setLibelle(DEFAULT_LIBELLE);
        return categorie;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Etantdonné("^qu'aucune catégorie n'était pas enregistrée$")
    public void qu_aucune_catégorie_n_était_enregistrée() {
        categorieRepository.deleteAll();
    }

    @Etantdonné("^qu'on dispose d'une catégorie valide à enregistrer$")
    public void qu_on_dispose_d_une_catégorie_valide_à_enregistrer() {
        this.categorie = CategorieStep.getCategorie();
    }

    @Lorsqu("^on ajoute la catégorie$")
    public void on_ajoute_la_catégorie() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                post(UrlConstants.SLASH + UrlConstants.Categorie.CATEGORIE_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(categorie)));
    }

    @Alors("^(\\d+) catégorie est créée$")
    public void categorie_est_créé(int arg1) {
        assertThat(categorieRepository.count()).isEqualTo(arg1);
    }

    @Alors("^la catégorie créée est celui soumise à l'ajout$")
    public void le_categorie_créé_est_celui_soumis_à_l_ajout() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.libelle")
                        .value(CategorieStep.DEFAULT_LIBELLE));
    }

    @Etantdonné("^qu'une catégorie était déjà enregistrée$")
    public void qu_un_categorie_était_déjà_enregistré() {
        categorieRepository.deleteAll();
        this.categorie = this.categorieRepository
                .save(CategorieStep.getCategorie());
        assertThat(categorieRepository.findAll()).hasSize(1);
    }

    @Etantdonné("^qu'on dispose d'une catégorie avec le même libellé à enregistrer$")
    public void qu_on_dispose_d_un_categorie_avec_le_même_nom_à_enregistrer() {
        this.categorie = CategorieStep.getCategorie();
        this.categorie.setLibelle(DEFAULT_LIBELLE);
    }

    @Alors("^(\\d+) catégorie demeure créée$")
    public void categorie_demeure_créé(int arg1) {
        assertThat(categorieRepository.count()).isEqualTo(arg1);
    }

    @Etantdonné("^qu'on dispose d'une catégorie dont le libellé n'est pas renseigné$")
    public void qu_on_dispose_d_un_categorie_dont_le_nom_n_est_pas_renseigné() {
        this.categorie = CategorieStep.getCategorie();
        this.categorie.setLibelle(null);
    }

    @Etantdonné("^qu'on récupère cette catégorie pour le modifier$")
    public void qu_on_récupère_cette_categorie_pour_le_modifier() {
        this.categorie.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on modifie la catégorie$")
    public void on_modifie_le_categorie() throws IOException, CustomException {
        this.perform = restSampleMockMvc.perform(
                put(UrlConstants.SLASH + UrlConstants.Categorie.CATEGORIE_RACINE)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(categorie)));
    }

    @Etantdonné("^qu'on récupère cette catégorie pour le modifier en ne renseignant pas le libellé$")
    public void qu_on_récupère_ce_categorie_pour_le_modifier_en_ne_renseignant_pas_le_libelle() {
        this.categorie.setLibelle(null);
    }

    @Etantdonné("^que deux catégories étaient déjà enregistrées$")
    public void que_deux_categories_étaient_déjà_enregistrés() {
        categorieRepository.deleteAll();
        this.categorie = this.categorieRepository
                .save(CategorieStep.getCategorie());
        Categorie categorie1 = CategorieStep.getCategorie();
        categorie1.setLibelle(UPDATE_LIBELLE);
        this.categorieRepository.save(categorie1);
        assertThat(categorieRepository.findAll()).hasSize(2);
    }

    @Etantdonné("^qu'on récupère cette catégorie pour modifier son libellé par une valeur qui existe déjà$")
    public void qu_on_récupère_ce_categorie_pour_modifier_son_nom_par_une_valeur_qui_existe_déjà() {
        this.categorie.setLibelle(UPDATE_LIBELLE);
    }

    @Lorsqu("^on récupère cette catégorie$")
    public void on_récupère_ce_categorie() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(UrlConstants.SLASH + UrlConstants.Categorie.CATEGORIE_ID,
                        this.categorie.getIdentifiant()));
    }

    @Alors("^on obtient la catégorie déjà enregistrée$")
    public void on_obtient_le_categorie_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.identifiant")
                        .value(this.categorie.getIdentifiant()))
                .andExpect(jsonPath("$.data.libelle")
                        .value(CategorieStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on récupère la liste des catégories$")
    public void on_récupère_la_liste_des_categories() throws CustomException {
        this.perform = restSampleMockMvc
                .perform(get(String.format("%s?page=0&size=10",
                        UrlConstants.SLASH + UrlConstants.Categorie.CATEGORIE_RACINE)));
    }

    @Alors("^on a une liste contenant la catégorie déjà enregistrée$")
    public void on_a_une_liste_contenant_le_categorie_déjà_enregistré() throws CustomException {
        this.perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(this.categorie.getIdentifiant()))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(CategorieStep.DEFAULT_LIBELLE));
    }

    @Lorsqu("^on supprime la catégorie$")
    public void on_supprime_le_categorie() throws CustomException {
        this.perform = restSampleMockMvc.perform(delete(UrlConstants.SLASH
                + UrlConstants.Categorie.CATEGORIE_ID, this.categorie.getIdentifiant())
                .accept(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Lorsqu("^on recherche des catégories en fonction d'un critère$")
    public void on_recherche_des_categories_en_fonction_d_un_critère() throws IOException, CustomException {
        Categorie categorie = getCategorie();
        CategorieFormulaireDeFiltre categorieFormulaireDeFiltre = new CategorieFormulaireDeFiltre();
        categorieFormulaireDeFiltre.setCategorie(categorie);
        categorieFormulaireDeFiltre.setPage(0);
        categorieFormulaireDeFiltre.setSize(10);
        String searchUrl = UrlConstants.SLASH + UrlConstants.Categorie.CATEGORIE_RECHERCHE;
        this.perform = restSampleMockMvc.perform(post(searchUrl)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categorieFormulaireDeFiltre)));
    }

    @Alors("^les catégories récupérées doivent respecter le critère défini$")
    public void les_categories_récupérés_doivent_respecter_le_critère_défini() throws CustomException {
        this.perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.content.[*].identifiant")
                        .value(CoreMatchers.hasItem(this.categorie.getIdentifiant())))
                .andExpect(jsonPath("$.data.content.[*].libelle")
                        .value(CoreMatchers.hasItem(CategorieStep.DEFAULT_LIBELLE)));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération d'ajout de la catégorie$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDAjoutDuLaCategorie() throws CustomException {
        this.perform.andExpect(status().isCreated());
    }

    @Alors("^on obtient le message sur la catégorie \"([^\"]*)\"$")
    public void onObtientLeMessageSurLaCategorie(String arg0) throws CustomException {
        this.perform.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(arg0));
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de modification de la catégorie$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeModificationDeLaCategorie() throws CustomException {
        this.perform.andExpect(status().isOk());
    }

    @Alors("^on obtient une réponse confirmant la réussite de l'opération de suppression de la catégorie$")
    public void onObtientUneRéponseConfirmantLaRéussiteDeLOpérationDeSuppressionDeLaCategorie() throws CustomException {
        this.perform.andExpect(status().isOk());
    }
}
