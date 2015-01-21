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
import com.openbravo.pos.util.Hashcypher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "CUSTOMERS")
public class Customer {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String SEARCHKEY = "SEARCHKEY";
    public static final String TAXCATEGORY = "TAXCATEGORY";
    public static final String EMAIL = "EMAIL";
    public static final String PROPERTIES = "PROPERTIES";

    private static final String LOGIN_PASSWORD_KEY = "customer.login.password";

    @DatabaseField(generatedId = true, columnName = ID)
    private UUID id;

    @DatabaseField(columnName = NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = SEARCHKEY, canBeNull = false)
    private String searchkey;

    @DatabaseField(foreign = true,
            columnName = TAXCATEGORY,
            foreignColumnName = TaxCategory.ID,
            foreignAutoRefresh = true,
            canBeNull = true)
    private TaxCategory taxCategory;

    @DatabaseField(columnName = EMAIL, canBeNull = true)
    private String email;

    @DatabaseField(columnName = PROPERTIES, dataType = DataType.BYTE_ARRAY)
    byte[] properties;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public TaxCategory getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(TaxCategory taxCategory) {
        this.taxCategory = taxCategory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        p.storeToXML(outputStream, "Customer properties", "UTF-8");
        this.properties = outputStream.toByteArray();
    }

    public String getPassword() throws IOException {
        return getProperties().getProperty(LOGIN_PASSWORD_KEY);
    }

    public void setPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, IOException {
        Properties p = getProperties();
        p.setProperty(LOGIN_PASSWORD_KEY, Hashcypher.hashString(password));
        setProperties(p);
    }

    public boolean isAuthentication(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, IOException {
        return Hashcypher.authenticate(password, getPassword());
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
        return name.equals(((Customer) other).name);
    }

}
