package org.ozan.structs;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FlowFileDTO {
    Map<String, String> attributes;
    byte[] content;

    public String getAttribute(String key) {
        return attributes.get(key);
    }
}
