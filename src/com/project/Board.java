package com.project;

import com.project.Pieces.Knight;
import com.project.Pieces.Pawn;
import com.project.Pieces.Piece;
import com.project.Pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

public final class Board {
    private List<List<Piece>> board;
    private static Board instance;
    private List<Piece> botPieces;

    private Board() {
        board = new ArrayList<>();
        botPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 8; ++j) {
                board.get(i).add(null);
            }
        }

        for (int i = 0; i < 8; i++) {
            // add all piece on board - pawns for now
            // knights are dummies used so we ignore them
            // and not consider them pawns
            board.get(0).set(i, new Knight(0, i, true));
            board.get(1).set(i, new Pawn(1, i, true));

            board.get(6).set(i, new Pawn(6, i, false));
            board.get(7).set(i, new Knight(7, i, false));

        }
        botPieces.addAll(board.get(6));
       // botPieces.addAll(board.get(7));
    }

    /**
     * Checks if a cell is empty.
     * @param x,y - coordinates.
     * @return    - true if it's empty
     *              false otherwise.
     */
    public boolean isCellEmpty(final int x, final int y) {
        return board.get(x).get(y) == null;
    }

    /**
     * Checks if a piece has the opposite color. Avoids eating their own color
     * @param x,y   - coordinates.
     * @param white - true if the moving piece is white;
     *                false otherwise.
     * @return      - true if the opponent has the opposite color;
     *                false otherwise.
     */
    public boolean isOppositeColor(final int x, final int y, final boolean white) {
        return white ? !(board.get(x).get(y).isWhite()) : board.get(x).get(y).isWhite();
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public static Board getNewInstance() {
        instance = new Board();
        return instance;
    }

    // banal function to iterate through pawns and move them
    public void generateMove() {
        for (Piece piece : botPieces) {
            if (piece.movePiece()) {
                return;
            }
        }
        // if for ended we couldn't move anything so we resign
        System.out.println("resign");
    }

    // move a piece on our internal board
    public void executeMove(final String move) {
        int currentX = Integer.parseInt(String.valueOf(move.charAt(1))) - 1;
        int currentY = move.charAt(0) - 'a';
        int nextX = Integer.parseInt(String.valueOf(move.charAt(3))) - 1;
        int nextY = move.charAt(2) -'a';

        // if we find another piece, eat it
        if (!isCellEmpty(nextX, nextY)) {
            removePiece(nextX, nextY);
        }

        Piece piece = board.get(currentX).get(currentY);
        board.get(nextX).set(nextY, piece);
        board.get(currentX).set(currentY, null);
        piece.setXPos(nextX);
        piece.setYPos(nextY);
    }

    public void removePiece(final int x, final int y) {
        botPieces.remove(board.get(x).get(y));
        board.get(x).set(y, null);
    }

    public void changeColor(final boolean white) {
        // if we already play with this color ignore command
        if (botPieces.get(0).isWhite() == white) {
            return;
        }
        botPieces.clear();
        for (int i = 0; i < 8; i++) {
            for (Piece piece : board.get(i)) {
                if (piece != null) {
                    if (piece.isWhite() == white && piece.type == PieceType.Pawn) {
                        botPieces.add(piece);
                    }
                }
            }
        }
    }
}
