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
import com.openbravo.pos.ticket.TicketLineInfo;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import mobi.nordpos.dao.model.Product;
import mobi.nordpos.dao.model.SharedTicket;
import mobi.nordpos.dao.model.Tax;
import mobi.nordpos.dao.model.Ticket;
import mobi.nordpos.dao.model.TicketLine;
import mobi.nordpos.dao.ormlite.TicketLineDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class TicketLinePersist implements PersistFactory {

    ConnectionSource connectionSource;
    TicketLineDao ticketLineDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        ticketLineDao = new TicketLineDao(connectionSource);
    }

    @Override
    public TicketLine read(Object id) throws SQLException {
        try {
            return ticketLineDao.queryForId((String) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<TicketLine> readList() throws SQLException {
        try {
            return ticketLineDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public TicketLine find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = ticketLineDao.queryBuilder();
            qb.where().like(column, value);
            return (TicketLine) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public TicketLine add(Object application) throws SQLException {
        try {
            return ticketLineDao.createIfNotExists((TicketLine) application);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object application) throws SQLException {
        try {
            return ticketLineDao.update((TicketLine) application) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return ticketLineDao.deleteById((String) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public Integer addTicketLineList(SharedTicket order, Ticket ticket) throws SQLException {
        try {
            Integer counter = 0;
            for (TicketLineInfo lineInfo : order.getContent().getLines()) {
                TicketLine line = new TicketLine();
                line.setTicket(ticket);
                line.setNumber(lineInfo.getM_iLine());
                line.setPrice(BigDecimal.valueOf(lineInfo.getPrice()));
                line.setUnit(BigDecimal.valueOf(lineInfo.getMultiply()));

                Product product = new Product();
                product.setId(lineInfo.getProductid());
                line.setProduct(product);

                Tax tax = new Tax();
                tax.setId(lineInfo.getTax().getId());
                line.setTax(tax);

                counter = counter + ticketLineDao.create(line);
            }
            return counter;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}
