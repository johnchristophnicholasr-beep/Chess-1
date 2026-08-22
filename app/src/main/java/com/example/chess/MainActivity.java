package com.example.chess;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ChessBoard(this));
    }

    static class ChessBoard extends View {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        String[] pieces = {
                "♜","♞","♝","♛","♚","♝","♞","♜",
                "♟","♟","♟","♟","♟","♟","♟","♟",
                "","","","","","","","",
                "","","","","","","","",
                "","","","","","","","",
                "","","","","","","","",
                "♙","♙","♙","♙","♙","♙","♙","♙",
                "♖","♘","♗","♕","♔","♗","♘","♖"
        };

        int selected = -1;

        ChessBoard(Activity activity) {
            super(activity);
            paint.setTypeface(Typeface.DEFAULT);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            float size = getWidth() / 8f;

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {

                    // Chess square
                    if ((row + col) % 2 == 0) {
                        paint.setColor(Color.rgb(240, 217, 181));
                    } else {
                        paint.setColor(Color.rgb(181, 136, 99));
                    }

                    canvas.drawRect(
                            col * size,
                            row * size,
                            (col + 1) * size,
                            (row + 1) * size,
                            paint
                    );

                    // Selected square
                    if (selected == row * 8 + col) {
                        paint.setColor(Color.YELLOW);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(6);
                        canvas.drawRect(
                                col * size + 3,
                                row * size + 3,
                                (col + 1) * size - 3,
                                (row + 1) * size - 3,
                                paint
                        );
                        paint.setStyle(Paint.Style.FILL);
                    }

                    // Chess piece
                    String piece = pieces[row * 8 + col];

                    if (!piece.isEmpty()) {
                        paint.setColor(Color.BLACK);
                        paint.setTextSize(size * 0.72f);
                        paint.setTextAlign(Paint.Align.CENTER);

                        canvas.drawText(
                                piece,
                                (col + 0.5f) * size,
                                (row + 0.72f) * size,
                                paint
                        );
                    }
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_UP) {

                float size = getWidth() / 8f;

                int col = (int) (event.getX() / size);
                int row = (int) (event.getY() / size);

                if (col < 0 || col > 7 || row < 0 || row > 7) {
                    return true;
                }

                int square = row * 8 + col;

                // Select a piece
                if (selected == -1) {

                    if (!pieces[square].isEmpty()) {
                        selected = square;
                        invalidate();
                    }

                } else {

                    // Move the selected piece
                    pieces[square] = pieces[selected];
                    pieces[selected] = "";

                    selected = -1;
                    invalidate();
                }

                return true;
            }

            return true;
        }
    }
}
