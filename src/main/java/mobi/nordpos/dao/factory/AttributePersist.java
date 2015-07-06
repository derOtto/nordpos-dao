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

import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mobi.nordpos.dao.model.Attribute;
import mobi.nordpos.dao.model.AttributeValue;
import mobi.nordpos.dao.ormlite.AttributeDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class AttributePersist implements PersistFactory {

    ConnectionSource connectionSource;
    AttributeDao attributeDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        attributeDao = new AttributeDao(connectionSource);
    }

    @Override
    public Attribute read(Object id) throws SQLException {
        try {
            return attributeDao.queryForId((UUID) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<Attribute> readList() throws SQLException {
        try {
            QueryBuilder qb = attributeDao.queryBuilder().orderBy(Attribute.NAME, true);
            qb.where().isNotNull(Attribute.ID);
            return qb.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Attribute find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = attributeDao.queryBuilder();
            qb.where().like(column, value);
            return (Attribute) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Attribute add(Object attribute) throws SQLException {
        try {
            return attributeDao.createIfNotExists((Attribute) attribute);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object attribute) throws SQLException {
        try {
            return attributeDao.update((Attribute) attribute) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return attributeDao.deleteById((UUID) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public List<AttributeValue> readAttributeValueList(Attribute attribute) throws SQLException {
        CloseableWrappedIterable<AttributeValue> iterator = attribute.getAttributeValueCollection().getWrappedIterable();
        List<AttributeValue> list = new ArrayList<>();
        try {
            for (AttributeValue value : iterator) {
                list.add(value);
            }
            return list;
        } finally {
            iterator.close();
        }
    }
}
