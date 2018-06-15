package com.gt.gestionsoi.service.impl;

import com.gt.base.exception.CustomException;
import com.gt.base.service.impl.BaseEntityService;
import com.gt.gestionsoi.entity.Categorie;
import com.gt.gestionsoi.repository.CategorieRepository;
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

    @Autowired
    public CategorieService(CategorieRepository repository) {
        super(repository);
    }

    @Override
    public synchronized Categorie save(Categorie categorie) throws CustomException {
        controler(categorie);
        return super.save(categorie);
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

    @Override
    public synchronized Categorie saveAndFlush(Categorie categorie) throws CustomException {
        controler(categorie);
        return super.saveAndFlush(categorie);
    }

    @Override
    public boolean supprimer(Integer categorieId) throws CustomException {
        Optional<Categorie> o = ((CategorieRepository) repository)
                .findWithSousCategoriesByIdentifiant(categorieId);
        if (o.isPresent() && !o.get().getSousCategories().isEmpty())
            throw new CustomException(MPConstants.LA_CATEGORIE_A_DEJA_SOUS_CATEGORIE);
        return super.delete(categorieId);
    }

    @Override
    public List<Categorie> recupererLaListeVersionnee(Integer[] ints) {
        return ((CategorieRepository) repository).recupererLaListeVersionnee(ints);
    }
}
