package com.gt.gestionsoi.repository;

import com.gt.gestionsoi.entity.Categorie;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Le repository de l'entité Categorie
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
public interface CategorieRepository extends BaseEntityRepository<Categorie, Integer> {
    Optional<Categorie> findByLibelle(String libelle);

    @Query("select c from Categorie c left join fetch c.sousCategories s where c.identifiant = ?1")
    Optional<Categorie> findWithSousCategoriesByIdentifiant(Integer categorieId);
}
