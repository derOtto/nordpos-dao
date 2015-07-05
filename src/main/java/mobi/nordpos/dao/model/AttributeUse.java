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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
@DatabaseTable(tableName = "ATTRIBUTEUSE")
public class AttributeUse {

    public static final String ID = "ID";
    public static final String ATTRIBUTESET_ID = "ATTRIBUTESET_ID";
    public static final String ATTRIBUTE_ID = "ATTRIBUTE_ID";
    public static final String LINENO = "LINENO";

    @DatabaseField(generatedId = true, columnName = ID)
    private UUID id;

    @DatabaseField(foreign = true,
            columnName = ATTRIBUTESET_ID,
            foreignColumnName = AttributeSet.ID,
            foreignAutoRefresh = true,
            canBeNull = false)
    private AttributeSet attributeSet;

    @DatabaseField(foreign = true,
            columnName = ATTRIBUTE_ID,
            foreignColumnName = Attribute.ID,
            foreignAutoRefresh = true,
            canBeNull = false)
    private Attribute attribute;

    @DatabaseField(columnName = LINENO)
    private Integer lineNumber;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AttributeSet getAttributeSet() {
        return attributeSet;
    }

    public void setAttributeSet(AttributeSet attributeSet) {
        this.attributeSet = attributeSet;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        return id.equals(((AttributeUse) other).id);
    }

}
