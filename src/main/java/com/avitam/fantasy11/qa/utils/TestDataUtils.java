package com.avitam.fantasy11.qa.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.EMPTY_STRING;
import static com.avitam.fantasy11.qa.utils.CheilStringUtils.NUMBER_FORMAT_EXCEPTION_LOG_FORMAT;

public class TestDataUtils {

    public static final Map<String, String> TEST_CLASSES_MAP = Map.of("PurchaseBCHDSameBillingAddCCVisaTestPlan", "avitam.fantasy11.qa.testPlans.PurchaseBCHDSameBillingAddCCVisaTestPlan");
    public static final String COMMA = ",";
    public static final String PRD = "prd";
    public static final String STG2 = "stg2";
    public static final String STG3 = "stg3";
    public static final String P6_PRE_QA2 = "p6-pre-qa2";
    public static final String P6_PRE_QA3 = "p6-pre-qa3";
    static Logger LOG = LoggerFactory.getLogger(TestDataUtils.class);

    /**
     * Retrieves a string value from a map based on the specified key. If the key corresponds to a collection,
     * an optional index can be provided to retrieve a specific element from the collection.
     * <p>
     * Parameters:
     * - testData: Map<String, ?> - The source map containing the data.
     * - key: Field - The key corresponding to the value to be retrieved. It is an enum value representing the field name.
     * - id: int... (optional) - An optional index to specify which element to retrieve from a collection. If not provided,
     * or if the index is out of bounds, the first element of the collection is returned.
     * This parameter is ignored if the value is a single string.
     * <p>
     * Returns:
     * - String: The retrieved value. If the key corresponds to a collection and a valid index is provided,
     * the method returns the element at that index. If the key corresponds to a single string or
     * the index is not provided/out of bounds, it returns the string directly or the first element
     * of the collection, respectively. Returns an empty string if the key is not found or the collection is empty.
     */
    public static String getString(Map<String, ?> testData, Field key, int... id) {
        Object value = testData.get(key.toString());
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            if (collection.isEmpty()) {
                return EMPTY_STRING;
            }
            if (id.length > 0 && id[0] >= 0) {
                // If the collection supports random access, use get method directly
                if (collection instanceof List) {
                    List<?> list = (List<?>) collection;
                    return id[0] < list.size() ? (String) list.get(id[0]) : EMPTY_STRING;
                } else {
                    // For other types of collections, iterate to the specified index
                    int currentIndex = 0;
                    for (Object obj : collection) {
                        if (currentIndex == id[0]) {
                            return (String) obj;
                        }
                        currentIndex++;
                    }
                }
            }
            // Default to the first element if id is not provided or out of bounds
            return (String) collection.iterator().next();
        }
        return EMPTY_STRING;
    }

    public static int getInt(Map<String, ?> testData, Field key, int... id) {
        String stringValue = getString(testData, key, id);
        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            LOG.error(String.format(NUMBER_FORMAT_EXCEPTION_LOG_FORMAT,
                    stringValue,
                    key,
                    e.getMessage()));

            return -1;
        }
    }

    public enum Field {
        FIRST_NAME("firstName"),
        LAST_NAME("lastName"),
        PHONE_NUMBER("phone"),
        EMAIL("email"),
        SHIPPING_STREET("shippingstreet"),
        SHIPPING_HOUSE_NUMBER("shippinghouseNr"),
        SHIPPING_POSTCODE("shippingpostcode"),
        SHIPPING_CITY("shippingcity"),
        SKUS("skus"),
        TEST_COUNT("testCount"),
        MAX_THREAD_COUNT("maxThreadCount"),
        HYBRIS_ENVIRONMENT("{hyb_env}"),
        AEM_ENVIRONMENT("{aem_env}");

        private final String field;

        Field(String field) {
            this.field = field;
        }

        public String toString() {
            return field;
        }
    }
}
