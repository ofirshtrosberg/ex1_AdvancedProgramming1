package test;

import java.util.Arrays;

public class Board {
    private static int[][] wordScore;
    private static int[][] letterScore;
    private Tile[][] boardTiles;
    private static Board board = null;
    private Board(){
        wordScore = new int[15][15];
        letterScore = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if(i==j) {
                    if (i == 0||i==14)
                        wordScore[i][j] = 3;
                    else if((i>=1&&i<=4)||(i>=10&&i<=13)||i==7)
                        wordScore[i][j] = 2;
                    else if(i==5||i==9)
                        letterScore[i][j] = 3;
                    else
                        letterScore[i][j] =2;
                }
                else if(i==14-j) {
                    if (i == 0||i==14)
                        wordScore[i][j] = 3;
                    else if((i>=1&&i<=4)||(i>=10&&i<=13))
                        wordScore[i][j] = 2;
                    else if(i==5||i==9)
                        letterScore[i][j] = 3;
                    else
                        letterScore[i][j] =2;
                }
                else{
                    if(((i==0||i==14)&&j==7)||((j==0||j==14)&&i==7)){
                        wordScore[i][j] = 3;
                    }
                    else if((i==0||i==14)&&(j==3||j==11)){
                        letterScore[i][j] = 2;
                    }
                    else if((i==3||i==11)&&(j==0||j==14)){
                        letterScore[i][j] = 2;
                    }
                    else if((i==1||i==13)&&(j==5||j==9)){
                        letterScore[i][j] = 3;
                    }
                    else if((j==1||j==13)&&(i==5||i==9)){
                        letterScore[i][j] = 3;
                    }
                    else if((i==6||i==8)&&(j==2||j==12)){
                        letterScore[i][j] = 2;
                    }
                    else if((j==6||j==8)&&(i==2||i==12)){
                        letterScore[i][j] = 2;
                    }
                    else if((i==3||i==11)&&j==7){
                        letterScore[i][j] = 2;
                    }
                    else if((j==3||j==11)&&i==7) {
                        letterScore[i][j] = 2;
                    }
                }
            }
        }
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if(wordScore[i][j]==0)
                    wordScore[i][j]=1;
                if(letterScore[i][j]==0)
                    letterScore[i][j]=1;
            }
        }
    }
    public static Board getBoard()
    {
        System.out.println(Arrays.deepToString(wordScore));
        System.out.println(Arrays.deepToString(letterScore));
        if (board == null)
            board = new Board();
        return board;
    }
}
