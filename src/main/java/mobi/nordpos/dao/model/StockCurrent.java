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

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "STOCKCURRENT")
public class StockCurrent {

    public static final String LOCATION = "LOCATION";
    public static final String PRODUCT = "PRODUCT";
    public static final String ATTRIBUTESETINSTANCE_ID = "ATTRIBUTESETINSTANCE_ID";
    public static final String UNITS = "UNITS";
    public static final String ID = "ID";

    @DatabaseField(id = true, useGetSet = true, columnName = ID)
    private String id;

    @DatabaseField(columnName = UNITS, canBeNull = false)
    private BigDecimal unit;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = LOCATION, canBeNull = false)
    private Location location;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1, columnName = PRODUCT, canBeNull = true)
    private Product product;
    
    public String getId() {
        return location.getId() + "." + product.getId();
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getUnit() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        try {
            return id.equals(((StockCurrent) other).id);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }
}
