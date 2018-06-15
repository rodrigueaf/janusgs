package com.gt.gestionsoi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gt.base.entity.AbstractAuditingEntity;
import com.gt.base.util.BaseConstant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

/**
 * Entité représentant un profil
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@Entity
@Table
public class Categorie extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identifiant;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String libelle;
    @ManyToOne
    @JoinColumn
    private Categorie categorieParent;
    @JsonIgnore
    @OneToMany(mappedBy = "categorieParent", fetch = FetchType.LAZY)
    private Set<Categorie> sousCategories;

    public Categorie() {
        // Do something
    }

    public Categorie(String libelle) {
        this.libelle = libelle;
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

    public Categorie getCategorieParent() {
        return categorieParent;
    }

    public void setCategorieParent(Categorie categorieParent) {
        this.categorieParent = categorieParent;
    }

    public Set<Categorie> getSousCategories() {
        return sousCategories;
    }

    public void setSousCategories(Set<Categorie> sousCategories) {
        this.sousCategories = sousCategories;
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
