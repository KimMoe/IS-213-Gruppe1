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
public class State {
    static final int N = 3;
    int key;


    public State(Player[] p) {
        key = boardToKey(p);
    }


    private static Player[] rotate(Player[] src, Player[] rot) {
        for (int i = 0; i < src.length; i++) {
            int c = i % N;
            int r = (i - c) / N;
            int iRot = N * c + N - r - 1;
            rot[iRot] = src[i];
        }
        return rot;
    }


    private static Player[] rotate(Player[] board) {
        return rotate(board, new Player[board.length]);
    }


    static int boardToKey(Player[] board) {
        int sum = 0;
        for (int i = N * N - 1; i >= 0; i--)
            sum = N * sum + board[i].ordinal();
        return sum;
    }

    private static final Player[] playerValues = Player.values();


    static Player[] keyToBoard(int key) {
        Player[] board = new Player[N * N];
        for (int i = 0; i < N * N; i++) {
            int digit = key % N;
            key = key / N;
            Player p = playerValues[digit];
            board[i] = p;
        }
        return board;
    }


    private static void printBoard(Player[] board, String header) {
        System.out.format("\n%s\n", header);
        System.out.format("|%s%s%s|\n", board[0].symbol, board[1].symbol, board[2].symbol);
        System.out.format("|%s%s%s|\n", board[3].symbol, board[4].symbol, board[5].symbol);
        System.out.format("|%s%s%s|\n", board[6].symbol, board[7].symbol, board[8].symbol);
    }


    public static void main(String[] args) {
        Player[] board = new Player[N * N];
        for (int i = 0; i < board.length; i++)
            board[i] = Player.NONE;
        printBoard(board, "Empty");
        Player p = Player.BLACK;
        board[1] = p;
        board[4] = p;
        printBoard(board, "tip");
        int k = boardToKey(board);
        System.out.println("key is " + k);
        board = keyToBoard(k);
        printBoard(board, "converted");

        board = rotate(board);
        printBoard(board, "converted");
        System.out.println("key is " + boardToKey(board));

        board = rotate(board);
        printBoard(board, "converted");
        System.out.println("key is " + boardToKey(board));

        board = rotate(board);
        printBoard(board, "converted");
        System.out.println("key is " + boardToKey(board));

    }
}
