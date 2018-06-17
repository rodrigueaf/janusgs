package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Projet;
import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.JournalRepository;
import com.gt.gestionsoi.repository.PrevisionRepository;
import com.gt.gestionsoi.repository.ProjetRepository;
import com.gt.gestionsoi.repository.VersionRepository;
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

    private VersionRepository versionRepository;
    private JournalRepository journalRepository;
    private PrevisionRepository previsionRepository;

    @Autowired
    public ProjetService(ProjetRepository repository) {
        super(repository);
    }

    @Override
    public VersionRepository getVersionRepository() {
        return versionRepository;
    }

    @Override
    public synchronized Projet save(Projet projet) throws CustomException {
        controler(projet);
        projet = super.save(projet);
        miseAJourDeLaVersion(projet, Version.MOTIF_AJOUT, projet.getIdentifiant());
        return projet;
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
    public synchronized Projet saveAndFlush(Projet projet) throws CustomException {
        controler(projet);
        projet = super.saveAndFlush(projet);
        miseAJourDeLaVersion(projet, Version.MOTIF_MODIFICATION, projet.getIdentifiant());
        return projet;
    }

    @Override
    public boolean supprimer(Integer id) throws CustomException{
        if(journalRepository.countByProjetIdentifiant(id) != 0)
            throw new CustomException(MPConstants.LE_PROJET_DEJA_ASSOCIE);
        if(previsionRepository.countByProjetIdentifiant(id) != 0)
            throw new CustomException(MPConstants.LE_PROJET_DEJA_ASSOCIE);
        Projet projet = findOne(id);
        if (super.delete(id)) {
            miseAJourDeLaVersion(projet, Version.MOTIF_SUPPRESSION, id);
            return true;
        }
        return false;
    }

    @Override
    public List<Projet> recupererLaListeVersionnee(Integer[] ints) {
        return ((ProjetRepository) repository).recupererLaListeVersionnee(ints);
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
