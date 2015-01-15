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
import java.util.Date;
import java.util.UUID;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "STOCKDIARY")
public class StockDiary {

    public enum MovementReasonType {

        IN_PURCHASE(+1), IN_REFUND(+2), IN_MOVEMENT(+4), OUT_SALE(-1), OUT_REFUND(-2), OUT_BREAK(-3), OUT_MOVEMENT(-4), OUT_CROSSING(1000);
        private final int value;

        private MovementReasonType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final String ID = "ID";
    public static final String DATENEW = "DATENEW";
    public static final String REASON = "REASON";
    public static final String LOCATION = "LOCATION";
    public static final String PRODUCT = "PRODUCT";
    public static final String UNITS = "UNITS";
    public static final String PRICE = "PRICE";

    @DatabaseField(generatedId = true, columnName = ID)
    private UUID id;

    @DatabaseField(columnName = DATENEW, canBeNull = false, index = true, indexName = "STOCKDIARY_INX_1")
    private Date date;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignColumnName = Location.ID, columnName = LOCATION, canBeNull = false)
    private Location location;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignColumnName = Product.ID, columnName = PRODUCT, canBeNull = false)
    private Product product;

    @DatabaseField(columnName = REASON, canBeNull = false)
    private Integer reason;

    @DatabaseField(columnName = UNITS, canBeNull = false)
    private BigDecimal unit;

    @DatabaseField(columnName = PRICE, canBeNull = false)
    private BigDecimal price;

    public UUID getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
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

}
