package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Prevision;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.PrevisionRepository;
import com.gt.gestionsoi.service.IPrevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Classe Service de l'entité Prevision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 25/09/2017
 */
@Service
public class PrevisionService extends BaseEntityService<Prevision, Integer> implements IPrevisionService {

    @Autowired
    public PrevisionService(PrevisionRepository repository) {
        super(repository);
    }

    @Override
    public synchronized Prevision save(Prevision prevision) throws CustomException {
        controler(prevision);
        construirePrevision(prevision);
        return super.save(prevision);
    }

    private void controler(Prevision prevision) throws CustomException {
        if (prevision.getDescription() == null || prevision.getDescription().isEmpty()) {
            throw new CustomException("error.description.obligatoire");
        }
    }

    private void construirePrevision(Prevision prevision) {
        prevision.setDateCreation(new Date());
    }

    @Override
    public synchronized Prevision saveAndFlush(Prevision prevision) throws CustomException {
        controler(prevision);
        construirePrevision(prevision);
        return super.saveAndFlush(prevision);
    }

    @Override
    public Page<Prevision> findAllByOrderByIdentifiantDesc(Pageable p) {
        return ((PrevisionRepository) repository).findAllByOrderByIdentifiantDesc(p);
    }

    @Override
    public List<Prevision> recupererLaListeVersionnee(Integer[] ints) {
        return ((PrevisionRepository) repository).recupererLaListeVersionnee(ints);
    }
}
