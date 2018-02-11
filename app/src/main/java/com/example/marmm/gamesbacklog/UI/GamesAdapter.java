package com.example.marmm.gamesbacklog.UI;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marmm.gamesbacklog.R;
import com.example.marmm.gamesbacklog.data.Game;
import com.example.marmm.gamesbacklog.data.GamesContract;

/**
 * Created by marmm on 10/02/2018.
 */


public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {

    private Cursor cursor;
    private OnGameClickListener onGameClickListener;

    public interface OnGameClickListener {
      void onGameClick(Game game);
   }

    public GamesAdapter(Cursor cursor, OnGameClickListener onGameClickListener) {
        this.cursor = cursor;
        this.onGameClickListener = onGameClickListener;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate our item_game layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        // Instantiate a GameViewHolder and pass our layout as it's view
        return new GameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        // Move the cursor to the right position
        cursor.moveToPosition(position);
        // Create a game object from the cursor's data
        Game game = new Game();
        game.setId(cursor.getInt(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_ID)));
        game.setTitle(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_TITLE)));
        game.setPlatform(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_PLATFORM)));
        game.setDateAdded(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_DATE)));
        game.setStatus(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_STATUS)));
        game.setNotes(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_NOTES)));
        // Bind the game object to the view
        holder.bind(game);
    }

    @Override
    public int getItemCount() {
        return (cursor == null ? 0 : cursor.getCount());
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }



    /**
     * A wrapper class representing a single view or row within our RecyclerView. The ViewHolder
     * holds a reference to all the views and the game object.
     */
    class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Game game;
        private final TextView title;
        private final TextView platform;
        private final TextView status;
        private final TextView date;

        GameViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.gameTitle);
            date = view.findViewById(R.id.gameDate);
            status = view.findViewById(R.id.gameStatus);
            platform = view.findViewById(R.id.gamePlatform);
            view.setOnClickListener(this);
        }

        public Game getGame() {
            return game;
        }

        void bind(final Game game) {
            this.game = game;
            title.setText(game.getTitle());
            date.setText(game.getDateAdded());
            status.setText(game.getStatus());
            platform.setText(game.getPlatform());

        }

        @Override
        public void onClick(View view) {
            // Move the cursor to the right position
            cursor.moveToPosition( getAdapterPosition());
            // Create a game object from the cursor's data
            Game game = new Game();
            game.setId(cursor.getInt(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_ID)));
            game.setTitle(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_TITLE)));
            game.setPlatform(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_PLATFORM)));
            game.setDateAdded(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_DATE)));
            game.setStatus(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_STATUS)));
            game.setNotes(cursor.getString(cursor.getColumnIndex(GamesContract.GameEntry.COLUMN_NAME_NOTES)));

            onGameClickListener.onGameClick(game);
        }
    }
}


