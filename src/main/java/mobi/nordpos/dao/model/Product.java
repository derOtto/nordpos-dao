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

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import mobi.nordpos.dao.util.ImagePreview;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "PRODUCTS")
public class Product {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String CODE = "CODE";
    public static final String REFERENCE = "REFERENCE";
    public static final String PRICEBUY = "PRICEBUY";
    public static final String PRICESELL = "PRICESELL";
    public static final String CATEGORY = "CATEGORY";
    public static final String TAXCAT = "TAXCAT";
    public static final String IMAGE = "IMAGE";
    public static final String ISCOM = "ISCOM";
    public static final String ATTRIBUTES = "ATTRIBUTES";
    public static final String ATTRIBUTESET = "ATTRIBUTESET_ID";

    @DatabaseField(id = true, columnName = ID)
    private String id;

    @DatabaseField(columnName = NAME, unique = true, canBeNull = false)
    private String name;

    @DatabaseField(columnName = CODE, unique = true, canBeNull = false)
    private String code;

    @DatabaseField(columnName = REFERENCE, unique = true, canBeNull = false)
    private String reference;

    @DatabaseField(columnName = PRICEBUY, canBeNull = false)
    private BigDecimal pricebuy;

    @DatabaseField(columnName = PRICESELL, canBeNull = false)
    private BigDecimal pricesell;

    @DatabaseField(columnName = IMAGE, dataType = DataType.BYTE_ARRAY, canBeNull = true)
    private byte[] image;

    @DatabaseField(columnName = ISCOM, defaultValue = "false")
    private Boolean com;

    @DatabaseField(columnName = ATTRIBUTES, dataType = DataType.BYTE_ARRAY)
    byte[] attributes;

    @DatabaseField(foreign = true,
            columnName = CATEGORY,
            foreignColumnName = ProductCategory.ID,
            foreignAutoRefresh = true,
            canBeNull = false)
    private ProductCategory productCategory;

    @DatabaseField(foreign = true,
            columnName = TAXCAT,
            foreignColumnName = TaxCategory.ID,
            foreignAutoRefresh = true,
            canBeNull = false)
    private TaxCategory taxCategory;

    @DatabaseField(foreign = true,
            columnName = ATTRIBUTESET,
            foreignColumnName = AttributeSet.ID,
            foreignAutoRefresh = true,
            canBeNull = false)
    private AttributeSet attributeSet;

    @DatabaseField(persisted = false)
    private Tax tax;

    @DatabaseField(persisted = false)
    private BigDecimal taxPriceSell;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getPriceBuy() {
        return pricebuy;
    }

    public void setPriceBuy(BigDecimal pricebuy) {
        this.pricebuy = pricebuy;
    }

    public BigDecimal getPriceSell() {
        return pricesell;
    }

    public void setPriceSell(BigDecimal pricesell) {
        this.pricesell = pricesell;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Boolean getCom() {
        return com;
    }

    public void setCom(Boolean com) {
        this.com = com;
    }

    public BigDecimal getTaxPriceSell() {
        return pricesell
                .multiply(getTax().getRate().add(BigDecimal.ONE))
                .setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }

    public void setTaxPriceSell(BigDecimal taxPriceSell) {
        this.taxPriceSell = taxPriceSell;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public TaxCategory getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(TaxCategory taxCategory) {
        this.taxCategory = taxCategory;
    }

    public AttributeSet getAttributeSet() {
        return attributeSet;
    }

    public void setAttributeSet(AttributeSet attributeSet) {
        this.attributeSet = attributeSet;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public byte[] getAttributes() {
        return attributes;
    }

    public void setAttributes(byte[] attributes) {
        this.attributes = attributes;
    }

    public Properties getProperties() throws IOException {
        Properties properties = new Properties();
        if (this.attributes != null) {
            properties.loadFromXML(new ByteArrayInputStream(this.attributes));
        }
        return properties;
    }

    public void setProperties(Properties properties) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        properties.storeToXML(outputStream, "Product attributes", "UTF-8");
        setAttributes(outputStream.toByteArray());
    }

    public byte[] getImageThumbnail(int size) throws IOException {
        return ImagePreview.createThumbnail(getImage(), size);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        return name.equals(((Product) other).name);
    }

}
