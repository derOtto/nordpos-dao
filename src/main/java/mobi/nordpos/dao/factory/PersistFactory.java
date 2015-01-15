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

import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public interface PersistFactory {

    public void init(ConnectionSource connectionSource) throws SQLException;

    public Object read(Object id) throws SQLException;

    public List<? extends Object> readList() throws SQLException;

    public Object find(String column, Object value) throws SQLException;

    public Object add(Object value) throws SQLException;

    public Boolean change(Object object) throws SQLException;

    public Boolean delete(Object id) throws SQLException;

}
