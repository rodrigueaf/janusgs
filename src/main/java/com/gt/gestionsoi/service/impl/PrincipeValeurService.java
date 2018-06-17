package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.PrincipeValeur;
import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.repository.PrincipeValeurRepository;
import com.gt.gestionsoi.repository.VersionRepository;
import com.gt.gestionsoi.service.IPrincipeValeurService;
import com.gt.gestionsoi.util.MPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe Service de l'entité ModePaiement
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 25/09/2017
 */
@Service
public class PrincipeValeurService extends BaseEntityService<PrincipeValeur, Integer> implements IPrincipeValeurService {

    private VersionRepository versionRepository;

    @Autowired
    public PrincipeValeurService(PrincipeValeurRepository repository) {
        super(repository);
    }

    @Override
    public VersionRepository getVersionRepository() {
        return versionRepository;
    }

    @Override
    public synchronized PrincipeValeur save(PrincipeValeur principeValeur) throws CustomException {
        controler(principeValeur);
        principeValeur = super.save(principeValeur);
        miseAJourDeLaVersion(principeValeur, Version.MOTIF_AJOUT, principeValeur.getIdentifiant());
        return principeValeur;
    }

    private void controler(PrincipeValeur categorie) throws CustomException {
        if (categorie.getLibelle() == null || categorie.getLibelle().isEmpty()) {
            throw new CustomException(MPConstants.LE_LIBELLE_DE_LA_VALEUR_OBLIGATOIRE);
        }
        Optional<PrincipeValeur> op = ((PrincipeValeurRepository) repository).findByLibelle(categorie.getLibelle());
        if (op.isPresent() && !op.get().getIdentifiant().equals(categorie.getIdentifiant())) {
            throw new CustomException(MPConstants.LE_LIBELLE_DE_LA_VALEUR_EXIST);
        }
    }

    @Override
    public synchronized PrincipeValeur saveAndFlush(PrincipeValeur principeValeur) throws CustomException {
        controler(principeValeur);
        principeValeur = super.saveAndFlush(principeValeur);
        miseAJourDeLaVersion(principeValeur, Version.MOTIF_MODIFICATION, principeValeur.getIdentifiant());
        return principeValeur;
    }

    @Override
    public boolean delete(Integer id) {
        PrincipeValeur principeValeur = findOne(id);
        if (super.delete(id)) {
            miseAJourDeLaVersion(principeValeur, Version.MOTIF_SUPPRESSION, id);
            return true;
        }
        return false;
    }

    @Override
    public List<PrincipeValeur> recupererLaListeVersionnee(Integer[] ints) {
        return ((PrincipeValeurRepository) repository).recupererLaListeVersionnee(ints);
    }

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }
}
