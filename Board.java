package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int turn;
    private static int[][] wordScore = null;
    private static int[][] letterScore = null;
    private Tile[][] boardTiles;
    private static Board board = null;
    private List<Word> wordsInBoard;

    private void createScores() {
        wordScore = new int[15][15];
        letterScore = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (i == j) {
                    if (i == 0 || i == 14)
                        wordScore[i][j] = 3;
                    else if ((i >= 1 && i <= 4) || (i >= 10 && i <= 13) || i == 7)
                        wordScore[i][j] = 2;
                    else if (i == 5 || i == 9)
                        letterScore[i][j] = 3;
                    else
                        letterScore[i][j] = 2;
                } else if (i == 14 - j) {
                    if (i == 0 || i == 14)
                        wordScore[i][j] = 3;
                    else if ((i >= 1 && i <= 4) || (i >= 10 && i <= 13))
                        wordScore[i][j] = 2;
                    else if (i == 5 || i == 9)
                        letterScore[i][j] = 3;
                    else
                        letterScore[i][j] = 2;
                } else {
                    if (((i == 0 || i == 14) && j == 7) || ((j == 0 || j == 14) && i == 7)) {
                        wordScore[i][j] = 3;
                    } else if ((i == 0 || i == 14) && (j == 3 || j == 11)) {
                        letterScore[i][j] = 2;
                    } else if ((i == 3 || i == 11) && (j == 0 || j == 14)) {
                        letterScore[i][j] = 2;
                    } else if ((i == 1 || i == 13) && (j == 5 || j == 9)) {
                        letterScore[i][j] = 3;
                    } else if ((j == 1 || j == 13) && (i == 5 || i == 9)) {
                        letterScore[i][j] = 3;
                    } else if ((i == 6 || i == 8) && (j == 2 || j == 12)) {
                        letterScore[i][j] = 2;
                    } else if ((j == 6 || j == 8) && (i == 2 || i == 12)) {
                        letterScore[i][j] = 2;
                    } else if ((i == 3 || i == 11) && j == 7) {
                        letterScore[i][j] = 2;
                    } else if ((j == 3 || j == 11) && i == 7) {
                        letterScore[i][j] = 2;
                    }
                }
            }
        }
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (wordScore[i][j] == 0)
                    wordScore[i][j] = 1;
                if (letterScore[i][j] == 0)
                    letterScore[i][j] = 1;
            }
        }
    }

    private Board() {
        if (letterScore == null && wordScore == null)
            createScores();
        boardTiles = new Tile[15][15];
        turn = 1;
        wordsInBoard = new ArrayList<Word>();;
    }

    public static Board getBoard() {
        if (board == null)
            board = new Board();
        return board;
    }

    Tile[][] getTiles() {
        ///// check if works
        return Arrays.stream(boardTiles).map(Tile[]::clone).toArray(Tile[][]::new);
    }

    boolean passTroughMiddleTile(Word word){
        int row = word.getRow();
        int col = word.getCol();
        int length = word.getTiles().length;
        if(word.isVertical()){
            if(col!=7)
                return false;
            if(row<=7&&row+length>=7)
                return true;
        }
        else{
            if(row!=7)
                return false;
            if(col<=7&&col+length>=7)
                return true;
        }
        return false;
    }
    boolean passTroughOrNearTile(Word word){
        int row = word.getRow();
        int col = word.getCol();
        int length = word.getTiles().length;
        for (int i = 0; i < length; i++) {
            if (word.isVertical()) {
                if (boardTiles[row + i][col] != null)
                    return true;
                if (col + 1 <= 14)
                    if (boardTiles[row + i][col + 1] != null)
                        return true;
                if (col - 1 >= 0)
                    if (boardTiles[row + i][col - 1] != null)
                        return true;
                if (row + 1 <= 14)
                    if (boardTiles[row + i + 1][col] != null)
                        return true;
                if (row - 1 >= 0)
                    if (boardTiles[row + i - 1][col] != null)
                        return true;

            } else {
                if (boardTiles[row][col + i] != null)
                    return true;
                if (col + 1 <= 14)
                    if (boardTiles[row][col + i + 1] != null)
                        return true;
                if (col - 1 >= 0)
                    if (boardTiles[row][col + i - 1] != null)
                        return true;
                if (row + 1 <= 14)
                    if (boardTiles[row + 1][col + i] != null)
                        return true;
                if (row - 1 >= 0)
                    if (boardTiles[row - 1][col + i] != null)
                        return true;
            }
        }
        return false;
    }
    boolean requiresTilesChanges(Word word){
        int row = word.getRow();
        int col = word.getCol();
        int length = word.getTiles().length;
        for (int i = 0; i < length; i++) {
            if(word.getTiles()[i]==null)
                continue;
            if(word.isVertical()){
                if(boardTiles[row+i][col]==null)
                    continue;
                if(boardTiles[row+i][col]!=word.getTiles()[i])
                    return true;
            }
            else{
                if(boardTiles[row][col+i]==null)
                    continue;
                if(boardTiles[row][col+i]!=word.getTiles()[i])
                    return true;
            }
        }
        return false;
    }
    boolean boardLegal(Word word) {
        if (word == null)
            return false; //?!?!
        int row = word.getRow();
        int col = word.getCol();
        int length = word.getTiles().length;
        if (row > 14 || row < 0 || col > 14 || col < 0)
            return false;
        if (word.isVertical() && row + length > 14)
            return false;
        else if (!word.isVertical() && col + length > 14)
            return false;
        //turn 1 must use the middle tile
        if (turn == 1) {
            return passTroughMiddleTile(word);
        }
        return passTroughOrNearTile(word)&&!requiresTilesChanges(word);
    }

    boolean dictionaryLegal(Word word) {
        return true;
    }
    int scoreOfNewWordsCreated(Word word){
        int wordCol = word.getCol();
        int wordRow = word.getRow();
        int wordLength = word.getCol();
        int newWordCol;
        int newWordRow;
        int newWordLength;
        int score = 0;
        //////////!!!!!

        return score;
    }
    int getScore(Word word) {
        if (word == null || !boardLegal(word) || !dictionaryLegal(word))
            return 0;
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;
        int lettersScore = 0;
        int wordBonusesMultipleBy = 1;
        for (int i = 0; i < wordLength; i++) {
            if (word.isVertical()) {
                if(word.getTiles()[i]==null){
                    lettersScore += (boardTiles[i + row][col].score) * (letterScore[i + row][col]);
                    wordBonusesMultipleBy *= wordScore[i + row][col];
                    continue;
                }
                lettersScore += (word.getTiles()[i].score) * (letterScore[i + row][col]);
                wordBonusesMultipleBy *= wordScore[i + row][col];
            } else {
                if(word.getTiles()[i]==null){
                    lettersScore += (boardTiles[row][i + col].score) * (letterScore[row][i + col]);
                    wordBonusesMultipleBy *= wordScore[row][i + col];
                    continue;
                }
                lettersScore += (word.getTiles()[i].score) * (letterScore[row][i + col]);
                wordBonusesMultipleBy *= wordScore[row][i + col];
            }
        }
        return lettersScore * wordBonusesMultipleBy;
    }
    int tryPlaceWord(Word word){
        if(!dictionaryLegal(word)||!boardLegal(word)||word==null)
            return 0;

        int row = word.getRow();
        int col = word.getCol();
        int length = word.getTiles().length;
        for (int i = 0; i < length; i++) {
            if(word.isVertical()) {
                if (word.getTiles()[i] == null)
                    continue;
                boardTiles[row + i][col] = word.getTiles()[i];
            }
            else
            if (word.getTiles()[i] == null)
                continue;
                boardTiles[row][col+i] = word.getTiles()[i];

        }
        wordsInBoard.add(word);
        int scoreOfNewWords = scoreOfNewWordsCreated(word);
        if(turn==1){
            int score = getScore(word);
            turn++;
            wordScore[7][7]=1;
            return score + scoreOfNewWords;
        }
        return getScore(word) + scoreOfNewWords;
    }
}
