package com.gt.gestionsoi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gt.base.entity.AbstractAuditingEntity;
import com.gt.base.util.BaseConstant;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entité représentant un Projet
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@Entity
@Table
public class Projet extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identifiant;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String libelle;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "projet_categorie",
            joinColumns = {
                    @JoinColumn(name = "projet_id", referencedColumnName = "identifiant")},
            inverseJoinColumns = {
                    @JoinColumn(name = "categorie_id", referencedColumnName = "identifiant")})
    @BatchSize(size = 20)
    private Set<Categorie> categories = new HashSet<>();

    public Projet() {
        // Do something
    }

    public Projet(String name) {
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
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
        return "Projet{" +
                "identifiant=" + identifiant +
                ", libelle='" + libelle + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
