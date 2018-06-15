package com.gt.gestionsoi.entity;

import com.gt.base.entity.AbstractAuditingEntity;
import com.gt.base.util.BaseConstant;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Entité représentant un Journal
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
public abstract class Item extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer identifiant;
    @Column(nullable = false)
    String description;
    Short tauxAchevement;
    TypeItem typeItem;
    String observation;
    @ManyToOne
    @JoinColumn
    Categorie categorie;
    @ManyToOne
    @JoinColumn
    Categorie domaine;
    @ManyToOne
    @JoinColumn
    Projet projet;
    @ManyToOne
    @JoinColumn
    Processus processus;
    @ManyToOne
    @JoinColumn
    Objectif objectif;
    @ManyToOne
    @JoinColumn
    PrincipeValeur principeValeur;
    @ManyToOne
    @JoinColumn
    Vision vision;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date dateCreation;
    @LastModifiedDate
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date dateDerniereMaj = new Date();


    public Item() {
        // Do something
    }

    public Integer getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getTauxAchevement() {
        return tauxAchevement;
    }

    public void setTauxAchevement(Short tauxAchevement) {
        this.tauxAchevement = tauxAchevement;
    }

    public TypeItem getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(TypeItem typeItem) {
        this.typeItem = typeItem;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Processus getProcessus() {
        return processus;
    }

    public void setProcessus(Processus processus) {
        this.processus = processus;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    public PrincipeValeur getPrincipeValeur() {
        return principeValeur;
    }

    public void setPrincipeValeur(PrincipeValeur principeValeur) {
        this.principeValeur = principeValeur;
    }

    public Vision getVision() {
        return vision;
    }

    public void setVision(Vision vision) {
        this.vision = vision;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateDerniereMaj() {
        return dateDerniereMaj;
    }

    public void setDateDerniereMaj(Date dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
    }

    public Categorie getDomaine() {
        return domaine;
    }

    public void setDomaine(Categorie domaine) {
        this.domaine = domaine;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 5;
        return 31 * hash + Objects.hashCode(this.identifiant);
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        return BaseConstant.entityEqualById(
                this.getClass(), this, obj, "getIdentifiant");
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "Journal{" +
                "identifiant=" + identifiant +
                ", description='" + description + '\'' +
                ", tauxAchevement=" + tauxAchevement +
                ", typeItem=" + typeItem +
                ", observation='" + observation + '\'' +
                ", categorie=" + categorie +
                ", projet=" + projet +
                ", processus=" + processus +
                ", objectif=" + objectif +
                ", principeValeur=" + principeValeur +
                '}';
    }
}
