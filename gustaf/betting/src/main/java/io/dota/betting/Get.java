package io.dota.betting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;

public class Get {
    static int numberAdded = 0;
    static int numberFailed = 0;
    //public Get () {}
    public static void writeToFile (String document, ArrayList<MatchesML.MatchML> matches) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < matches.size(); i++) {
            lines.add(matches.get(i).toString());
        }
        Path file = Paths.get(document);
        Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    public static ArrayList<Match> getAll (int lower, int upper) throws InterruptedException {
        ArrayList matches = new ArrayList<Match>();
        ArrayList<Integer> failed = new ArrayList<>();
        ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(7, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        final CountDownLatch countdown = new CountDownLatch(upper-lower);
        for (int i = lower; i < upper; ++i) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Added: " + numberAdded + " Failed: " + numberFailed + " Total: " + (numberFailed + numberAdded));
                    try {
                        Match match = getMatch(finalI);
                        synchronized ((Integer)numberAdded) { numberAdded++; }
                        synchronized (matches) {
                            matches.add(match);
                        }
                    } catch(IOException e) {
                        failed.add(finalI);
                        synchronized ((Integer)numberFailed) { numberFailed++; }
                    }
                    countdown.countDown();
                }
            });
        }
        countdown.await(500, TimeUnit.SECONDS);
        threadPool.shutdown();
        System.out.println(failed.size());
        for (int i = 0; i < failed.size(); i++) {
            try {
                System.out.println(failed.size()-i);
                Match match = getMatch(failed.get(i));
                matches.add(match);
            } catch (IOException e) {
                System.out.println(e);
                failed.add(i);
            }
        }
        Collections.sort(matches);
        return matches;
    }
    private static Match getMatch(int id) throws IOException {
        URLConnection connection = new URL("https://egb.com/bets/" + id).openConnection();
        connection.setConnectTimeout(10 * 1000);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            sb.append(line);
        }
        String content = sb.toString();
        if(id%100 == 0) { System.out.println(id); }
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(content).getAsJsonObject();
        return jsonToMatch(json);
    }
    public static Match jsonToMatch (JsonObject json) {
        JsonObject bet = json.getAsJsonObject("bet");
        if(!bet.has("parent_gamer_1")) {
            JsonObject gamer_1 = bet.getAsJsonObject("gamer_1");
            JsonObject gamer_2 = bet.getAsJsonObject("gamer_2");
            return new Match(bet.get("id").getAsLong(),
                             bet.get("date").getAsLong(),
                             bet.get("game").getAsString(),
                             bet.get("tourn").getAsString(),
                             gamer_1.get("nick").getAsString(),
                             gamer_2.get("nick").getAsString(),
                             gamer_1.get("flag").getAsString(),
                             gamer_2.get("flag").getAsString(),
                             bet.get("winner").getAsShort(),
                             bet.get("coef_1").getAsDouble(),
                             bet.get("coef_2").getAsDouble(),
                             "normal");
        } else if (bet.has("parent_gamer_1")) {
            JsonObject gamer_1 = bet.getAsJsonObject("gamer_1");
            //JsonObject gamer_2 = bet.getAsJsonObject("gamer_2");
            JsonObject parent_gamer_1 = bet.getAsJsonObject("parent_gamer_1");
            JsonObject parent_gamer_2 = bet.getAsJsonObject("parent_gamer_2");
            return new Match(bet.get("id").getAsLong(),
                             bet.get("date").getAsLong(),
                             bet.get("game").getAsString(),
                             bet.get("tourn").getAsString(),
                             parent_gamer_1.get("nick").getAsString(),
                             parent_gamer_2.get("nick").getAsString(),
                             parent_gamer_1.get("flag").getAsString(),
                             parent_gamer_2.get("flag").getAsString(),
                             bet.get("winner").getAsShort(),
                             bet.get("coef_1").getAsDouble(),
                             bet.get("coef_2").getAsDouble(),
                             gamer_1.get("nick").getAsString());
        }
        return null;
    }
}
