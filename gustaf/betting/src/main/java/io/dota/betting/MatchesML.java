package io.dota.betting;

import java.util.ArrayList;

/**
 * Created by ElevPC on 2017-03-23.
 */
public class MatchesML extends ArrayList<MatchesML.MatchML> {

    public MatchesML (Read read, int startIndex) {
        for (int i = startIndex; i < read.size(); i++) {
            add(new MatchML(read, i));
        }
    }

    public class MatchML {
        double coef_1, coef_2, last10coef_1, last10coef_2;
        int winner;

        public String toString() {
            return coef_1 + "," + last10coef_1 + "," + coef_2 + "," + last10coef_2 + "," + winner;
        }

        public MatchML (Read read, int index) {
            this.coef_1 = read.get(index).coef_1;
            this.coef_2 = read.get(index).coef_2;
            this.last10coef_1 = Analyse.analyseLastMatches(read.getLastMatches(read.get(index), 1), read.get(index).team_1);
            this.last10coef_2 = Analyse.analyseLastMatches(read.getLastMatches(read.get(index), 2), read.get(index).team_2);
            this.winner = read.get(index).winner;
        }
    }
}
