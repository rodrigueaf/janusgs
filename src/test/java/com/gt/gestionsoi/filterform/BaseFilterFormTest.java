/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt. gestionsoi.filterform;

import com.gt. gestionsoi.controller.entity.User;
import com.gt. gestionsoi.filtreform.BaseFilterForm;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 */
public class BaseFilterFormTest {

    /**
     * Test of setPage method, of class BaseFilterForm.
     */
    @Test
    public void testSetPage() {
        Integer page = null;
        BaseFilterForm instance = new BaseFilterFormImpl();
        instance.setPage(page);
    }

    /**
     * Test of setSize method, of class BaseFilterForm.
     */
    @Test
    public void testSetSize() {
        Integer size = null;
        BaseFilterForm instance = new BaseFilterFormImpl();
        instance.setSize(size);
    }

    /**
     * Implémentation par défaut
     */
    public class BaseFilterFormImpl extends BaseFilterForm {

        /**
         * Le constructeur
         *
         * @return
         */
        @Override
        public Specification<User> getCriteres() {
            return null;
        }
    }

}
