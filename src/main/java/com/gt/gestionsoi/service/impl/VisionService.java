package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.entity.Vision;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.VersionRepository;
import com.gt.gestionsoi.repository.VisionRepository;
import com.gt.gestionsoi.service.IVisionService;
import com.gt.gestionsoi.util.MPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe Service de l'entité Vision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 25/09/2017
 */
@Service
public class VisionService extends BaseEntityService<Vision, Integer> implements IVisionService {

    private VersionRepository versionRepository;

    @Autowired
    public VisionService(VisionRepository repository) {
        super(repository);
    }

    @Override
    public VersionRepository getVersionRepository() {
        return versionRepository;
    }

    @Override
    public synchronized Vision save(Vision vision) throws CustomException {
        controler(vision);
        vision = super.save(vision);
        miseAJourDeLaVersion(vision, Version.MOTIF_AJOUT, vision.getIdentifiant());
        return vision;
    }

    private void controler(Vision vision) throws CustomException {
        if (vision.getLibelle() == null || vision.getLibelle().isEmpty()) {
            throw new CustomException(MPConstants.LE_LIBELLE_DE_LA_VISION_OBLIGATOIRE);
        }
        Optional<Vision> op = ((VisionRepository) repository).findByLibelle(vision.getLibelle());
        if (op.isPresent() && !op.get().getIdentifiant().equals(vision.getIdentifiant())) {
            throw new CustomException(MPConstants.LE_LIBELLE_DE_LA_VISION_EXIST);
        }
    }

    @Override
    public synchronized Vision saveAndFlush(Vision vision) throws CustomException {
        controler(vision);
        vision = super.saveAndFlush(vision);
        miseAJourDeLaVersion(vision, Version.MOTIF_MODIFICATION, vision.getIdentifiant());
        return vision;
    }

    @Override
    public boolean delete(Integer id) {
        Vision vision = findOne(id);
        if (super.delete(id)) {
            miseAJourDeLaVersion(vision, Version.MOTIF_SUPPRESSION, id);
            return true;
        }
        return false;
    }

    @Override
    public List<Vision> recupererLaListeVersionnee(Integer[] ints) {
        return ((VisionRepository) repository).recupererLaListeVersionnee(ints);
    }

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }
}
