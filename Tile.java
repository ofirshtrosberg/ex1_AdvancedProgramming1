package test;
import java.util.Arrays;
import java.util.Random;

import java.util.Objects;

public class Tile {
    final char letter;
    final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof Tile){
            Tile tile = (Tile)o;
            return (letter == tile.letter && score == tile.score);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    public static class Bag {
        static int[] tilesAmounts;
        static Tile[] tiles;
        private static Bag bag = null;
        private Bag() {
            tilesAmounts = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            tiles = new Tile[]{new Tile('A', 1), new Tile('B', 3), new Tile('C', 3), new Tile('D', 2), new Tile('E', 1), new Tile('F', 4), new Tile('G', 2), new Tile('H', 4), new Tile('I', 1), new Tile('J', 8), new Tile('K', 5), new Tile('L', 1), new Tile('M', 3), new Tile('N', 1), new Tile('O', 1), new Tile('P', 3), new Tile('Q', 10), new Tile('R', 1), new Tile('S', 1), new Tile('T', 1), new Tile('U', 1), new Tile('V', 4), new Tile('W', 4), new Tile('X', 8), new Tile('Y', 4), new Tile('Z', 10)};
        }
        boolean isBagEmpty(){
            for (int tileAmount : tilesAmounts)
                if (tileAmount != 0)
                    return false;
            return true;
        }
        Tile getRand(){
            if(isBagEmpty())
                return null;
            Random rand = new Random();
            int randIndex = rand.nextInt(26);
            while(tilesAmounts[randIndex]==0){
                randIndex = rand.nextInt(26);
            }
            //!!!!!!?????
            tilesAmounts[randIndex]--;
            return tiles[randIndex];
        }
        boolean isLetterValid(char letter){
            return (letter>=65 && letter<=90);
        }
        Tile getTile(char tileLetter){
            if(isBagEmpty())
                return null;
            if(!isLetterValid(tileLetter))
                return null;
            for(int i=0;i<26;i++){
                if(tiles[i].letter==tileLetter && tilesAmounts[i]>0){
                    tilesAmounts[i]--;
                    return tiles[i];
                }
            }
            return null;
        }

        int size(){
            int size = 0;
            for(int tileAmount: tilesAmounts){
                size+= tileAmount;
            }
            return size;
        }
        void put(Tile tile){
            if(tile!=null) {
                int index = tile.letter - 65;
                if (size() < 98) {
                    tilesAmounts[index]++;
                }
            }
        }
        int[] getQuantities(){
            int[] tilesAmountsClone = Arrays.copyOf(tilesAmounts, tilesAmounts.length);
            return tilesAmountsClone;
        }
        static Bag getBag(){
            if(bag==null){
                bag = new Bag();
            }
            return bag;
        }
    }
}