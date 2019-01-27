package io.dota.betting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Read extends ArrayList<Match> {
    public Read () {}
    public Read (String file) {
        Path path = Paths.get(file);
        ArrayList<String> list = null;
        try {
            list = (ArrayList<String>) Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println(e);
        }
        JsonParser parser = new JsonParser();
        for (int i = 0; i < list.size(); i++) {
            JsonObject json = parser.parse(list.get(i)).getAsJsonObject();
            Match initiate = new Match(json.get("id").getAsLong(),
                    json.get("date").getAsLong(),
                    json.get("game").getAsString(),
                    json.get("tourn").getAsString(),
                    json.get("team_1").getAsString(),
                    json.get("team_2").getAsString(),
                    json.get("country_1").getAsString(),
                    json.get("country_2").getAsString(),
                    json.get("winner").getAsShort(),
                    json.get("coef_1").getAsDouble(),
                    json.get("coef_2").getAsDouble(),
                    json.get("bet").getAsString());
            add(initiate);
        }
        removeTies();
    }

    public Read getLastMatches (Match match, int team) {
        Read lastMatches= new Read();
        if(team == 1) {
            int numberOfMatches = 0;
            for(int i = indexOf(match)-1; i >= 0; i--) {
                if(match.team_1.equals(get(i).team_1) || match.team_1.equals(get(i).team_2)) {
                    lastMatches.add(get(i));
                    numberOfMatches++;
                }
                if(numberOfMatches == 10) {
                    break;
                }
            }
            return lastMatches;
        } else if (team == 2) {
            int numberOfMatches = 0;
            for(int i = indexOf(match)-1; i >= 0; i--) {
                if(match.team_2.equals(get(i).team_1) || match.team_2.equals(get(i).team_2)) {
                    lastMatches.add(get(i));
                    numberOfMatches++;
                    if(numberOfMatches == 10) {
                        break;
                    }
                }
            }
            return lastMatches;
        } else {
            System.out.println("WTF");
            return lastMatches;
        }
    }
    public void sortByTeam(String team) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if(get(i).team_1.contains(team) || get(i).team_2.contains(team)) {
                relevantMatches.add(get(i));
            }
        }
        clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            add(relevantMatches.get(i));
        }
    }
    public void sortByGame(String game) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if(get(i).game.contains(game)) {
                relevantMatches.add(get(i));
            }
        }
        clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            add(relevantMatches.get(i));
        }
    }
    public void sortByTournament(String tourn) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if(get(i).tourn.contains(tourn)) {
                relevantMatches.add(get(i));
            }
        }
        clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            add(relevantMatches.get(i));
        }
    }
    public void sortByCoef(double lower ,double upper) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if(get(i).coef_1 >= lower && get(i).coef_1 <= upper || get(i).coef_2 >= lower && get(i).coef_2 <= upper) {
                relevantMatches.add(get(i));
            }
        }
        clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            add(relevantMatches.get(i));
        }
    }
    public void sortByBet(String bet) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if(get(i).bet.contains(bet)) {
                relevantMatches.add(get(i));
            }
        }
        clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            add(relevantMatches.get(i));
        }
    }
    public void sortByWinner(int winner) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if(get(i).winner == winner) {
                relevantMatches.add(get(i));
            }
        }
        clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            add(relevantMatches.get(i));
        }
    }
    public void removeTies() {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if(get(i).winner == 1 ||get(i).winner == 2) {
                relevantMatches.add(get(i));
            }
        }
        clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            add(relevantMatches.get(i));
        }
    }

}
