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
import mobi.nordpos.dao.model.SharedTicket;
import mobi.nordpos.dao.ormlite.SharedTicketDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class SharedTicketPersist implements PersistFactory {

    ConnectionSource connectionSource;
    SharedTicketDao sharedTicketDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        sharedTicketDao = new SharedTicketDao(connectionSource);
    }

    @Override
    public SharedTicket read(Object id) throws SQLException {
        try {
            return sharedTicketDao.queryForId((String) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<SharedTicket> readList() throws SQLException {
        try {
            return sharedTicketDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public SharedTicket find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = sharedTicketDao.queryBuilder();
            qb.where().like(column, value);
            return (SharedTicket) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public SharedTicket add(Object ticket) throws SQLException {
        try {
            return sharedTicketDao.createIfNotExists((SharedTicket) ticket);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object ticket) throws SQLException {
        try {
            return sharedTicketDao.update((SharedTicket) ticket) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return sharedTicketDao.deleteById((String) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}
