public class Dubstep {
	public static String SongDecoder (String song)
	{
    	song = song.replaceAll("WUB", " ");
    	song = song.replaceAll("( ){2,}", " ");
    	return song.trim();
	}

	public static void main(String[] args) {
		SongDecoder("WUBAWUBBWUBCWUB");
	}
}