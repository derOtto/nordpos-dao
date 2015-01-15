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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import mobi.nordpos.dao.model.Location;
import mobi.nordpos.dao.model.Receipt;
import mobi.nordpos.dao.model.StockDiary;
import mobi.nordpos.dao.model.TicketLine;
import mobi.nordpos.dao.ormlite.StockDiaryDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class StockDiaryPersist implements PersistFactory {

    ConnectionSource connectionSource;
    StockDiaryDao stockDiaryDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        stockDiaryDao = new StockDiaryDao(connectionSource);
    }

    @Override
    public StockDiary read(Object id) throws SQLException {
        try {
            return stockDiaryDao.queryForId((UUID) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<StockDiary> readList() throws SQLException {
        try {
            return stockDiaryDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public StockDiary find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = stockDiaryDao.queryBuilder();
            qb.where().like(column, value);
            return (StockDiary) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public StockDiary add(Object stockDiary) throws SQLException {
        try {
            return stockDiaryDao.createIfNotExists((StockDiary) stockDiary);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object stockDiary) throws SQLException {
        try {
            return stockDiaryDao.update((StockDiary) stockDiary) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return stockDiaryDao.deleteById((UUID) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public Integer addStockDiaryList(Integer reason, Location location, Receipt receipt, List<TicketLine> lineList) throws SQLException {
        try {
            Integer counter = 0;
            for (TicketLine line : lineList) {
                StockDiary diary = new StockDiary();
                diary.setReason(reason);
                diary.setLocation(location);
                diary.setDate(receipt.getDate());
                diary.setProduct(line.getProduct());
                diary.setPrice(line.getPrice());

                if (reason >= 0) {
                    diary.setUnit(line.getUnit());
                } else {
                    diary.setUnit(line.getUnit().negate());
                }
                
                counter = counter + stockDiaryDao.create(diary);
            }
            return counter;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

}
