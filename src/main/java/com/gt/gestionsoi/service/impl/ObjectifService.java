package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Objectif;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.ObjectifRepository;
import com.gt.gestionsoi.service.IObjectifService;
import com.gt.gestionsoi.util.MPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe Service de l'entité Objectif
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 25/09/2017
 */
@Service
public class ObjectifService extends BaseEntityService<Objectif, Integer> implements IObjectifService {

    @Autowired
    public ObjectifService(ObjectifRepository repository) {
        super(repository);
    }

    @Override
    public synchronized Objectif save(Objectif categorie) throws CustomException {
        controler(categorie);
        return super.save(categorie);
    }

    private void controler(Objectif categorie) throws CustomException {
        if (categorie.getLibelle() == null || categorie.getLibelle().isEmpty()) {
            throw new CustomException(MPConstants.LE_LIBELLE_DE_L_OBJECTIF_OBLIGATOIRE);
        }
        Optional<Objectif> op = ((ObjectifRepository) repository).findByLibelle(categorie.getLibelle());
        if (op.isPresent() && !op.get().getIdentifiant().equals(categorie.getIdentifiant())) {
            throw new CustomException(MPConstants.LE_LIBELLE_DE_L_OBJECTIF_EXIST);
        }
    }

    @Override
    public synchronized Objectif saveAndFlush(Objectif categorie) throws CustomException {
        controler(categorie);
        return super.saveAndFlush(categorie);
    }

    @Override
    public List<Objectif> recupererLaListeVersionnee(Integer[] ints) {
        return ((ObjectifRepository) repository).recupererLaListeVersionnee(ints);
    }
}
