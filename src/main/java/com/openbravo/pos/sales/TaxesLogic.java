//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2008-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.openbravo.pos.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mobi.nordpos.dao.model.Customer;
import mobi.nordpos.dao.model.Tax;
import mobi.nordpos.dao.model.TaxCategory;

/**
 *
 * @author adrianromero
 * @author Andrey Svininykh <svininykh@gmail.com>
 * @author NORD POS 3
 */
public class TaxesLogic {

    private List<Tax> taxlist;

    private Map<String, TaxesLogicElement> taxtrees;

    public TaxesLogic(List<Tax> taxlist) {
        this.taxlist = taxlist;

        taxtrees = new HashMap<>();

        // Order the taxlist by Application Order...
        List<Tax> taxlistordered = new ArrayList<>();
        taxlistordered.addAll(taxlist);

        Comparator<Tax> comparatorTax = new Comparator<Tax>() {
            @Override
            public int compare(Tax o1, Tax o2) {
                return o1.getApplicationRateOrder().compareTo(o2.getApplicationRateOrder());
            }
        };

        Collections.sort(taxlistordered, comparatorTax);

        // Generate the taxtrees
        HashMap<String, TaxesLogicElement> taxorphans = new HashMap<String, TaxesLogicElement>();

        for (Tax t : taxlistordered) {

            TaxesLogicElement te = new TaxesLogicElement(t);

            // get the parent
            TaxesLogicElement teparent = taxtrees.get(t.getParentId());
            if (teparent == null) {
                // orphan node
                teparent = taxorphans.get(t.getParentId());
                if (teparent == null) {
                    teparent = new TaxesLogicElement(null);
                    taxorphans.put(t.getParentId(), teparent);
                }
            }

            teparent.getSons().add(te);

            // Does it have orphans ?
            teparent = taxorphans.get(t.getId());
            if (teparent != null) {
                // get all the sons
                te.getSons().addAll(teparent.getSons());
                // remove the orphans
                taxorphans.remove(t.getId());
            }

            // Add it to the tree...
            taxtrees.put(t.getId(), te);
        }
    }

    public BigDecimal getTaxRate(String tcid, Date date) {
        return getTaxRate(tcid, date, null);
    }

    public BigDecimal getTaxRate(TaxCategory tc, Date date) {
        return getTaxRate(tc, date, null);
    }

    public BigDecimal getTaxRate(TaxCategory tc, Date date, Customer customer) {
        if (tc == null) {
            return BigDecimal.ZERO;
        } else {
            return getTaxRate(tc.getId(), date, customer);
        }
    }

    public BigDecimal getTaxRate(String tcid, Date date, Customer customer) {

        if (tcid == null) {
            return BigDecimal.ZERO;
        } else {
            Tax tax = getTax(tcid, date, customer);
            if (tax == null) {
                return BigDecimal.ZERO;
            } else {
                return tax.getRate();
            }
        }
    }

    public Tax getTax(String tcid, Date date) {
        return getTax(tcid, date, null);
    }

    public Tax getTax(TaxCategory tc, Date date) {
        return getTax(tc.getId(), date, null);
    }

    public Tax getTax(TaxCategory tc, Date date, Customer customer) {
        return getTax(tc.getId(), date, customer);
    }

    public Tax getTax(String tcid, Date date, Customer customer) {

        Tax candidatetax = null;
        Tax defaulttax = null;

        for (Tax tax : taxlist) {
            if (tax.getParentId() == null && tax.getCategoryId().equals(tcid) && tax.getValidFrom().compareTo(date) <= 0) {

                if (candidatetax == null || tax.getValidFrom().compareTo(candidatetax.getValidFrom()) > 0) {
                    // is valid date
                    if ((customer == null || customer.getTaxCategory().getId() == null) && tax.getCustCategoryId() == null) {
                        candidatetax = tax;
                    } else if (customer != null && customer.getTaxCategory().getId() != null && customer.getTaxCategory().getId().equals(tax.getCustCategoryId())) {
                        candidatetax = tax;
                    }
                }

                if (tax.getCustCategoryId() == null) {
                    if (defaulttax == null || tax.getValidFrom().compareTo(defaulttax.getValidFrom()) > 0) {
                        defaulttax = tax;
                    }
                }
            }
        }

        return candidatetax == null ? defaulttax : candidatetax;
    }
}
