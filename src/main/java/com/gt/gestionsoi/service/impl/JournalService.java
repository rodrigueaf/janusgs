package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Journal;
import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.*;
import com.gt.gestionsoi.service.IJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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
    private VersionRepository versionRepository;
    private PrevisionRepository previsionRepository;

    @Autowired
    public JournalService(JournalRepository repository) {
        super(repository);
    }

    @Override
    public VersionRepository getVersionRepository() {
        return versionRepository;
    }

    @Override
    public synchronized Journal save(Journal journal) throws CustomException {
        controler(journal);
        construireJournal(journal);
        journal = super.save(journal);
        miseAJourDeLaVersion(journal, Version.MOTIF_AJOUT, journal.getIdentifiant());
        return journal;
    }

    private void controler(Journal journal) throws CustomException {
        if (journal.getDescription() == null || journal.getDescription().isEmpty()) {
            throw new CustomException("error.description.obligatoire");
        }
    }

    private void construireJournal(Journal journal) {
        if (journal.getCategorie() != null && journal.getCategorie().getIdentifiant() != null) {
            journal.setCategorie(categorieRepository.findOne(journal.getCategorie().getIdentifiant()));
        }
        if (journal.getProjet() != null && journal.getProjet().getIdentifiant() != null) {
            journal.setProjet(projetRepository.findOne(journal.getProjet().getIdentifiant()));
        }
        if (journal.getObjectif() != null && journal.getObjectif().getIdentifiant() != null) {
            journal.setObjectif(objectifRepository.findOne(journal.getObjectif().getIdentifiant()));
        }
        if (journal.getProcessus() != null && journal.getProcessus().getIdentifiant() != null) {
            journal.setProcessus(processusRepository.findOne(journal.getProcessus().getIdentifiant()));
        }
        if (journal.getPrevision() != null && journal.getPrevision().getIdentifiant() != null) {
            journal.setPrevision(previsionRepository.findOne(journal.getPrevision().getIdentifiant()));
        }
    }

    @Override
    public synchronized Journal saveAndFlush(Journal journal) throws CustomException {
        controler(journal);
        construireJournal(journal);
        journal = super.saveAndFlush(journal);
        miseAJourDeLaVersion(journal, Version.MOTIF_MODIFICATION, journal.getIdentifiant());
        return journal;
    }

    @Override
    public boolean delete(Integer journalId) {
        Journal journal = findOne(journalId);
        if (super.delete(journalId)) {
            miseAJourDeLaVersion(journal, Version.MOTIF_SUPPRESSION, journalId);
            return true;
        }
        return false;
    }

    @Override
    public Page<Journal> findAllByOrderByIdentifiantDesc(Pageable p) {
        return ((JournalRepository) repository).findAllByOrderByIdentifiantDesc(p);
    }

    @Override
    public List<Journal> recupererLaListeVersionnee(Integer[] ints) {
        return ((JournalRepository) repository).recupererLaListeVersionnee(ints);
    }

    /**
     * @see IJournalService#importer(String)
     */
    @Override
    public void importer(String journal) {
        String patternARespecter = "(\\d{2}/\\d{2}/\\d{4};\\d{2}:\\d{2};\\d{2}:\\d{2};[^;]*\\|?)+";
        if (!Pattern.matches(patternARespecter, journal)) {
            throw new CustomException("error.pattern.non.respecter");
        }
        Arrays.stream(journal.split("\\|"))
                .map(l -> {
                    String[] split = l.split(";");
                    Journal j = new Journal();
                    try {
                        j.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(split[0]));
                        j.setHeureDebutRealisation(new SimpleDateFormat("dd/MM/yyyy HH:mm")
                                .parse(split[0] + " " + split[1]));
                        j.setHeureFinRealisation(new SimpleDateFormat("dd/MM/yyyy HH:mm")
                                .parse(split[0] + " " + split[2]));
                        j.setDescription(split[3]);
                        return j;
                    } catch (ParseException e) {
                        Logger.getLogger(JournalService.class.getSimpleName()).info(e.getMessage());
                        return null;
                    }
                })
                .forEach(this::save);
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

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Autowired
    public void setPrevisionRepository(PrevisionRepository previsionRepository) {
        this.previsionRepository = previsionRepository;
    }
}
