package com.gt.gestionsoi.entity;

import com.gt.gestionsoi.util.BaseConstant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Entité représentant un Vision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@Entity
@Table
public class Vision extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identifiant;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String libelle;

    public Vision() {
        // Do something
    }

    public Vision(String name) {
        this.libelle = name;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.identifiant);
        return 31 * hash + Objects.hashCode(this.libelle);
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        return BaseConstant.entityEqualById(
                this.getClass(), this, obj, "getIdentifiant", "getLibelle");
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "Categorie{" +
                "identifiant=" + identifiant +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
