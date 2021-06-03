package ro.cs.pub.systems.eim.practicaltest02;

import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    private String address;
    private int port;
    private String pokename;
    private TextView pokemonTextView;
    private TextView pokemonImage;

    private Socket socket;

    public ClientThread(String address, int port, String pokename, TextView pokemonTextView, TextView pokemonImage) {
        this.address = address;
        this.port = port;
        this.pokename = pokename;
        this.pokemonTextView = pokemonTextView;
        this.pokemonImage = pokemonImage;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            System.out.println(pokename);
            printWriter.println(pokename);
            printWriter.flush();
            String pokemonInformation;
            while ((pokemonInformation = bufferedReader.readLine()) != null) {
                final String finalizedPokemonInformation = pokemonInformation;
                String ImageSrc = finalizedPokemonInformation;
                int indexStart = ImageSrc.indexOf("image=[") + 7;
                int indexEnd = ImageSrc.indexOf("], types");
                ImageSrc = ImageSrc.substring(indexStart, indexEnd);
                System.out.println(ImageSrc);
                final String finalizedSrc = ImageSrc;
                pokemonTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        pokemonTextView.setText(finalizedPokemonInformation);
                        pokemonImage.setText(finalizedSrc);
                    }
                });
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}
