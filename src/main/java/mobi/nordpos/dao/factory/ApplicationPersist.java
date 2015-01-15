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
import mobi.nordpos.dao.model.Application;
import mobi.nordpos.dao.ormlite.ApplicationDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class ApplicationPersist implements PersistFactory {

    ConnectionSource connectionSource;
    ApplicationDao applicationDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        applicationDao = new ApplicationDao(connectionSource);
    }

    @Override
    public Application read(Object id) throws SQLException {
        try {
            return applicationDao.queryForId((String) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<Application> readList() throws SQLException {
        try {
            return applicationDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Application find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = applicationDao.queryBuilder();
            qb.where().like(column, value);
            return (Application) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Application add(Object application) throws SQLException {
        try {
            return applicationDao.createIfNotExists((Application) application);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object application) throws SQLException {
        try {
            return applicationDao.update((Application) application) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return applicationDao.deleteById((String) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}
