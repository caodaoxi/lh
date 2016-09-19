package com.lh.modules.schema.entity;

import com.lh.common.persistence.DataEntity;

/**
 * Created by caodaoxi on 16-8-1.
 */
public class SchemaField extends DataEntity<SchemaField> {
    private int schemaId;
    private String fieldName;
    private String fieldDescribe;
    private String fieldType;


    public int getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(int schemaId) {
        this.schemaId = schemaId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescribe() {
        return fieldDescribe;
    }

    public void setFieldDescribe(String fieldDescribe) {
        this.fieldDescribe = fieldDescribe;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
