/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;


/**
 *
 * @author evenal
 */
public class GameState {
    public static final int N = 3; // board size;
    public static final int NxN = N * N;
    private static final Player[] playerValues = Player.values();

    Player[] board;
    int key;
    int canonicalKey;


    public GameState() {
        board = new Player[NxN];
        for (int i = 0; i < board.length; i++)
            board[i] = Player.NONE;
    }


    public GameState(int inKey) {
        this.key = inKey;
        Player[] board = new Player[NxN];
        for (int i = 0; i < NxN; i++) {
            int digit = inKey % N;
            inKey = inKey / N;
            Player p = playerValues[digit];
            board[i] = p;
        }
    }


    public int getKey() {
        return key;
    }


    private static int computeKey(Player[] brd) {
        int sum = 0;
        for (int i = NxN - 1; i >= 0; i--)
            sum = N * sum + brd[i].ordinal();
        return sum;
    }


    public boolean makeMove(Player p, int row, int col)
            throws IllegalMoveException {
        int index = getIndex(col, row);
        if (index < 0 || index >= board.length || board[index] != Player.NONE) {
            throw new IllegalMoveException();
        }

        board[index] = p;
        updateKeys();
        return false;
    }


    private void updateKeys() {
        key = computeKey(board);
        canonicalKey = key;

        Player[] backup = board;
        Player[] mirrored = mirror(board, new Player[NxN]);
        check(mirrored);

        Player[] rotated = rotate(board, new Player[NxN]);
        check(rotated);
        check(mirror(rotated, mirrored));

        rotated = rotate(board, new Player[NxN]);
        check(rotated);
        check(mirror(rotated, mirrored));

        rotated = rotate(board, new Player[NxN]);
        check(rotated);
        check(mirror(rotated, mirrored));
    }


    private void check(Player[] rotMirr) {
        int k = computeKey(rotMirr);
//        print(rotMirr, "K=" + k);
        if (k < canonicalKey)
            canonicalKey = k;
    }


    private static Player[] rotate(Player[] src, Player[] tgt) {
        for (int i = 0; i < src.length; i++) {
            int c = i % N;
            int r = (i - c) / N;
            int iRot = N * c + N - r - 1;
            tgt[iRot] = src[i];
        }
        return tgt;
    }


    private static Player[] mirror(Player[] src, Player[] tgt) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                tgt[r * N + c] = src[r * N + N - c - 1];
            }
        }
        return tgt;
    }


    private static void copy(Player[] org, Player[] copy) {
        for (int i = 0; i < org.length; i++)
            copy[i] = org[i];
    }


    public Player get(int row, int col) {
        return board[N * row + col];
    }


    public int getIndex(int col, int row) {
        return N * row + col;
    }


    public int getCol(int index) {
        return index % N;
    }


    public int getRow(int index) {
        return (index - getCol(index)) / N;
    }


    public static void print(Player[] bb, String heading) {
        for (int r = 0; r < N; r++) {
            System.out.print("|");
            for (int c = 0; c < N; c++) {
                Player p = bb[N * r + c];
                String symbol = " ";
                if (p != null)
                    symbol = p.symbol;
                System.out.format("%s", symbol);
            }
            System.out.print("|\n");
        }
        System.out.format(heading + " HashCode = %d\n\n", computeKey(bb));

    }


    public void printBoard(String name) {
        for (int r = 0; r < N; r++) {
            System.out.print("|");
            for (int c = 0; c < N; c++) {
                Player p = board[N * r + c];
                String symbol = " ";
                if (p != null)
                    symbol = p.symbol;
                System.out.format("%s", symbol);
            }
            System.out.print("|\n");
        }
        System.out.format(name + " HashCode = %d\n\n", getKey());

    }


    public static void main(String[] args) throws IllegalMoveException {
        System.out.println("Setting up board");
        GameState g3 = new GameState();
        g3.makeMove(Player.BLACK, 0, 1);
        g3.makeMove(Player.BLACK, 1, 1);
        g3.makeMove(Player.WHITE, 2, 0);
        g3.printBoard("Start");

        Player[] rot1 = new Player[NxN];
        Player[] rot2 = new Player[NxN];
        Player[] mirrored = new Player[NxN];
        Player[] backup = g3.board; // backup = org

        g3.board = mirror(backup, mirrored);
        g3.printBoard("M");

        g3.board = rotate(backup, rot1);
        g3.printBoard("R");

        g3.board = mirror(rot1, mirrored);
        g3.printBoard("RM");

        g3.board = rotate(rot1, rot2);
        g3.printBoard("RR");

        g3.board = mirror(rot2, mirrored);
        g3.printBoard("RRM");

        g3.board = rotate(rot2, rot1);
        g3.printBoard("RRR");

        g3.board = mirror(rot1, mirrored);
        g3.printBoard("RRRM");

        g3.board = backup;
        g3.printBoard("Check");
    }
}
