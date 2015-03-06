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

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "RECEIPTS")
public class Receipt {

    public static final String ID = "ID";
    public static final String MONEY = "MONEY";
    public static final String DATENEW = "DATENEW";
    public static final String ATTRIBUTES = "ATTRIBUTES";

    @DatabaseField(generatedId = true, columnName = ID)
    private UUID id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignColumnName = ClosedCash.MONEY, columnName = MONEY, canBeNull = false, index = true, indexName = "RECEIPTS_MONEY_INX")
    private ClosedCash closedCash;

    @DatabaseField(columnName = DATENEW, canBeNull = false, index = true, indexName = "RECEIPTS_INX_1")
    private Date date;

    @DatabaseField(columnName = ATTRIBUTES, dataType = DataType.BYTE_ARRAY)
    byte[] properties;

    @ForeignCollectionField
    ForeignCollection<Payment> paymentCollection;

    @DatabaseField(persisted = false)
    private List<Payment> paymentList;

    @ForeignCollectionField
    ForeignCollection<TaxLine> taxLineCollection;

    @DatabaseField(persisted = false)
    private List<TaxLine> taxList;

    public UUID getId() {
        return id;
    }

    public ClosedCash getClosedCash() {
        return closedCash;
    }

    public void setClosedCash(ClosedCash closedCash) {
        this.closedCash = closedCash;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Properties getProperties() throws IOException {
        Properties p = new Properties();
        if (this.properties != null) {
            p.loadFromXML(new ByteArrayInputStream(this.properties));
        }
        return p;
    }

    public void setProperties(Properties p) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        p.storeToXML(outputStream, "Receipt properties", "UTF-8");
        this.properties = outputStream.toByteArray();
    }

    public ForeignCollection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public ForeignCollection<TaxLine> getTaxLineCollection() {
        return taxLineCollection;
    }

    public List<TaxLine> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<TaxLine> taxList) {
        this.taxList = taxList;
    }

}
