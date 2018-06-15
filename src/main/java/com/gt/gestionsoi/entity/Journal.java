package com.gt.gestionsoi.entity;

import com.gt.base.util.BaseConstant;

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
@DiscriminatorValue("JOURNAL")
public class Journal extends Item {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.DATE)
    private Date dateRealisation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureDebutRealisation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureFinRealisation;
    private String recommandation;
    @ManyToOne
    @JoinColumn
    private Prevision prevision;


    public Journal() {
        // Do something
    }

    public Prevision getPrevision() {
        return prevision;
    }

    public void setPrevision(Prevision prevision) {
        this.prevision = prevision;
    }

    public Date getDateRealisation() {
        return dateRealisation;
    }

    public void setDateRealisation(Date dateRealisation) {
        this.dateRealisation = dateRealisation;
    }

    public Date getHeureDebutRealisation() {
        return heureDebutRealisation;
    }

    public void setHeureDebutRealisation(Date heureDebutRealisation) {
        this.heureDebutRealisation = heureDebutRealisation;
    }

    public Date getHeureFinRealisation() {
        return heureFinRealisation;
    }

    public void setHeureFinRealisation(Date heureFinRealisation) {
        this.heureFinRealisation = heureFinRealisation;
    }

    public String getRecommandation() {
        return recommandation;
    }

    public void setRecommandation(String recommandation) {
        this.recommandation = recommandation;
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
                "super" + super.toString() +
                "dateRealisation=" + dateRealisation +
                ", heureDebutRealisation=" + heureDebutRealisation +
                ", heureFinRealisation=" + heureFinRealisation +
                ", recommandation='" + recommandation + '\'' +
                '}';
    }
}
