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
import com.openbravo.pos.sales.TaxesLogic;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import mobi.nordpos.dao.model.Tax;
import mobi.nordpos.dao.ormlite.TaxDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class TaxPersist implements PersistFactory {

    ConnectionSource connectionSource;
    TaxDao taxDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        taxDao = new TaxDao(connectionSource);
    }

    @Override
    public Tax read(Object taxCategoryId) throws SQLException {
        try {
            TaxesLogic taxLogic = new TaxesLogic(taxDao.queryForAll());
            return taxLogic.getTax((String) taxCategoryId, new Date());
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<Tax> readList() throws SQLException {
        try {
            return taxDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Tax find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = taxDao.queryBuilder();
            qb.where().like(column, value);
            return (Tax) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Tax add(Object tax) throws SQLException {
        try {
            return taxDao.createIfNotExists((Tax) tax);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object tax) throws SQLException {
        try {
            return taxDao.update((Tax) tax) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return taxDao.deleteById((String) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}
