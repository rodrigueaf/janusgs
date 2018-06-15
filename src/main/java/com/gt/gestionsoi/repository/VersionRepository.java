package com.gt.gestionsoi.repository;

import com.gt.gestionsoi.entity.Version;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VersionRepository extends BaseEntityRepository<Version, Integer> {

    @Query("select c from Version c where c.attribut = :attribut and c.valeur > :valeur order by valeur desc")
    List<Version> selectionnerListe(@Param("attribut") String attribut,
                                           @Param("valeur") Integer valeur);

    @Query("select c from Version c where c.attribut = :attribut order by valeur desc")
    List<Version> listerValeurDesc(@Param("attribut") String attribut);
}
