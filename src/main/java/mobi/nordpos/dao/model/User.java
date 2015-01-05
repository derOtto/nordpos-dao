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
package mobi.nordpos.dao.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.openbravo.pos.util.Hashcypher;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "PEOPLE")
public class User {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String PASSWORD = "APPPASSWORD";
    public static final String ROLE = "ROLE";
    public static final String VISIBLE = "VISIBLE";
    public static final String IMAGE = "IMAGE";

    @DatabaseField(id = true, columnName = ID)
    private String id;

    @DatabaseField(columnName = NAME, unique = true)
    private String name;

    @DatabaseField(columnName = PASSWORD)
    private String password;

    private String card;

    @DatabaseField(foreign = true,
            columnName = ROLE,
            foreignColumnName = Role.ID,
            foreignAutoRefresh = true,
            canBeNull = false)
    private Role role;

    @DatabaseField(columnName = VISIBLE, defaultValue = "true")
    private Boolean visible;

    @DatabaseField(columnName = IMAGE, dataType = DataType.BYTE_ARRAY, canBeNull = true)
    private byte[] image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.password = Hashcypher.hashString(password);
    }

    public boolean isAuthentication(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return Hashcypher.authenticate(password, this.password);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        return name.equals(((User) other).name);
    }
}
