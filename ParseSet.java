// Example Code for parsing XML file
// Dr. Moushumi Sharmin
// CSCI 345

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ParseSet{

   public Set[] parse(String fileName) {
      try {
          File file = new File(fileName);
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(file);

          NodeList setList = doc.getElementsByTagName("set");

          Set[] tempCards = new Set[setList.getLength()];
          int indexC = 0;

          
          for (int i = 0; i < setList.getLength(); i++) {
              Element setElement = (Element) setList.item(i);
              String name = setElement.getAttribute("name");
              
              
              int budget = Integer.parseInt(setElement.getAttribute("budget"));
              String scene = setElement.getElementsByTagName("scene").item(0).getTextContent();

              

              
              NodeList partList = setElement.getElementsByTagName("part");
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