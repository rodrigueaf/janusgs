package com.gt.gestionsoi.service;

import com.gt.base.service.IBaseEntityService;
import com.gt.gestionsoi.entity.Objectif;

import java.util.List;

/**
 * Interface Service de l'entité Objectif
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public interface IObjectifService extends IBaseEntityService<Objectif, Integer> {
    List<Objectif> recupererLaListeVersionnee(Integer[] ints);
}
