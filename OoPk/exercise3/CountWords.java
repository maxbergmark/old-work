import java.util.regex.*;

public class CountWords {

	public static void main(String[] args) {
		String s = "Ett test där ordet ett ska räknas. ettan ballett! ett. ett";
		// teststräng med flera förekomster av ordet "ett"
		s = s.toLowerCase();	// gör om till lowercase för enklare jämförelse

		int found = 0;
		String target = " ett ";	
		// naiv lösning för att hitta ordet "ett", 
		// men inte matcha på ord som "ballett" eller "ettan"

		int lastIndex = 0;	// index för senast hittade förekomst av target i s

		while (true) {
			lastIndex = s.indexOf(target, lastIndex);
			// ger ut positionen för nästa förekomst av target i s, eller -1

			if (lastIndex != -1) {	// om en förekomst hittades
				found++;
				lastIndex += target.length();	
				// se till att nästa förekomst ligger 
				// efter den nuvarande förekomsten
			} else {
				break;
			}
		}

		System.out.println(found);

		String patternString = "\\bett\\b";	
		// regex-sträng som matchar på ordet "ett"
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(s);	
		// kompilerar en matcher som används för att söka i strängen

		int count2 = 0;

		if (matcher.find()) {	
		// kontrollera först att det finns åtminstone en förekomst av ordet i s

			do {
				count2++;
			} while(matcher.find(matcher.end()));	
			// börja alla sökningar där den förra förekomsten slutade
		}

		System.out.println(count2);

	}
}