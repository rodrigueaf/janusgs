package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.AbstractAuditingEntity;
import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.repository.BaseEntityRepository;
import com.gt.gestionsoi.repository.VersionRepository;
import com.gt.gestionsoi.service.IBaseEntityService;
import com.gt.gestionsoi.util.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

/**
 * Classe implémentant l'interface <code>BaseEntityService</code>
 *
 * @param <T> : L'entié
 * @param <I> : La clé primaire
 * @author <a href="mailto:ali.amadou@ace3i.com?">Yasser Ali</a>,
 */
public abstract class BaseEntityService<T extends AbstractAuditingEntity, I extends Serializable>
        extends GenericService<T, I>
        implements IBaseEntityService<T, I> {

    /**
     * Constructeur parent pour récupérer l'instance de chaque classe dérivé à
     * utiliser
     *
     * @param repository : une instance de repository
     */
    public BaseEntityService(BaseEntityRepository<T, I> repository) {
        super(repository);
    }

    @Override
    public Page<T> findByStateNotDeleted(Pageable p) {
        return ((BaseEntityRepository<T, I>) repository).findByStateNot(State.DELETED, p);
    }

    /**
     * @see com.gt.gestfinance.service.IBaseEntityService#findByState(com.gt.gestfinance.util.State, Pageable)
     */
    @Override
    public Page<T> findByState(State state, Pageable p) {
        return ((BaseEntityRepository<T, I>) repository).findByState(state, p);
    }

    @Override
    public boolean enabled(I i) {
        try {
            T t = repository.findOne(i);
            t.setState(State.ENABLED);
            repository.saveAndFlush(t);
            return true;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(BaseEntityService.class.getName()).log(Level.INFO, null, e);
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean disabled(I i) {
        try {
            T t = repository.findOne(i);
            t.setState(State.DISABLED);
            repository.saveAndFlush(t);
            return true;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(BaseEntityService.class.getName()).log(Level.INFO, null, e);
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteSoft(I id) {
        try {
            T t = repository.findOne(id);
            t.setState(State.DELETED);
            repository.saveAndFlush(t);
            return true;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(BaseEntityService.class.getName()).log(Level.INFO, null, e);
            logger.error(e.getMessage());
            return false;
        }
    }

    VersionRepository getVersionRepository() {
        return null;
    }

    void miseAJourDeLaVersion(T t, String type, I id) {
        miseAJourDeLaVersion(type, id, t.getClass().getSimpleName());
    }

    void miseAJourDeLaVersion(String type, I id, String attribut) {
        List<Version> vs = getVersionRepository().listerValeurDesc(attribut);
        Version version = new Version();
        version.setAttribut(attribut);
        version.setMotif(id.toString() + "/" + type);
        if (!vs.isEmpty()) {
            version.setValeur(vs.get(0).getValeur() + 1);
        } else {
            version.setValeur(Integer.parseInt(id.toString()));
        }
        getVersionRepository().save(version);
    }
}
