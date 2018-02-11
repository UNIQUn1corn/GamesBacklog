package com.example.marmm.gamesbacklog.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marmm.gamesbacklog.R;
import com.example.marmm.gamesbacklog.data.DataSource;
import com.example.marmm.gamesbacklog.data.Game;

import java.util.Objects;

public class GameModifyActivity extends AppCompatActivity {


    private Game game;
    private ArrayAdapter statusAdapter;
    private TextInputEditText titleInput;
    private TextInputEditText platformInput;
    private TextInputEditText notesInput;
    private Spinner statusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find all our views from within our layout
        titleInput = findViewById(R.id.inputGameTitle);
        notesInput = findViewById(R.id.inputGameNotes);
        platformInput = findViewById(R.id.inputGamePlatform);
        statusSpinner = findViewById(R.id.spinnerGameStatus);
        // Create an ArrayAdapter using the string array and a default spinner layout
        statusAdapter = ArrayAdapter.createFromResource(this, R.array.game_status,
                R.layout.support_simple_spinner_dropdown_item);
        // Set the adapter to the spinner
        statusSpinner.setAdapter(statusAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // Based on the action we will add or update a game
        if (Objects.equals(getIntent().getAction(), Intent.ACTION_INSERT)) {
            // We are adding a new game
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveGame();
                }
            });
        } else {
            // We are updating an existing game so start by retrieving it from the intent
            game = (Game) getIntent().getSerializableExtra("game");
            // Set the values for the views
            titleInput.setText(game.getTitle());
            notesInput.setText(game.getNotes());
            platformInput.setText(game.getPlatform());
            // Get the position of the game's status within the adapter
            int spinnerPosition = statusAdapter.getPosition(game.getStatus());
            statusSpinner.setSelection(spinnerPosition);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateGame();
                }
            });
        }

    }


    public void saveGame() {
        String title = titleInput.getText().toString();
        String notes = notesInput.getText().toString();
        String platform = platformInput.getText().toString();
        String gameStatus = statusSpinner.getSelectedItem().toString();

        if (title.isEmpty()) {
            titleInput.setError(getString(R.string.error_game_add_title_required));
        } else if (platform.isEmpty()) {
            platformInput.setError(getString(R.string.error_game_add_platform_required));
        } else {
            Game game = new Game(title, platform, gameStatus, notes);

            DataSource dataSource = new DataSource(this);
            dataSource.open();
            dataSource.save(game);

            Toast.makeText(this, R.string.message_game_saved, Toast.LENGTH_LONG).show();

            finish();
        }
    }

    private void updateGame() {
        // Get the input from the Views
        String title = titleInput.getText().toString();
        String platform = platformInput.getText().toString();
        String status = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        // Validate that the title and platform is not empty
        if (title.isEmpty()) {
            titleInput.setError(getString(R.string.error_game_add_title_required));
        } else if (platform.isEmpty()) {
            platformInput.setError(getString(R.string.error_game_add_platform_required));
        } else {
            Game updatedGame = new Game(title, platform, status, notes);

            DataSource dataSource = new DataSource(this);
            dataSource.open();
            dataSource.update(game.getId(), updatedGame);

            Toast.makeText(this, R.string.message_game_modified, Toast.LENGTH_LONG).show();

            finish();
        }
    }



}
