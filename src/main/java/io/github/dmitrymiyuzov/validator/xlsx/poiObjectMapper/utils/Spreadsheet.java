package io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.ss.model.annotations.SheetColumn;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Spreadsheet related utilites.
 */
public final class Spreadsheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Spreadsheet.class);

    private Spreadsheet() {
        // Utility Class
    }


    // Utilities
    // ------------------------------------------------------------------------

    /**
     * Splits the CellReference and returns only the column reference.
     *
     * @param cellRef the cell reference value (ex. D3)
     * @return returns the column index "D" from the cell reference "D3"
     */
    public static String getCellColumnReference(String cellRef) {
        return cellRef.split("[0-9]*$")[0];
    }



    // Bean :: Property Utils

    public static Map<String, String> getPropertyToColumnNameMap(Class<?> beanType) {
        // Sanity checks
        if (beanType == null) {
            throw new IllegalArgumentException("getColumnToPropertyMap :: Invalid ExcelBean type - " + beanType);
        }

        // Property to Column name Mapping
        final Map<String, String> mapping = new HashMap<>();

        // Fields
        Field[] fields = beanType.getDeclaredFields();
        for (Field f : fields) {
            String fieldName = f.getName();
            mapping.put(fieldName, fieldName);

            SheetColumn ec = f.getAnnotation(SheetColumn.class);
            if (ec != null && StringUtils.isNotEmpty(ec.value())) {
                mapping.put(fieldName, ec.value());
            }
        }

        // Methods
        Method[] methods = beanType.getDeclaredMethods();
        for (Method m : methods) {
            String fieldName = Beans.getFieldName(m);
            if (!mapping.containsKey(fieldName)) {
                mapping.put(fieldName, fieldName);
            }

            SheetColumn ec = m.getAnnotation(SheetColumn.class);
            if (ec != null && StringUtils.isNotEmpty(ec.value())) {
                mapping.put(fieldName, ec.value());
            }
        }

        LOGGER.info("Bean property to Excel Column of - {} : {}", beanType, mapping);
        return Collections.unmodifiableMap(mapping);
    }

    public static Map<String, String> getColumnToPropertyMap(Class<?> beanType) {
        // Column to Property Mapping
        final Map<String, String> columnToPropMap = new HashMap<>();

        // Bean Property to Column Mapping
        final Map<String, String> propToColumnMap = getPropertyToColumnNameMap(beanType);
        for (String prop : propToColumnMap.keySet()) {
            columnToPropMap.put(propToColumnMap.get(prop), prop);
        }

        LOGGER.info("Excel Column to property map of - {} : {}", beanType, columnToPropMap);
        return Collections.unmodifiableMap(columnToPropMap);
    }

    public static List<String> getColumnNames(Class<?> beanType) {
        // Bean Property to Column Mapping
        final Map<String, String> propToColumnMap = getPropertyToColumnNameMap(beanType);

        return new ArrayList<>(propToColumnMap.values());
    }




    // Read from Bean : as Row Data
    // ------------------------------------------------------------------------

    public static Map<String, String> asRowDataMap(Object beanObj, List<String> colHeaders) throws Exception {
        // Excel Bean Type
        final Class<?> beanType = beanObj.getClass();

        // RowData map
        final Map<String, String> rowDataMap = new HashMap<>();

        // Fields
        for (Field f : beanType.getDeclaredFields()) {
            if (!f.isAnnotationPresent(SheetColumn.class)) {
                continue;
            }

            String fieldName = f.getName();

            SheetColumn ec = f.getAnnotation(SheetColumn.class);
            String header = StringUtils.isEmpty(ec.value()) ? fieldName : ec.value();
            if (!colHeaders.contains(header)) {
                continue;
            }

            rowDataMap.put(header, Beans.getFieldValueAsString(beanObj, fieldName));
        }

        // Methods
        for (Method m : beanType.getDeclaredMethods()) {
            if (!m.isAnnotationPresent(SheetColumn.class)) {
                continue;
            }

            String fieldName = Beans.getFieldName(m);

            SheetColumn ec = m.getAnnotation(SheetColumn.class);
            String header = StringUtils.isEmpty(ec.value()) ? fieldName : ec.value();
            if (!colHeaders.contains(header)) {
                continue;
            }

            rowDataMap.put(header, Beans.getFieldValueAsString(beanObj, fieldName));
        }

        return rowDataMap;
    }



    // Write to Bean :: from Row data
    // ------------------------------------------------------------------------

    public static <T> T rowAsBean(Class<T> beanClz, Map<String, String> cellProperties, Map<String, Object> cellValues) {
        // Sanity checks
        if (cellValues == null || cellProperties == null) {
            return null;
        }

        try {
            // Create new Instance
            T rowBean = beanClz.getDeclaredConstructor().newInstance();

            // Fill in the data
            for (String cellName : cellProperties.keySet()) {
                String propName = cellProperties.get(cellName);
                if (StringUtils.isEmpty(propName)) {
                    LOGGER.debug("{} : No mathching property found for column[name] - {} ", beanClz, cellName);
                    continue;
                }

                Object propValue = cellValues.get(cellName);
                try {
                    // Set the property value in the current row object bean
                    BeanUtils.setProperty(rowBean, propName, propValue);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    String errMsg = String.format("Failed to set bean property - %s, value - %s", propName, propValue);
                    LOGGER.error(errMsg, ex);
                }
            }

            return rowBean;
        } catch (Exception ex) {
            String errMsg = String.format("Error while creating bean - %s, from - %s", beanClz, cellValues);
            LOGGER.error(errMsg, ex);
        }

        return null;
    }



}