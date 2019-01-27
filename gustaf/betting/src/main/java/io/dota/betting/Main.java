package io.dota.betting;

import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws Exception {
        //long start = System.nanoTime();
        //ArrayList<Match> matches = Get.getAll(240000, 245000);
        //System.out.println((System.nanoTime() - start) / 1e9);
        //Get.writeToFile(matches);
        //new ML("Train.arff", "Test.arff");
        Read read = new Read("matches.txt");
        read.sortByGame("Dota2");
        read.sortByBet("normal");
        //System.out.println(read.size());
        //MatchesML mlmatches = new MatchesML(read, 1500);
        //Get.writeToFile("MLParsed.txt", mlmatches);
        //Get.writeToFile("dotamatches.txt", read);
        Analyse analyse = new Analyse(read);
        analyse.analyseMatches(1900);
        //System.out.println(analyse.analyseTeam("OG", false));*/
    }
}
