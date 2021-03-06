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
import mobi.nordpos.dao.model.TicketNumber;
import mobi.nordpos.dao.ormlite.TicketNumberDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class TicketNumberPersist implements PersistFactory {

    ConnectionSource connectionSource;
    TicketNumberDao ticketNumberDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        ticketNumberDao = new TicketNumberDao(connectionSource);
    }

    @Override
    public TicketNumber read(Object id) throws SQLException {
        try {
            return ticketNumberDao.queryForId((Integer) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<TicketNumber> readList() throws SQLException {
        try {
            return ticketNumberDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public TicketNumber find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = ticketNumberDao.queryBuilder();
            qb.where().like(column, value);
            return (TicketNumber) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public TicketNumber add(Object ticketNumber) throws SQLException {
        try {
            return ticketNumberDao.createIfNotExists((TicketNumber) ticketNumber);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object ticketNumber) throws SQLException {
        TicketNumber number = (TicketNumber) ticketNumber;
        try {
            return ticketNumberDao.updateId(number, number.getId() + 1) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return ticketNumberDao.deleteById((Integer) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

}
