import java.io.File;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

import java.util.HashMap;
import java.util.Stack;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
  
public class XMLParser {
	private String outputText;
	private String outputColor;
	private String outputUsername;
	private String outputCrypto;
	private String outputRequest;
	
	public XMLParser(String user, String cryptoType, String key, String color, String cryptMessage, String message){ //Två konstruktorerna nedan utgår bara utifrån två olika anrop och använder utifrån det givna anropet rätt metod (skapa XML eller parse XML)
		createXML(user, cryptoType, key, color, cryptMessage, message);
	}
	
	public XMLParser(String message) { //Här får vi bara in ett meddelanden som ska parseas
		parseXML(message);
	}
	
	private void createXML(String user, String cryptoTypeString, String key, String colorString, String message, String uncryptMessage){
  
        try {
  
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance(); //Skapar XML i form av ett dokument först för att få rätt struktur
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
            Document xmldoc = docBuilder.newDocument();
            Element root = xmldoc.createElement("message");	//Vill ha taggen "message" som yttersta tag. 
            xmldoc.appendChild(root);
 
            Attr username = xmldoc.createAttribute("username");
            username.setValue(user);
            root.setAttributeNode(username); 
             
            Element text = xmldoc.createElement("text");
            root.appendChild(text);
             
            Attr color = xmldoc.createAttribute("color");
            color.setValue(colorString);
            text.setAttributeNode(color);

            Element encrypt = xmldoc.createElement("encrypted");
            text.appendChild(encrypt);

            Attr cryptoType = xmldoc.createAttribute("encryptType");
            cryptoType.setValue(cryptoTypeString);
            encrypt.setAttributeNode(cryptoType);

            Element request = xmldoc.createElement("request");
            encrypt.appendChild(request);

            Attr requestType = xmldoc.createAttribute("requestType");
			requestType.setValue("none");

			if (uncryptMessage.equals("<keyRequest>")) {
            	requestType.setValue("keyRequest");
            	cryptoType.setValue("none");
	            request.appendChild(xmldoc.createTextNode("\n" + "Motparten ber om din publika nyckel. Tillåt?" + "\n")); //Lägger till radbyten för att enkelt kunna parsea senare
	        } else if (uncryptMessage.equals("<sendKey>")) {
            	System.out.println("sending key to server: |" + key + "|");
            	cryptoType.setValue("none");
            	requestType.setValue("keyAnswer");
	            request.appendChild(xmldoc.createTextNode("\n" + key + "\n")); //Lägger till radbyten för att enkelt kunna parsea senare
            } else if (uncryptMessage.equals("<disconnectUser>")) {
           		cryptoType.setValue("none");
           		System.out.println("disconnecting user: " + user);
           		request.appendChild(xmldoc.createTextNode("\n" + user + " har loggat ut" + "\n<disconnect />"));
           	} else if (uncryptMessage.startsWith("<filerequest>")){
				cryptoType.setValue("none");
				requestType.setValue("fileRequest");
				String[] fileInfo = uncryptMessage.split(" ");
				String[] fileDir = fileInfo[1].split("\\\\");
				request.appendChild(xmldoc.createTextNode("\n" + fileDir[fileDir.length-1] + " " + fileInfo[2] + "\n"));
			}
			if (uncryptMessage.equals("<sendFile>")) {
				cryptoType.setValue("none");
				System.out.println("Sending File");
				
			}else {
	            request.appendChild(xmldoc.createTextNode("\n" + message + "\n")); //Lägger till radbyten för att enkelt kunna parsea senare
            }



            request.setAttributeNode(requestType);
             
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xmldoc);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //StreamResult result = new StreamResult(new File("file.xml")); //Skapar dock inte en fil
            outputText = toString(xmldoc); //Använder en egen toString(), vilken bevarar XML layout inkl. rader
 
//          transformer.transform(source, result);
      
      
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
        }
    }

	private void parseXML2(String xmessage){
		//System.out.println(xmessage);
		try {
			String lines[] = xmessage.split("\\r?\\n"); //Skapar array lines med varje rad i meddelandesträngen som ett array-element
			int listLength = lines.length;
			int tillEnd = lines[1].length(); //Eftersom vi skapat den XML-layoutade strängen vet vi strukturen och kan därför per automatik lösa ut viktig information. Ex. användarnamn, krypteringsmetod, färg... 
			String username = lines[1].substring(19,(tillEnd-2));
			int tillEnd2 = lines[2].length();
			String color = lines[2].substring(13,(tillEnd2-2));
			int tillEnd3 = lines[3].length();
			String crypto = lines[3].substring(17, (tillEnd3-2));
			if (lines[3].startsWith("<encrypted")) { //DENNA IF KAN VI NOG TA BORT, ALLA BÖRJAR JU SÅ NU!
				String message = lines[5];
				if (lines[7].equals("</encrypted>")) { //Kollar igenom så att alla sluttaggar finns. Fångar annars felet med vår catch.
					if (lines[8].equals("</text>")) {
						if (lines[9].equals("</message>")) {
							outputText = message; //Genom att inte låta vår parseXML returnera något blir det lätt för server/klient att hämta den information som efterfrågas via metoderna getText() o.s.v. nedan. 
							outputColor = color;
							outputUsername = username;
							outputCrypto = crypto;
						}
					}
				}
			}
		}
		catch (Exception e){
			System.out.println("Message error");
			//return errorMessage;
		}
	
	
	}

	private void parseXML(String xmessage) {
		Stack<String> messageStack = new Stack<String>();
		HashMap<String, String> messageMap = new HashMap<String, String>();
		String lines[] = xmessage.split("\\r?\\n");
		int i = 0;
		boolean correctXML = true;
		while (lines[++i].startsWith("<")) {
			String[] tempSplit = lines[i].split(" ");
			messageStack.push(tempSplit[0].substring(1));
			String[] mapSplit = tempSplit[1].split("=");
			System.out.println(mapSplit[0] + "|   |" + mapSplit[1].substring(1, mapSplit[1].length()-2));
			messageMap.put(mapSplit[0], mapSplit[1].substring(1, mapSplit[1].length()-2));
		}
		messageMap.put("message", lines[i]);
		while (i < lines.length-1 && lines[++i].startsWith("<")) {
			if (!lines[i].startsWith("</" + messageStack.pop())) {
				correctXML = false;
			}
		}
		if (correctXML) {
			outputText = messageMap.get("message");
			outputColor = messageMap.get("color");
			outputUsername = messageMap.get("username");
			outputCrypto = messageMap.get("encryptType");
			outputRequest = messageMap.get("requestType");

		} else {
			System.out.println("fel skedde");
		}

	}

	public String getText() { 
		return outputText;
	}

	public String getUsername() {
		return outputUsername;
	}

	public String getColor() {
		return outputColor;
	}

	public String getCrypto() {
		return outputCrypto;
	}

	public String getRequest() {
		return outputRequest;
	}
 
    public static String toString(Document doc) { //XML-dokument till en sträng
    try {
        StringWriter sw = new StringWriter(); 
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
 
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString().trim(); //returnerar en sträng på XML-form. 
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }
/*
	public static void main(String argv[]) { //DENNA TAR VI VÄL BORT SEDAN?
		XMLParser test = new XMLParser("max", "1", "sadofiasdifasodiuagd", "#00ff00", "hejsan", "hejsan");
		String testString = test.getText();
		System.out.println(testString);
		XMLParser test2 = new XMLParser(testString);
		System.out.println(test2.getText());
		System.out.println(test2.getColor());
		System.out.println(test2.getUsername());
		System.out.println(test2.getCrypto());

	}
*/
}