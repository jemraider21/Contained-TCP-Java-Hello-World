package org.ozan;

import java.net.ServerSocket;
import java.net.Socket;
import org.ozan.consts.DefaultConstants;
import org.ozan.structs.FlowFileDTO;
import org.ozan.utils.FlowFileUtils;
import org.ozan.utils.SocketUtils;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(DefaultConstants.CLIENT_PORT)) {
            System.out.println("Server is running on port " + DefaultConstants.CLIENT_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getRemoteSocketAddress());
                byte[] dataFromClient = SocketUtils.processInput(clientSocket);

                byte[] dataToSend = "Hello from server".getBytes();
                if (FlowFileUtils.checkIfBytesAreFlowFile(dataFromClient)) {
                    System.out.println("Bytes are for a NiFi FlowFile");
                    FlowFileDTO flowFile = FlowFileUtils.createFlowFileDTO(dataFromClient);
                    System.out.println("Attributes: " + flowFile.getAttributes());
                    System.out.println("Content: " + new String(flowFile.getContent()));
                    dataToSend = FlowFileUtils.createFlowFileBytes(flowFile);
                } else {
                    System.out.println("Bytes are not for a NiFi FlowFile");
                }

                SocketUtils.sendOutput(clientSocket, dataToSend);
                clientSocket.close();
                System.out.println("Client disconnected");
            }
        } catch (Exception e) {
            System.out.println(
                    "Error connecting to socket on port" + DefaultConstants.CLIENT_PORT + " : " + e.getMessage());
        }
    }
}