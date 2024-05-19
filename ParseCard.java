

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ParseCard{

   public Card[] parse(String fileName) {
      try {
          File file = new File(fileName);
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(file);

          NodeList cardList = doc.getElementsByTagName("card");

          Card[] tempCards = new Card[cardList.getLength()];
          int indexC = 0;
          for (int i = 0; i < cardList.getLength(); i++) {
              Element cardElement = (Element) cardList.item(i);
              String name = cardElement.getAttribute("name");
              String img = cardElement.getAttribute("img");
              int budget = Integer.parseInt(cardElement.getAttribute("budget"));
              String scene = cardElement.getElementsByTagName("scene").item(0).getTextContent();

              

              
              NodeList partList = cardElement.getElementsByTagName("part");
              Role[] rTemp = new Role[partList.getLength()];
              int index = 0;
              for (int j = 0; j < partList.getLength(); j++) {
                  Element partElement = (Element) partList.item(j);
                  String partName = partElement.getAttribute("name");
                  int level = Integer.parseInt(partElement.getAttribute("level"));
                  String line = partElement.getElementsByTagName("line").item(0).getTextContent();
                  Role role = new Role(partName, level, line, 1);
                  rTemp[index] = role;
                  index++;
              }
              Card card = new Card(rTemp, name, img, budget, scene);
              tempCards[indexC] = card;
              
          }
          return tempCards;
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }



}//class