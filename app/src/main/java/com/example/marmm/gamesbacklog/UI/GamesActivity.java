package com.example.marmm.gamesbacklog.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.marmm.gamesbacklog.R;
import com.example.marmm.gamesbacklog.data.DataSource;
import com.example.marmm.gamesbacklog.data.Game;

public class GamesActivity extends AppCompatActivity implements GamesAdapter.OnGameClickListener {


    private GamesAdapter mAdapter;
    private Cursor mCursor;
    private RecyclerView mRecyclerView;
    private DataSource mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup the RecyclerView
        mRecyclerView = findViewById(R.id.gameList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(100L);
        itemAnimator.setRemoveDuration(100L);
        mRecyclerView.setItemAnimator(itemAnimator);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        mDataSource = new DataSource(this);
        mDataSource.open();

        updateUI();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGame();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        mCursor =  mDataSource.findAll();
        if (mAdapter == null) {
            mAdapter = new GamesAdapter (mCursor, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapCursor(mCursor);
        }
    }


    protected void onResume() {
        super.onResume();
        mDataSource.open();
        updateUI();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mCursor != null && !mCursor.isClosed()) mCursor.close();
        mDataSource.close();
    }

    private void updateGame(Game game) {
        Intent intent = new Intent(this, GameModifyActivity.class);
        intent.putExtra("game", game);
        intent.setAction(Intent.ACTION_EDIT);
        startActivity(intent);
    }

    private void addGame() {
        Intent intent = new Intent(this, GameModifyActivity.class);
        intent.setAction(Intent.ACTION_INSERT);
        startActivity(intent);
    }


    @Override
    public void onGameClick(Game game) {
        updateGame (game);
    }


    private ItemTouchHelper.SimpleCallback itemTouchCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // unused
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Get the id of the game object on which we performed the swipe operation
                Game game = ((GamesAdapter.GameViewHolder) viewHolder).getGame();
                // Delete the game object from our database
                mDataSource.delete(game.getId());
                // Get a new cursor from our database
                Cursor cursor = mDataSource.findAll();
                mAdapter.swapCursor(cursor);
                mAdapter.notifyDataSetChanged();
                // Show a Toast message to inform the user that the game was deleted, note that we
                // are calling makeText from within an anonymous class so we have to explicitly tell
                // it to use GamesActivity.this instead of just this as that points to the anonymous
                // class
                Toast.makeText(GamesActivity.this, R.string.message_game_deleted, Toast.LENGTH_SHORT).show();
            }
        };
    }


}

