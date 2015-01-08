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
import java.io.IOException;
import java.util.List;
import mobi.nordpos.dao.util.ImagePreview;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "CATEGORIES")
public class ProductCategory {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String CODE = "CODE";
    public static final String IMAGE = "IMAGE";

    @DatabaseField(id = true, columnName = ID)
    private String id;

    @DatabaseField(columnName = NAME, unique = true, canBeNull = false)
    private String name;

    @DatabaseField(columnName = CODE, canBeNull = true)
    private String code;

    @DatabaseField(columnName = IMAGE, dataType = DataType.BYTE_ARRAY, canBeNull = true)
    private byte[] image;

    @ForeignCollectionField(orderAscending = true, orderColumnName = Product.NAME, eager = true)
    private ForeignCollection<Product> productCollection;

    @DatabaseField(persisted = false)
    private List<Product> productList;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ForeignCollection<Product> getProductCollection() {
        return this.productCollection;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getProductListSize() {
        return productList.size();
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
        return name.equals(((ProductCategory) other).name);
    }

}
