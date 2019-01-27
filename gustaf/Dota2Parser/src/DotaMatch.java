import java.io.IOException;

public class DotaMatch {
    String[] matchData;
    int id, date;
    String team1, team2, tournament;
    double coef1, coef2;
    String parsedInfo;

    public static void main(String[] args) throws IOException {
        new DotaMatch(229531);
    }

    public DotaMatch (int id) throws IOException {
        matchData = GetInfo.getInfo(id);
        this.id = id;
        for (int i = 0; i < matchData.length; i++) {
            if(matchData[i].length() > 8)
                checkData(matchData[i]);
        }
        parsedInfo = this.id + "," + date + "," + tournament + "," + team1 + "," + team2 + "," + coef1 + "," + coef2 + "\r\n";
        System.out.print(parsedInfo);
    }

    public void checkData (String data) {
        if(team2 == null && "gamer_2".equals(data.substring(1,8))){
            team2 = data.substring(19, data.length()-9);
        }
        if(team1 == null && "gamer_1".equals(data.substring(1,8))){
            team1 = data.substring(19, data.length()-9);
        }
        if(tournament == null && "tourn".equals(data.substring(1,6))){
            tournament = data.substring(9, data.length()-1);
        }
        if(coef2 == 0 && "coef_2".equals(data.substring(1,7))){
            coef2 = Double.valueOf(data.substring(10, data.length()-1));
        }
        if(coef1 == 0 && "coef_1".equals(data.substring(1,7))){
            coef1 = Double.valueOf(data.substring(10, data.length()-1));
        }
        if("date".equals(data.substring(1,5))) {
            date = Integer.valueOf(data.substring(7));
        }
    }
}
