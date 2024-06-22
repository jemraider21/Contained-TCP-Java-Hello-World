package org.ozan.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.apache.nifi.util.FlowFilePackagerV3;
import org.apache.nifi.util.FlowFileUnpackagerV3;
import org.ozan.structs.FlowFileDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FlowFileUtils {
    public boolean checkIfBytesAreFlowFile(byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            FlowFileUnpackagerV3 unpackager = new FlowFileUnpackagerV3();
            unpackager.unpackageFlowFile(byteArrayInputStream, null);
            return true;
        } catch (Exception e) {
            System.out.println("Bytes are not for a NiFi FlowFile");
            return false;
        }
    }

    public FlowFileDTO createFlowFileDTO(byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            FlowFileUnpackagerV3 unpackager = new FlowFileUnpackagerV3();
            Map<String, String> attributes = unpackager.unpackageFlowFile(byteArrayInputStream, byteArrayOutputStream);

            return new FlowFileDTO(attributes, byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            System.out.println("Error creating FlowFile: " + e.getMessage());
            return null;
        }
    }

    public byte[] createFlowFileBytes(FlowFileDTO flowFile) {
        try{
            ByteArrayInputStream inputStream = new ByteArrayInputStream(flowFile.getContent());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            FlowFilePackagerV3 packager = new FlowFilePackagerV3();
            packager.packageFlowFile(inputStream, outputStream, flowFile.getAttributes(), flowFile.getContent().length);

            return outputStream.toByteArray();
        } catch (Exception e) {
            System.out.println("Error creating FlowFile bytes: " + e.getMessage());
            return null;
        }
    }
}
