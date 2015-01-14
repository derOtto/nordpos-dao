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

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mobi.nordpos.dao.model.Receipt;
import mobi.nordpos.dao.model.SharedTicket;
import mobi.nordpos.dao.model.Tax;
import mobi.nordpos.dao.model.TaxLine;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class TaxLinePersist implements PersistFactory {

    ConnectionSource connectionSource;
    TaxLineDao taxLineDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        taxLineDao = new TaxLineDao(connectionSource);
    }

    @Override
    public TaxLine read(Object id) throws SQLException {
        try {
            return taxLineDao.queryForId((UUID) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<TaxLine> readList() throws SQLException {
        try {
            return taxLineDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public TaxLine find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = taxLineDao.queryBuilder();
            qb.where().like(column, value);
            return (TaxLine) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public TaxLine add(Object taxLine) throws SQLException {
        try {
            return taxLineDao.createIfNotExists((TaxLine) taxLine);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object taxLine) throws SQLException {
        try {
            return taxLineDao.update((TaxLine) taxLine) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return taxLineDao.deleteById((UUID) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public Integer addTaxLineList(SharedTicket order, Receipt receipt) throws SQLException {
        Map taxLines = new HashMap();
        for (TicketLineInfo lineInfo : order.getContent().getLines()) {
            TaxLine line = new TaxLine();
            String taxId = lineInfo.getTax().getId();
            BigDecimal base = BigDecimal.valueOf(lineInfo.getSubValue());
            BigDecimal amount = BigDecimal.valueOf(lineInfo.getValue() - lineInfo.getSubValue());
            if (taxLines.containsKey(taxId)) {
                line = (TaxLine) taxLines.get(taxId);
                line.setBase(line.getBase().add(base));
                line.setAmount(line.getAmount().add(amount));
            } else {
                line.setReceipt(receipt);
                line.setBase(base);
                line.setAmount(amount);
                Tax tax = new Tax();
                tax.setId(taxId);
                line.setTax(tax);
            }
            taxLines.put(taxId, line);
        }

        try {

            List<TaxLine> taxLineList = new ArrayList<>(taxLines.values());
            Integer counter = 0;
            for (TaxLine taxLine : taxLineList) {
                counter = counter + taxLineDao.create(taxLine);
            }
            return counter;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

}
