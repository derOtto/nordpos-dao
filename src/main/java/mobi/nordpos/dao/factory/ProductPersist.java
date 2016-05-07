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
package mobi.nordpos.dao.factory;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.List;
import mobi.nordpos.dao.model.Product;
import mobi.nordpos.dao.model.ProductCategory;
import mobi.nordpos.dao.ormlite.ProductCategoryDao;
import mobi.nordpos.dao.ormlite.ProductDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class ProductPersist implements PersistFactory {

    ConnectionSource connectionSource;
    ProductDao productDao;
    ProductCategoryDao categoryDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        productDao = new ProductDao(connectionSource);
        categoryDao = new ProductCategoryDao(connectionSource);
    }

    @Override
    public Product read(Object id) throws SQLException {
        try {
            return productDao.queryForId((String) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<Product> readList() throws SQLException {
        try {
            QueryBuilder qb = productDao.queryBuilder().orderBy(Product.NAME, true);
            qb.where().isNotNull(Product.ID);
            return qb.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
    
    public List<Product> readList(ProductCategory category) throws SQLException {
        try {
            QueryBuilder productQB = productDao.queryBuilder().orderBy(Product.CATEGORY, true);
            QueryBuilder categoryQB = categoryDao.queryBuilder().orderBy(ProductCategory.ID, true);
            productQB.join(categoryQB);
            productQB.where().eq(Product.CATEGORY, category.getId());
            return productQB.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }    

    @Override
    public Product find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = productDao.queryBuilder();
            qb.where().like(column, value);
            return (Product) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Product add(Object product) throws SQLException {
        try {
            return productDao.createIfNotExists((Product) product);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object product) throws SQLException {
        try {
            return productDao.update((Product) product) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return productDao.deleteById((String) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public List<Product> listByCodePrefix(String prefix) throws SQLException {
        try {
            QueryBuilder qb = productDao.queryBuilder();
            qb.where().like(Product.CODE, prefix.concat("%"));
            return qb.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}
