package ro.cs.pub.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText pokeEditText = null;
    private Button getPokemonButton = null;
    private TextView pokemonImage = null;
    private TextView pokemonDetails = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private GetPokemonListener getPokemonListener = new GetPokemonListener();
    private class GetPokemonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = "127.0.0.1";
            String clientPort = "8000";
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            String pokename = pokeEditText.getText().toString();
            if (pokename == null || pokename.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (pokename ) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            pokemonDetails.setText(Constants.EMPTY_STRING);

            clientThread = new ClientThread(
                    clientAddress, Integer.parseInt(clientPort), pokename, pokemonDetails, pokemonImage
            );
            clientThread.start();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onCreate() callback method has been invoked");
        setContentView(R.layout.activity_main);

        String serverPort = "8000";
        if (serverPort == null || serverPort.isEmpty()) {
            Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
            return;
        }
        serverThread = new ServerThread(Integer.parseInt(serverPort));
        if (serverThread.getServerSocket() == null) {
            Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
            return;
        }
        serverThread.start();

        getPokemonButton = (Button)findViewById(R.id.get_pokemon_button);
        getPokemonButton.setOnClickListener(getPokemonListener);
        pokeEditText = (EditText) findViewById(R.id.pokemon_name);
        pokemonImage = (TextView) findViewById(R.id.pokemon_image);
        pokemonDetails = (TextView) findViewById(R.id.pokemon_details);
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}