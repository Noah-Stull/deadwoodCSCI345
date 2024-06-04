import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ParseCard {

    private Card parseCard(Element e) {
        String name = e.getAttribute("name");
        String img = e.getAttribute("img");
        int budget = Integer.parseInt(e.getAttribute("budget"));
        Element sceneElement = (Element) e.getElementsByTagName("scene").item(0);
        String sceneDescription = sceneElement.getTextContent();

        List<Role> roleList = new ArrayList<>();
        NodeList partList = e.getElementsByTagName("part");
        for (int i = 0; i < partList.getLength(); i++) {
            Node partNode = partList.item(i);
            if (partNode.getNodeType() == Node.ELEMENT_NODE) {
                roleList.add(parseRole((Element) partNode));
            }
        }

        Role[] roles = roleList.toArray(new Role[0]);
        return new Card(roles, name, sceneDescription, budget, img);
    }
    //uses parsed role data to create Role object
    private Role parseRole(Element e) {
        String name = e.getAttribute("name");
        int level = Integer.parseInt(e.getAttribute("level"));
        String line = e.getElementsByTagName("line").item(0).getTextContent();
        Element a = (Element) e.getElementsByTagName("area").item(0);
        int x = Integer.parseInt(a.getAttribute("x"));
        int y = Integer.parseInt(a.getAttribute("y"));
        return new Role(name, level, line, 1, x,y);
    }

    // returns array of card objects
    public Card[] parse(String fileName) {
        try{
            File file = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList cardList = doc.getElementsByTagName("card");
            List<Card> cardArrayList = new ArrayList<>();

            for(int i = 0; i < cardList.getLength(); i++) {
                Node cardNode = cardList.item(i);
                if(cardNode.getNodeType() == Node.ELEMENT_NODE) {
                    cardArrayList.add(parseCard((Element) cardNode));
                }
            }

            return cardArrayList.toArray(new Card[0]);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}