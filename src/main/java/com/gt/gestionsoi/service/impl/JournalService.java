package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.entity.*;
import com.gt.gestionsoi.repository.*;
import com.gt.gestionsoi.service.IJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Classe Service de l'entité Journal
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 25/09/2017
 */
@Service
public class JournalService extends BaseEntityService<Journal, Integer> implements IJournalService {

    private CategorieRepository categorieRepository;
    private ObjectifRepository objectifRepository;
    private ProjetRepository projetRepository;
    private ProcessusRepository processusRepository;

    @Autowired
    public JournalService(JournalRepository repository) {
        super(repository);
    }

    @Override
    public synchronized Journal save(Journal journal) throws CustomException {
        controler(journal);
        construireJournal(journal);
        return super.save(journal);
    }

    private void controler(Journal journal) throws CustomException {
        if (journal.getDescription() == null || journal.getDescription().isEmpty()) {
            throw new CustomException("error.description.obligatoire");
        }
    }

    private void construireJournal(Journal journal) {
        if (journal.getCategorie() != null) {
            journal.setCategorie(categorieRepository.findByLibelle(journal.getCategorie().getLibelle())
                    .orElseGet(() -> categorieRepository
                            .save(new Categorie(journal.getCategorie().getLibelle().trim()))));
        }
        if (journal.getProjet() != null) {
            journal.setProjet(projetRepository.findByLibelle(journal.getProjet().getLibelle())
                    .orElseGet(() -> projetRepository
                            .save(new Projet(journal.getProjet().getLibelle().trim()))));
        }
        if (journal.getObjectif() != null) {
            journal.setObjectif(objectifRepository.findByLibelle(journal.getObjectif().getLibelle())
                    .orElseGet(() -> objectifRepository
                            .save(new Objectif(journal.getObjectif().getLibelle().trim()))));
        }
        if (journal.getProcessus() != null) {
            journal.setProcessus(processusRepository.findByLibelle(journal.getProcessus().getLibelle())
                    .orElseGet(() -> processusRepository
                            .save(new Processus(journal.getProcessus().getLibelle().trim()))));
        }
    }

    @Override
    public synchronized Journal saveAndFlush(Journal journal) throws CustomException {
        controler(journal);
        construireJournal(journal);
        return super.saveAndFlush(journal);
    }

    @Override
    public Page<Journal> findAllByOrderByIdentifiantDesc(Pageable p) {
        return ((JournalRepository) repository).findAllByOrderByIdentifiantDesc(p);
    }

    @Autowired
    public void setCategorieRepository(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Autowired
    public void setObjectifRepository(ObjectifRepository objectifRepository) {
        this.objectifRepository = objectifRepository;
    }

    @Autowired
    public void setProcessusRepository(ProcessusRepository processusRepository) {
        this.processusRepository = processusRepository;
    }

    @Autowired
    public void setProjetRepository(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }
}
