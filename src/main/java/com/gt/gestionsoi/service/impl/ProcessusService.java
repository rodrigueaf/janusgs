package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Processus;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.ProcessusRepository;
import com.gt.gestionsoi.service.IProcessusService;
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
public class ProcessusService extends BaseEntityService<Processus, Integer> implements IProcessusService {

    @Autowired
    public ProcessusService(ProcessusRepository repository) {
        super(repository);
    }

    @Override
    public synchronized Processus save(Processus processus) throws CustomException {
        controler(processus);
        return super.save(processus);
    }

    private void controler(Processus processus) throws CustomException {
        if (processus.getLibelle() == null || processus.getLibelle().isEmpty()) {
            throw new CustomException(MPConstants.LE_LIBELLE_DU_PROCESSUS_OBLIGATOIRE);
        }
        Optional<Processus> op = ((ProcessusRepository) repository).findByLibelle(processus.getLibelle());
        if (op.isPresent() && !op.get().getIdentifiant().equals(processus.getIdentifiant())) {
            throw new CustomException(MPConstants.LE_LIBELLE_DU_PROCESSUS_EXIST);
        }
    }

    @Override
    public synchronized Processus saveAndFlush(Processus processus) throws CustomException {
        controler(processus);
        return super.saveAndFlush(processus);
    }

    @Override
    public List<Processus> recupererLaListeVersionnee(Integer[] ints) {
        return ((ProcessusRepository) repository).recupererLaListeVersionnee(ints);
    }
}
