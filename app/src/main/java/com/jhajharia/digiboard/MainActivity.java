package com.jhajharia.digiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reset(View v) {
        Board board = findViewById(R.id.board);
        board.reset();
    }

    public void undo(View v) {
        Board board = findViewById(R.id.board);
        board.undo();
    }

    public void changeColor(View v) {
        Board board = findViewById(R.id.board);
        board.setCurrCol(((ColorDrawable) v.getBackground()).getColor());
    }

    public void thinnerBrush(View v) {
        Board board = findViewById(R.id.board);
        board.setStrWidth(board.getStrWidth() - 5);
    }

    public void thickerBrush(View v) {
        Board board = findViewById(R.id.board);
        board.setStrWidth(board.getStrWidth() + 5);
    }

    public void lowThickness(View v) {
        Board board = findViewById(R.id.board);
        board.setStrWidth(5);
    }

    public void mediumThickness(View v) {
        Board board = findViewById(R.id.board);
        board.setStrWidth(10);
    }

    public void highThickness(View v) {
        Board board = findViewById(R.id.board);
        board.setStrWidth(20);
    }

    public void erase(View v) {
        Board board = findViewById(R.id.board);
        board.setCurrCol(0xffffffff);
    }
}