package org.ozan;

import org.ozan.structs.FlowFileDTO;
import org.ozan.utils.FlowFileUtils;
import org.ozan.utils.SocketUtils;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nServer has started...");
        try {
            while (true) {
                byte[] dataFromClient = SocketUtils.processInput();

                byte[] dataToSend = "Hello from server".getBytes();

                if (FlowFileUtils.checkIfBytesAreFlowFile(dataFromClient)) {
                    System.out.println("\nBytes are for a NiFi FlowFile");
                    FlowFileDTO flowFile = FlowFileUtils.createFlowFileDTO(dataFromClient);
                    System.out.println("FlowFile Attributes: " + flowFile.getAttributes());
                    System.out.println("FlowFile Content: " + new String(flowFile.getContent()) + "\n");
                    dataToSend = FlowFileUtils.createFlowFileBytes(flowFile);
                }

                SocketUtils.sendOutput(dataToSend);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server is shutting down...");
    }
}