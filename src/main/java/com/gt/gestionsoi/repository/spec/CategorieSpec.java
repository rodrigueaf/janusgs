package com.gt.gestionsoi.repository.spec;

import com.gt.gestionsoi.entity.Categorie;
import com.gt.gestionsoi.filtreform.CategorieFormulaireDeFiltre;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Specification de l'entité Categorie
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class CategorieSpec extends BaseSpecifications {

    private static Specifications spec;

    private CategorieSpec() {
        // DO
    }

    /**
     * Filtrer les données
     *
     * @param formulaireDeFiltre
     * @return
     */
    @SuppressWarnings({"unchecked", "null"})
    public static Specification<Categorie> allCriteres(CategorieFormulaireDeFiltre formulaireDeFiltre) {

        setSpec(Specifications.where((root, query, cb) -> cb.equal(cb.literal(1), 1)));

        if (formulaireDeFiltre.getCategorie() != null) {
            CategorieSpec.filtrerParLibelle(getSpec(), formulaireDeFiltre.getCategorie().getLibelle());
        }
        if (formulaireDeFiltre.getParent() != null) {
            if (formulaireDeFiltre.getParent()) {
                setSpec(getSpec().and((root, cq, cb) -> cb.isNull(root.get("categorieParent"))));
            }else{
                setSpec(getSpec().and((root, cq, cb) -> cb.isNotNull(root.get("categorieParent"))));
            }
        }

        return spec;
    }

    private static void filtrerParLibelle(Specifications spec, String libelle) {
        if (libelle != null && !libelle.isEmpty()) {
            setSpec(spec.and(containsLike("libelle", libelle)));
        }
    }

    public static Specifications getSpec() {
        return spec;
    }

    public static void setSpec(Specifications spec) {
        CategorieSpec.spec = spec;
    }
}
