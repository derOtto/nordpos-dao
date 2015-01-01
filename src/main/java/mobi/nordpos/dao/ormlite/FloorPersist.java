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
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mobi.nordpos.dao.model.Floor;
import mobi.nordpos.dao.model.Place;
import mobi.nordpos.dao.model.Product;
import mobi.nordpos.dao.model.ProductCategory;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class FloorPersist extends BaseDaoImpl<Floor, String> {

    Dao<Floor, String> floorDao;

    public FloorPersist(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Floor.class);
    }

    public List<Floor> readList() throws SQLException {
        try {
            floorDao = new FloorPersist(connectionSource);
            QueryBuilder qb = floorDao.queryBuilder().orderBy(Floor.NAME, true);
            qb.where().isNotNull(Floor.ID);
            return qb.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
    
    public List<Place> readPlaceList(Floor floor) throws SQLException {
        CloseableWrappedIterable<Place> iterator = floor.getPlaceCollection().getWrappedIterable();
        List<Place> list = new ArrayList<>();
        try {
            for (Place place : iterator) {
                list.add(place);
            }
            return list;
        } finally {
            iterator.close();
        }
    }      
    

}
