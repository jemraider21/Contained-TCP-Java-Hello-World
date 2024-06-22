package org.ozan.utils;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SocketUtils {
    public byte[] processInput(Socket clientSocket) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            while ((bytesRead = clientSocket.getInputStream().read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            System.out.println("Error reading input from client: " + e.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void sendOutput(Socket clientSocket, byte[] data) {
        try {
            clientSocket.getOutputStream().write(data);
        } catch (Exception e) {
            System.out.println("Error sending output to client: " + e.getMessage());
        }
    }
}
