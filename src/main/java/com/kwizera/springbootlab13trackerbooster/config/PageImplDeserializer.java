package com.kwizera.springbootlab13trackerbooster.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonComponent
public class PageImplDeserializer extends JsonDeserializer<PageImpl> {

    @Override
    public PageImpl deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode contentNode = node.get("content");
        JsonNode pageableNode = node.get("pageable");
        JsonNode totalElementsNode = node.get("totalElements");

        List<Object> content = new ArrayList<>();

        if (contentNode != null && contentNode.isArray()) {
            // Use the ObjectMapper from the context
            ObjectMapper mapper = (ObjectMapper) p.getCodec();
            content = mapper.convertValue(contentNode, new TypeReference<List<Object>>() {
            });
        }

        // Handle pageable - it might be null for unpaged results
        Pageable pageable = Pageable.unpaged();
        if (pageableNode != null && !pageableNode.isNull()) {
            int page = pageableNode.path("pageNumber").asInt(0);
            int size = pageableNode.path("pageSize").asInt(20);
            pageable = PageRequest.of(page, size);
        }

        long totalElements = totalElementsNode != null ? totalElementsNode.asLong(0) : content.size();

        return new PageImpl<>(content, pageable, totalElements);
    }
}
