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
package mobi.nordpos.dao.ormlite;

import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mobi.nordpos.dao.model.Product;
import mobi.nordpos.dao.model.ProductCategory;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class ProductCategoryPersist implements PersistFactory {

    ConnectionSource connectionSource;
    ProductCategoryDao productCategoryDao;
    
    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        productCategoryDao = new ProductCategoryDao(connectionSource);
    }

    @Override
    public ProductCategory read(Object id) throws SQLException {
        try {
            return productCategoryDao.queryForId((String) id);
        } finally {            
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
    
    @Override
    public List<ProductCategory> readList() throws SQLException {
        try {
            QueryBuilder qb = productCategoryDao.queryBuilder().orderBy(ProductCategory.NAME, true);
            qb.where().isNotNull(ProductCategory.ID);
            return qb.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    } 

    @Override
    public ProductCategory find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = productCategoryDao.queryBuilder();
            qb.where().like(column, value);
            return (ProductCategory) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
    
    @Override
    public ProductCategory add(Object category) throws SQLException {
        try {
            return productCategoryDao.createIfNotExists((ProductCategory) category);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object category) throws SQLException {
        try {
            return productCategoryDao.update((ProductCategory) category) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return productCategoryDao.deleteById((String) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public List<Product> readProductList(ProductCategory category) throws SQLException {
        CloseableWrappedIterable<Product> iterator = category.getProductCollection().getWrappedIterable();
        List<Product> list = new ArrayList<>();
        try {
            for (Product product : iterator) {
                list.add(product);
            }
            return list;
        } finally {
            iterator.close();
        }
    }
}
