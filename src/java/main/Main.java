package main;

import main.model.Hands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(play(readFile("/Users/jchen/IdeaProjects/poker-hand-sorter/src/resources/poker-hands.txt")));
    }

    private static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

    private static String play(String string){
        int result = 0;
        int p1 = 0, p2 = 0;

        for(String line: string.split("\n")){
            Hands h1 = new Hands(line.substring(0,14));
            Hands h2 = new Hands(line.substring(15,29));
            h1.determineCombination();
            h2.determineCombination();
            result = h1.beat(h2);
            if (result > 0) {
                p1++;
            } else {
                p2++;
            }
            System.out.println(line+" - "+result + "  -  "+h1.getCombination().name() +"  -  "+h2.getCombination().name());
        }

        return "Player 1: "+p1+"\nPlayer 2: "+p2;
    }


}
