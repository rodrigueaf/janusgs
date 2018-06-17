package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Objectif;
import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.ObjectifRepository;
import com.gt.gestionsoi.repository.VersionRepository;
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

    private VersionRepository versionRepository;

    @Autowired
    public ObjectifService(ObjectifRepository repository) {
        super(repository);
    }

    @Override
    public VersionRepository getVersionRepository() {
        return versionRepository;
    }

    @Override
    public synchronized Objectif save(Objectif objectif) throws CustomException {
        controler(objectif);
        objectif = super.save(objectif);
        miseAJourDeLaVersion(objectif, Version.MOTIF_AJOUT, objectif.getIdentifiant());
        return objectif;
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
    public synchronized Objectif saveAndFlush(Objectif objectif) throws CustomException {
        controler(objectif);
        objectif = super.saveAndFlush(objectif);
        miseAJourDeLaVersion(objectif, Version.MOTIF_MODIFICATION, objectif.getIdentifiant());
        return objectif;
    }

    @Override
    public boolean delete(Integer id) {
        Objectif objectif = findOne(id);
        if (super.delete(id)) {
            miseAJourDeLaVersion(objectif, Version.MOTIF_SUPPRESSION, id);
            return true;
        }
        return false;
    }

    @Override
    public List<Objectif> recupererLaListeVersionnee(Integer[] ints) {
        return ((ObjectifRepository) repository).recupererLaListeVersionnee(ints);
    }

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }
}
