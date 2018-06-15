package com.gt.gestionsoi.service;

import com.gt.gestionsoi.entity.Categorie;
import com.gt.gestionsoi.exception.CustomException;

import java.util.List;

/**
 * Interface Service de l'entité Categorie
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public interface ICategorieService extends IBaseEntityService<Categorie, Integer> {

    boolean supprimer(Integer categorieId) throws CustomException;

    List<Categorie> recupererLaListeVersionnee(Integer[] ints);
}
