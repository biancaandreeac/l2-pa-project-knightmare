package com.project;

import com.project.Pieces.Pawn;
import com.project.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public final class Board {
    private List<List<Piece>> board;
    private static Board instance;

    private Board() {
        board = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 8; ++j) {
                board.get(i).add(null);
            }
        }

        for(int i = 0; i < 8; i++) {
            // add pawns
            board.get(1).set(i, new Pawn(1, i));
            board.get(6).set(i, new Pawn(1, i));

            board.get(0).set(i, new Pawn(1, i));
            board.get(7).set(i, new Pawn(1, i));
        }
    }

    public boolean isCellEmpty(final int x, final int y) {
        return board.get(x).get(y) == null;
    }

    public static Board getInstance() {
        if(instance == null) {
            instance = new Board();
        }
        return instance;
    }
}