package org.ozan.utils;

import java.io.ByteArrayOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.ozan.consts.DefaultConstants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SocketUtils {

    /**
     * Receive byte data from client over a socket connection
     * 
     * @return byte[] data received from client
     */
    public byte[] processInput() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ServerSocket serverSocket = new ServerSocket(DefaultConstants.CLIENT_INPUT_PORT);
                Socket clientSocket = serverSocket.accept();) {
            System.out.println("Receiving data on port " + DefaultConstants.CLIENT_INPUT_PORT);
            System.out.println("Client connected from " + clientSocket.getRemoteSocketAddress());

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = clientSocket.getInputStream().read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error connecting to socket on port " + DefaultConstants.CLIENT_INPUT_PORT + " : "
                    + e.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Send byte data to client over a socket connection
     * 
     * @param data byte[] data to send to client
     */
    public void sendOutput(byte[] data) {
        try (ServerSocket serverSocket = new ServerSocket(DefaultConstants.CLIENT_OUTPUT_PORT);
                Socket clientSocket = serverSocket.accept();) {
            System.out.println("Sending " + data.length + " bytes of data to client over port "
                    + DefaultConstants.CLIENT_OUTPUT_PORT);
            clientSocket.getOutputStream().write(data);
            clientSocket.getOutputStream().flush();
            System.out.println("Bytes have been sent to client. Connection closed.");
        } catch (Exception e) {
            System.out.println("Error sending output to client: " + e.getMessage());
        }
    }
}
