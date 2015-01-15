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
import mobi.nordpos.dao.model.User;
import mobi.nordpos.dao.ormlite.UserDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class UserPersist implements PersistFactory {

    ConnectionSource connectionSource;
    UserDao userDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        userDao = new UserDao(connectionSource);
    }

    @Override
    public User read(Object id) throws SQLException {
        try {
            return userDao.queryForId((String) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<User> readList() throws SQLException {
        try {
            QueryBuilder qb = userDao.queryBuilder().orderBy(User.NAME, true);
            qb.where().isNotNull(User.ID);
            return qb.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public User find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = userDao.queryBuilder();
            qb.where().like(column, value);
            return (User) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public User add(Object user) throws SQLException {
        try {
            return userDao.createIfNotExists((User) user);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object user) throws SQLException {
        try {
            return userDao.update((User) user) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return userDao.deleteById((String) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}
