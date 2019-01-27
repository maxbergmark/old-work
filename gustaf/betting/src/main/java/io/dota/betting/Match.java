package io.dota.betting;

import com.google.gson.JsonObject;

public class Match implements Comparable<Match> {
    long id, date;
    String game, tourn, team_1, team_2, country_1, country_2, bet;
    short winner;
    double coef_1, coef_2;

    public Match (long id, long date, String game, String tourn, String team_1, String team_2,
                  String country_1, String country_2, short winner, double coef_1, double coef_2, String bet) {
        this.id = id;
        this.date = date;
        this.game = game;
        this.tourn = tourn;
        this.team_1 = team_1;
        this.team_2 = team_2;
        this.country_1 = country_1;
        this.country_2 = country_2;
        this.winner = winner;
        this.coef_1 = coef_1;
        this.coef_2 = coef_2;
        this.bet = bet;
    }

    public JsonObject toJson () {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("date", date);
        json.addProperty("game", game);
        json.addProperty("tourn", tourn);
        json.addProperty("team_1", team_1);
        json.addProperty("team_2", team_2);
        json.addProperty("country_1", country_1);
        json.addProperty("country_2", country_2);
        json.addProperty("winner", winner);
        json.addProperty("coef_1", coef_1);
        json.addProperty("coef_2", coef_2);
        json.addProperty("bet", bet);
        return json;
    }
    public String toString () {
        return String.valueOf(toJson());
    }

    @Override
    public int compareTo(Match match) {
        return (int)(id-match.id);
    }
}
