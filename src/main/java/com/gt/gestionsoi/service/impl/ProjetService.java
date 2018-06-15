package com.gt.gestionsoi.service.impl;

import com.gt.base.exception.CustomException;
import com.gt.base.service.impl.BaseEntityService;
import com.gt.gestionsoi.entity.Projet;
import com.gt.gestionsoi.repository.ProjetRepository;
import com.gt.gestionsoi.service.IProjetService;
import com.gt.gestionsoi.util.MPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe Service de l'entité Projet
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 25/09/2017
 */
@Service
public class ProjetService extends BaseEntityService<Projet, Integer> implements IProjetService {

    @Autowired
    public ProjetService(ProjetRepository repository) {
        super(repository);
    }

    @Override
    public synchronized Projet save(Projet processus) throws CustomException {
        controler(processus);
        return super.save(processus);
    }

    private void controler(Projet processus) throws CustomException {
        if (processus.getLibelle() == null || processus.getLibelle().isEmpty()) {
            throw new CustomException(MPConstants.LE_LIBELLE_DU_PROJET_OBLIGATOIRE);
        }
        Optional<Projet> op = ((ProjetRepository) repository).findByLibelle(processus.getLibelle());
        if (op.isPresent() && !op.get().getIdentifiant().equals(processus.getIdentifiant())) {
            throw new CustomException(MPConstants.LE_LIBELLE_DU_PROJET_EXIST);
        }
    }

    @Override
    public synchronized Projet saveAndFlush(Projet processus) throws CustomException {
        controler(processus);
        return super.saveAndFlush(processus);
    }

    @Override
    public List<Projet> recupererLaListeVersionnee(Integer[] ints) {
        return ((ProjetRepository) repository).recupererLaListeVersionnee(ints);
    }
}
