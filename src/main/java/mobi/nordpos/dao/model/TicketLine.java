/**
 * Copyright (c) 2012-2015 Nord Trading Network.
 *
 * http://www.nordpos.mobi
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package mobi.nordpos.dao.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "TICKETLINES")
public class TicketLine {

    public static final String TICKET = "TICKET";
    public static final String LINE = "LINE";
    public static final String PRODUCT = "PRODUCT";
    public static final String ATTRIBUTESETINSTANCE_ID = "ATTRIBUTESETINSTANCE_ID";
    public static final String UNITS = "UNITS";
    public static final String PRICE = "PRICE";
    public static final String TAXID = "TAXID";
    public static final String ATTRIBUTES = "ATTRIBUTES";
    public static final String ID = "ID";

    @DatabaseField(id = true, useGetSet = true, columnName = ID)
    private String id;

    @DatabaseField(columnName = LINE, canBeNull = false)
    private Integer number;

    @DatabaseField(columnName = UNITS, canBeNull = false)
    private BigDecimal unit;

    @DatabaseField(columnName = PRICE, canBeNull = false)
    private BigDecimal price;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TAXID, canBeNull = false)
    private Tax tax;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TICKET, canBeNull = false)
    private Ticket ticket;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1, columnName = PRODUCT, canBeNull = true)
    private Product product;

    public String getId() {
        if (ticket == null) {
            return number.toString();
        } else {
            return ticket.getId().toString() + "-" + number.toString();
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getUnit() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getSellPrice() {
        return getPrice().setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getSubTotal() {
        return getUnit().multiply(getSellPrice()).setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getTaxPrice() {
        return getSellPrice().add(getTaxAmount()).setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getTaxAmount() {
        return getPrice().multiply(getTaxRate()).setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getTaxAmountSubTotal() {
        return getUnit().multiply(getTaxAmount()).setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getTaxSubTotal() {
        return getSubTotal().add(getTaxAmountSubTotal()).setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getTaxRate() {
        return tax.getRate();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        try {
            return id.equals(((TicketLine) other).id);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }
}
