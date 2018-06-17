package com.gt.gestionsoi.service;

import com.gt.gestionsoi.entity.Processus;
import com.gt.gestionsoi.exception.CustomException;

import java.util.List;

/**
 * Interface Service de l'entité Processus
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public interface IProcessusService extends IBaseEntityService<Processus, Integer> {
    List<Processus> recupererLaListeVersionnee(Integer[] ints);

    boolean supprimer(Integer id) throws CustomException;
}
