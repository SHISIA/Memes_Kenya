package com.memesKenya.meme.model;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**Download image from the internet and convert it to byte arrays*/
public class ImageToByteArrayConvertor {
    public static byte[] convert(String imageUrl) {
        // Replace the URL with the path to your image
        byte[] byteArray = new byte[0];

        try {
            // Create a URL object
            URL url = new URL(imageUrl);

            // Open a connection to the URL and get an input stream
            try (InputStream inputStream = url.openStream();
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

                // Read the image data into a byte array
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }

                // Get the byte array
                byteArray = byteArrayOutputStream.toByteArray();

                // Now, byteArray contains the image data as bytes
                // You can use this byte array as needed
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
        }
        return byteArray;
    }
}


