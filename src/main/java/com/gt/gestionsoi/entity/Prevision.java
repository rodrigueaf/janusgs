package com.gt.gestionsoi.entity;

import com.gt.gestionsoi.util.BaseConstant;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

/**
 * Entité représentant un Prevision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@Entity
@DiscriminatorValue("PREVISION")
public class Prevision extends Item {

    private static final long serialVersionUID = 1L;

    private EtatItem etatItem;
    @Temporal(TemporalType.DATE)
    private Date datePrevueRealisation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHeureEcheance;
    @Temporal(TemporalType.TIME)
    private Date heureDebut;
    @Temporal(TemporalType.TIME)
    private Date heureFin;
    private Short estimationDuree;
    private Float urgence;
    private Float importance;
    private Integer duree;
    @Temporal(TemporalType.DATE)
    private Date periodeDebutPrevueRealisation;
    @Temporal(TemporalType.DATE)
    private Date periodeFinPrevueRealisation;
    private Finalite finalite;


    public Prevision() {
        // Do something
    }

    public EtatItem getEtatItem() {
        return etatItem;
    }

    public void setEtatItem(EtatItem etatItem) {
        this.etatItem = etatItem;
    }

    public Date getDatePrevueRealisation() {
        return datePrevueRealisation;
    }

    public void setDatePrevueRealisation(Date datePrevueRealisation) {
        this.datePrevueRealisation = datePrevueRealisation;
    }

    public Finalite getFinalite() {
        return finalite;
    }

    public void setFinalite(Finalite finalite) {
        this.finalite = finalite;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Date getDateHeureEcheance() {
        return dateHeureEcheance;
    }

    public void setDateHeureEcheance(Date dateHeureEcheance) {
        this.dateHeureEcheance = dateHeureEcheance;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public Short getEstimationDuree() {
        return estimationDuree;
    }

    public void setEstimationDuree(Short estimationDuree) {
        this.estimationDuree = estimationDuree;
    }

    public Float getUrgence() {
        return urgence;
    }

    public void setUrgence(Float urgence) {
        this.urgence = urgence;
    }

    public Float getImportance() {
        return importance;
    }

    public void setImportance(Float importance) {
        this.importance = importance;
    }

    public Date getPeriodeDebutPrevueRealisation() {
        return periodeDebutPrevueRealisation;
    }

    public void setPeriodeDebutPrevueRealisation(Date periodeDebutPrevueRealisation) {
        this.periodeDebutPrevueRealisation = periodeDebutPrevueRealisation;
    }

    public Date getPeriodeFinPrevueRealisation() {
        return periodeFinPrevueRealisation;
    }

    public void setPeriodeFinPrevueRealisation(Date periodeFinPrevueRealisation) {
        this.periodeFinPrevueRealisation = periodeFinPrevueRealisation;
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
        return "Prevision{" +
                "super" + super.toString() +
                "etatItem=" + etatItem +
                ", datePrevueRealisation=" + datePrevueRealisation +
                ", dateHeureEcheance=" + dateHeureEcheance +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", estimationDuree=" + estimationDuree +
                ", urgence=" + urgence +
                ", importance=" + importance +
                ", periodeDebutPrevueRealisation=" + periodeDebutPrevueRealisation +
                ", periodeFinPrevueRealisation=" + periodeFinPrevueRealisation +
                '}';
    }
}
