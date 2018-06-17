package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Prevision;
import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.*;
import com.gt.gestionsoi.service.IPrevisionService;
import com.gt.gestionsoi.util.MPConstants;
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

    private VersionRepository versionRepository;
    private JournalRepository journalRepository;
    private ProcessusRepository processusRepository;
    private ObjectifRepository objectifRepository;
    private ProjetRepository projetRepository;
    private CategorieRepository categorieRepository;

    @Autowired
    public PrevisionService(PrevisionRepository repository) {
        super(repository);
    }

    @Override
    public VersionRepository getVersionRepository() {
        return versionRepository;
    }

    @Override
    public synchronized Prevision save(Prevision prevision) throws CustomException {
        controler(prevision);
        construirePrevision(prevision);
        prevision = super.save(prevision);
        miseAJourDeLaVersion(prevision, Version.MOTIF_AJOUT, prevision.getIdentifiant());
        return prevision;
    }

    private void controler(Prevision prevision) throws CustomException {
        if (prevision.getDescription() == null || prevision.getDescription().isEmpty()) {
            throw new CustomException("error.description.obligatoire");
        }
    }

    private void construirePrevision(Prevision prevision) {
        prevision.setDateCreation(new Date());
        if (prevision.getProcessus() != null && prevision.getProcessus().getIdentifiant() != null) {
            prevision.setProcessus(processusRepository.findOne(prevision.getProcessus().getIdentifiant()));
        }
        if (prevision.getObjectif() != null && prevision.getObjectif().getIdentifiant() != null) {
            prevision.setObjectif(objectifRepository.findOne(prevision.getObjectif().getIdentifiant()));
        }
        if (prevision.getProjet() != null && prevision.getProjet().getIdentifiant() != null) {
            prevision.setProjet(projetRepository.findOne(prevision.getProjet().getIdentifiant()));
        }
        if (prevision.getCategorie() != null && prevision.getCategorie().getIdentifiant() != null) {
            prevision.setCategorie(categorieRepository.findOne(prevision.getCategorie().getIdentifiant()));
        }
        if (prevision.getDomaine() != null && prevision.getDomaine().getIdentifiant() != null) {
            prevision.setDomaine(categorieRepository.findOne(prevision.getDomaine().getIdentifiant()));
        }
    }

    @Override
    public synchronized Prevision saveAndFlush(Prevision prevision) throws CustomException {
        controler(prevision);
        construirePrevision(prevision);
        prevision = super.saveAndFlush(prevision);
        miseAJourDeLaVersion(prevision, Version.MOTIF_MODIFICATION, prevision.getIdentifiant());
        return prevision;
    }

    @Override
    public boolean supprimer(Integer id) throws CustomException {
        if (journalRepository.countByPrevisionIdentifiant(id) != 0)
            throw new CustomException(MPConstants.LA_PREVISION_DEJA_ASSOCIE);
        Prevision prevision = findOne(id);
        if (super.delete(id)) {
            miseAJourDeLaVersion(prevision, Version.MOTIF_SUPPRESSION, id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Prevision> findAllByOrderByIdentifiantDesc(Pageable p) {
        return ((PrevisionRepository) repository).findAllByOrderByIdentifiantDesc(p);
    }

    @Override
    public List<Prevision> recupererLaListeVersionnee(Integer[] ints) {
        return ((PrevisionRepository) repository).recupererLaListeVersionnee(ints);
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
    public void setProjetRepository(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    @Autowired
    public void setProcessusRepository(ProcessusRepository processusRepository) {
        this.processusRepository = processusRepository;
    }

    @Autowired
    public void setObjectifRepository(ObjectifRepository objectifRepository) {
        this.objectifRepository = objectifRepository;
    }

    @Autowired
    public void setCategorieRepository(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }
}
