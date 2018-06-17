package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Categorie;
import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.CategorieRepository;
import com.gt.gestionsoi.repository.JournalRepository;
import com.gt.gestionsoi.repository.PrevisionRepository;
import com.gt.gestionsoi.repository.VersionRepository;
import com.gt.gestionsoi.service.ICategorieService;
import com.gt.gestionsoi.util.MPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe Service de l'entité ModePaiement
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 25/09/2017
 */
@Service
public class CategorieService extends BaseEntityService<Categorie, Integer> implements ICategorieService {

    private VersionRepository versionRepository;
    private JournalRepository journalRepository;
    private PrevisionRepository previsionRepository;

    @Autowired
    public CategorieService(CategorieRepository repository) {
        super(repository);
    }

    @Override
    public VersionRepository getVersionRepository() {
        return versionRepository;
    }

    @Override
    public synchronized Categorie save(Categorie categorie) throws CustomException {
        controler(categorie);
        reconstructionDeLaCategorie(categorie);
        categorie = super.save(categorie);
        miseAJourDeLaVersion(categorie, Version.MOTIF_AJOUT, categorie.getIdentifiant());
        return categorie;
    }

    private void controler(Categorie categorie) throws CustomException {
        if (categorie.getLibelle() == null || categorie.getLibelle().isEmpty()) {
            throw new CustomException(MPConstants.LE_LIBELLE_DE_LA_CATEGORIE_OBLIGATOIRE);
        }
        Optional<Categorie> op = ((CategorieRepository) repository).findByLibelle(categorie.getLibelle());
        if (op.isPresent() && !op.get().getIdentifiant().equals(categorie.getIdentifiant())) {
            throw new CustomException(MPConstants.LE_LIBELLE_DE_LA_CATEGORIE_EXIST);
        }
    }

    private void reconstructionDeLaCategorie(Categorie categorie){
        if(categorie.getCategorieParent() != null && categorie.getCategorieParent().getIdentifiant() != null){
            categorie.setCategorieParent(findOne(categorie.getCategorieParent().getIdentifiant()));
        }
    }

    @Override
    public synchronized Categorie saveAndFlush(Categorie categorie) throws CustomException {
        controler(categorie);
        reconstructionDeLaCategorie(categorie);
        categorie = super.saveAndFlush(categorie);
        miseAJourDeLaVersion(categorie, Version.MOTIF_MODIFICATION, categorie.getIdentifiant());
        return categorie;
    }

    @Override
    public boolean supprimer(Integer categorieId) throws CustomException {
        Optional<Categorie> o = ((CategorieRepository) repository)
                .findWithSousCategoriesByIdentifiant(categorieId);
        if (o.isPresent() && !o.get().getSousCategories().isEmpty())
            throw new CustomException(MPConstants.LA_CATEGORIE_A_DEJA_SOUS_CATEGORIE);
        if(journalRepository.countByCategorieIdentifiant(categorieId) != 0)
            throw new CustomException(MPConstants.LA_CATEGORIE_DEJA_ASSOCIE);
        if(journalRepository.countByDomaineIdentifiant(categorieId) != 0)
            throw new CustomException(MPConstants.LA_CATEGORIE_DEJA_ASSOCIE);
        if(previsionRepository.countByDomaineIdentifiant(categorieId) != 0)
            throw new CustomException(MPConstants.LA_CATEGORIE_DEJA_ASSOCIE);
        if(previsionRepository.countByCategorieIdentifiant(categorieId) != 0)
            throw new CustomException(MPConstants.LA_CATEGORIE_DEJA_ASSOCIE);
        Categorie categorie = findOne(categorieId);
        if (super.delete(categorieId)) {
            miseAJourDeLaVersion(categorie, Version.MOTIF_SUPPRESSION, categorieId);
            return true;
        }
        return false;
    }

    @Override
    public List<Categorie> recupererLaListeVersionnee(Integer[] ints) {
        return ((CategorieRepository) repository).recupererLaListeVersionnee(ints);
    }

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Autowired
    public void setJournalRepository(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @Autowired
    public void setPrevisionRepository(PrevisionRepository previsionRepository) {
        this.previsionRepository = previsionRepository;
    }
}
