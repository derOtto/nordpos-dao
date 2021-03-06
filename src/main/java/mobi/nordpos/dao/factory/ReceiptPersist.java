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
import java.util.UUID;
import mobi.nordpos.dao.model.Receipt;
import mobi.nordpos.dao.ormlite.ReceiptDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class ReceiptPersist implements PersistFactory {

    ConnectionSource connectionSource;
    ReceiptDao receiptDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        receiptDao = new ReceiptDao(connectionSource);
    }

    @Override
    public Receipt read(Object id) throws SQLException {
        try {
            return receiptDao.queryForId((UUID) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<Receipt> readList() throws SQLException {
        try {
            return receiptDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Receipt find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = receiptDao.queryBuilder();
            qb.where().like(column, value);
            return (Receipt) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Receipt add(Object receipt) throws SQLException {
        try {
            return receiptDao.createIfNotExists((Receipt) receipt);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object receipt) throws SQLException {
        try {
            return receiptDao.update((Receipt) receipt) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return receiptDao.deleteById((UUID) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

}
