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
import mobi.nordpos.dao.model.Ticket;
import mobi.nordpos.dao.ormlite.TicketDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class TicketPersist implements PersistFactory {

    ConnectionSource connectionSource;
    TicketDao ticketDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        ticketDao = new TicketDao(connectionSource);
    }

    @Override
    public Ticket read(Object id) throws SQLException {
        try {
            return ticketDao.queryForId((UUID) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<Ticket> readList() throws SQLException {
        try {
            return ticketDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Ticket find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = ticketDao.queryBuilder();
            qb.where().like(column, value);
            return (Ticket) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Ticket add(Object ticket) throws SQLException {
        try {
            return ticketDao.createIfNotExists((Ticket) ticket);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object ticket) throws SQLException {
        try {
            return ticketDao.update((Ticket) ticket) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return ticketDao.deleteById((UUID) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

}
