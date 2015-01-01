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

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import mobi.nordpos.dao.model.SharedTicket;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class SharedTicketPersist extends BaseDaoImpl<SharedTicket, String> {

    Dao<SharedTicket, String> sharedTicketDao;

    public SharedTicketPersist(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SharedTicket.class);
    }

    public SharedTicket read(String id) throws SQLException {
        try {
            sharedTicketDao = new SharedTicketPersist(connectionSource);
            return sharedTicketDao.queryForId(id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public SharedTicket add(SharedTicket ticket) throws SQLException {
        try {
            sharedTicketDao = new SharedTicketPersist(connectionSource);
            return sharedTicketDao.createIfNotExists(ticket);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public Boolean change(SharedTicket ticket) throws SQLException {
        try {
            sharedTicketDao = new SharedTicketPersist(connectionSource);
            return sharedTicketDao.update(ticket) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public Boolean delete(String id) throws SQLException {
        try {
            sharedTicketDao = new SharedTicketPersist(connectionSource);
            return sharedTicketDao.deleteById(id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}
