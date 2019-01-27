package io.dota.betting;

import java.util.ArrayList;

public class Analyse {
    Read read;
    public Analyse (Read read) {
        this.read = read;
    }
    public void analyseMatches(int start) {
        double value = 1;
        for (int i = start; i < read.size(); i++) {
            int guess = analyseCoef(read.get(i), 1,2);
            //int guess = analyseMethod1(read.get(i), read);
            if(guess != 0) {
                value--;
                if (guess == read.get(i).winner) {
                    if (guess == 1) value +=  (read.get(i).coef_1);
                    if (guess == 2) value +=  (read.get(i).coef_2);
                }
            }
        }
        System.out.println(value);
    }

    public static float analyseLastMatches(Read read,  String team) {
        float value = 1;
        for (int i = 0; i < read.size(); i++) {
            if(read.get(i).team_1.equals(team)) {
                if(read.get(i).winner == 1) {
                    value += read.get(i).coef_1 - 1;
                } else if (read.get(i).winner == 2) {
                    value -= 1;
                } else {
                    System.out.println("ERROR 1fsdf");
                }
            }
            if(read.get(i).team_2.equals(team)) {
                if(read.get(i).winner == 2) {
                    value += read.get(i).coef_2 - 1;
                } else if (read.get(i).winner == 1) {
                    value -= 1;
                } else {
                    System.out.println("ERROR 1fsbfdrg");
                }
            }
        }
        return value;
    }
    public double analyseTeam (String team, boolean with) {
        read.sortByTeam(team);
        double value = 100;
        if(with) {
            for (int i = 0; i < read.size(); i++) {
                double coef;
                if (read.get(i).team_1.contains(team)) {
                    coef = read.get(i).coef_1;
                    if (read.get(i).winner == 1) {
                        value += value/10 * (coef - 1);
                    } else if (read.get(i).winner == 2) {
                        value -= value/10;
                    }
                }
                if (read.get(i).team_2.contains(team)) {
                    coef = read.get(i).coef_2;
                    if (read.get(i).winner == 2) {
                        value += value/10 * (coef - 1);
                    } else if (read.get(i).winner == 1) {
                        value -= value/10;
                    }
                }
            }
            return value;
        } else {
            for (int i = 0; i < read.size(); i++) {
                double coef;
                if (read.get(i).team_1.contains(team)) {
                    coef = read.get(i).coef_2;
                    if (read.get(i).winner == 2) {
                        value += coef - 1;
                    } else if (read.get(i).winner == 1) {
                        value--;
                    }
                }
                if (read.get(i).team_2.contains(team)) {
                    coef = read.get(i).coef_1;
                    if (read.get(i).winner == 1) {
                        value += coef - 1;
                    } else if (read.get(i).winner == 2) {
                        value--;
                    }
                }
            }
            return value;
        }
    }
    public int analyseMethod1 (Match match, Read read) {
        this.read = read;
        Read team1matches = read.getLastMatches(match, 1);
        Read team2matches = read.getLastMatches(match, 2);
        Analyse analyseTeam1 = new Analyse(team1matches);
        Analyse analyseTeam2 = new Analyse(team2matches);
        double team1with = analyseTeam1.analyseTeam(match.team_1, true);
        double team1against = analyseTeam1.analyseTeam(match.team_1, false);
        double team2with = analyseTeam2.analyseTeam(match.team_2, true);
        double team2against = analyseTeam2.analyseTeam(match.team_2, false);
        double team1coef = team1with * team2against;
        double team2coef = team2with * team1against;
        double last1 = team1coef - match.coef_1;
        double last2 = team2coef - match.coef_2;
        //System.out.println("Team 1: " + match.coef_1 + "    " + team1coef);
        //System.out.println("Team 2: " + match.coef_2 + "    " + team2coef);
        if (team1coef == 1.0) {
            System.out.println("NOT NUFF");
            return 0;
        }
        if (last1 < last2) return 1;
        if (last1 > last2) return 2;
        return 0;
    }
    public int analyseCoef (Match match, double lower, double upper) {
        if(match.coef_1 < upper && match.coef_1 > lower) {
            return 1;
        } else if(match.coef_2 < upper && match.coef_2 > lower) {
            return 2;
        }
        return 0;
    }
    public void sortByTeam(String team) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < read.size(); i++) {
            if(read.get(i).team_1.contains(team) || read.get(i).team_2.contains(team)) {
                relevantMatches.add(read.get(i));
            }
        }
        read.clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            read.add(relevantMatches.get(i));
        }
    }
    public void sortByGame(String game) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < read.size(); i++) {
            if(read.get(i).game.contains(game)) {
                relevantMatches.add(read.get(i));
            }
        }
        read.clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            read.add(relevantMatches.get(i));
        }
    }
    public void sortByTournament(String tourn) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < read.size(); i++) {
            if(read.get(i).tourn.contains(tourn)) {
                relevantMatches.add(read.get(i));
            }
        }
        read.clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            read.add(relevantMatches.get(i));
        }
    }
    public void sortByCoef(double lower ,double upper) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < read.size(); i++) {
            if(read.get(i).coef_1 >= lower && read.get(i).coef_1 <= upper || read.get(i).coef_2 >= lower && read.get(i).coef_2 <= upper) {
                relevantMatches.add(read.get(i));
            }
        }
        read.clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            read.add(relevantMatches.get(i));
        }
    }
    public void sortByBet(String bet) {
        ArrayList<Match> relevantMatches = new ArrayList<>();
        for (int i = 0; i < read.size(); i++) {
            if(read.get(i).bet.contains(bet)) {
                relevantMatches.add(read.get(i));
            }
        }
        read.clear();

        for (int i = 0; i < relevantMatches.size(); i++) {
            read.add(relevantMatches.get(i));
        }
    }
}
