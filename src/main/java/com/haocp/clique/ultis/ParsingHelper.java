package com.haocp.clique.ultis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ParsingHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Long> parseJsonToLongList(String json) {
        if (!StringUtils.hasText(json)) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(
                    json,
                    new TypeReference<List<Long>>() {}
            );

        } catch (Exception e) {
            System.err.println(
                    "Parse swipeOrder failed: " + e.getMessage()
            );
            return new ArrayList<>();
        }
    }

    public static String toJson(List<Long> ids){
        try {
            return mapper.writeValueAsString(
                    ids
            );

        } catch (Exception e) {
            System.err.println(
                    "Parse failed: " + e.getMessage()
            );
            return "";
        }
    }

    public static String toJson(String ids){
        try {
            return mapper.writeValueAsString(
                    ids
            );

        } catch (Exception e) {
            System.err.println(
                    "Parse failed: " + e.getMessage()
            );
            return "";
        }
    }
}
