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

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class Customer {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String SEARCHKEY = "SEARCHKEY";
    public static final String TAXCATEGORY = "TAXCATEGORY";
    public static final String EMAIL = "EMAIL";

    @DatabaseField(id = true, columnName = ID)
    private String id;

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

    @DatabaseField(columnName = EMAIL, canBeNull = false)
    private String email;

    public String getId() {
        return ID;
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

}
