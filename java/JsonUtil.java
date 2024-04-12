package com.ultimate-rad-games;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;

import java.util.Iterator;

/**
 * JSON utility: inspect, traverse, verify.
 */
public class JsonUtil {

    final String HDR = "UtilityJSON";

    /**
     * Traverse JSON data structure.
     * @param inJson String JSON
     */
    private void traverse(final String inJson) {

        final String DEBUG = HDR + "traverse()::";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(inJson);
            parse(node);
        } catch (IOException e) {
            logger.error(DEBUG + e.getMessage());
        }
    }

    /**
     * Parse JsonNode object.
     * @param root JsonNode object input.
     */
    private void parse(final JsonNode root) {

        if (root.isObject()) {
            Iterator<String> iterator = root.fieldNames();            
            while (iterator.hasNext()) {
                String name = iterator.next();
                JsonNode child = root.get(name);
                inspect(root, name);
                obfuscate(root, child, name);                  
                parse(child);
            }
        } else if (root.isArray()) {
            ArrayNode arrayNode = (ArrayNode)root;
            for (int i = 0; i < arrayNode.size(); ++i) {
                JsonNode arrayElement = arrayNode.get(i);
                parse(arrayElement);
            }
        }
    }

    /**
     * Inspect JSON node.
     * @param node JsonNode object input.
     * @param name String JsonNode node field name.
     */    
    private void inspect(final JsonNode node, final String field) {

        final String DEBUG = HDR + "inspect()::";

        final String REGEX_FIELD = "field";
        final String REGEX_VALUE = "value";

        if ( isNode(node) && isString(field) ) {
            final String VALUE = node.get(field).asText();
            if ( field.contains(REGEX_FIELD) && VALUE.contains(REGEX_VALUE) ) {
                // set private | public variable *HERE*
                System.out.println(DEBUG + "field = " + field + "; value = " + VALUE);
            }
        }
    }

    /**
     * Obfuscate JsonNode value parameter.
     * @param parent JsonNode parent tree-node object.
     * @param child JsonNode child tree-node object.
     * @param name String containing sensitive data.
     */
    private void obfuscate(JsonNode parent, JsonNode child, final String name ) {        
        auditlog.traceEntry();        

        final String MASK = "********";

        if ( isNode(child) && isString(name) && this.field.contains(name) ) {
            ((ObjectNode)parent).put(name, MASK);
        }
    }    

    /**
     * Test JsonNode integrity and validity.
     * @param node JsonNode object input.
     * @return boolean result compare.
     */
    private boolean isNode(final JsonNode node) {
        return (node != null && !node.isNull());
    }    

    /**
     * Test string integrity and validity.
     * @param input String data input.
     * @return boolean describes string status.
     */
    private boolean isString(final String input) {
        return (input != null && !input.trim().isEmpty());
    }

    /**
     * Deserialize JSON string data structure to class object.
     * @param inJson String JSON data.
     * @param classObject class template deserialize type
     * @return Object output
     */
    private Object deserializeJSON(final String inJson, Class<T> classObject) {

        final String DEBUG = "deserializeJSON()::";

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
            return mapper.readValue(inJson, classObject.class);
        } catch (IOException e) { 
            System.out.println(DEBUG + e.getMessage());
            return null;
        }
    }

    /**
     * Serialize Object to JSON string data structure.
     * @param object Object generic fashioned from JSON data.
     * @return String JSON data.
     */  
    private String serializeJSON(final Object object) {

        final String DEBUG = "serializeJSON()::";

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            System.out.println(DEBUG + e.getMessage());
            return null;          
        }
    }      

}
