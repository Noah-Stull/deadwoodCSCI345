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
import java.util.HashMap;

public class ParseSet{

   public Set[] parse(String fileName) {
      try {
          File file = new File(fileName);
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(file);

          NodeList setList = doc.getElementsByTagName("set");

          HashMap<String,Set> nameMap = new HashMap<String,Set>();
          Set[] allSets = new Set[setList.getLength()+2];
          for (int i = 0; i < setList.getLength(); i++) {
              Element setElement = (Element) setList.item(i);
              String name = setElement.getAttribute("name");

              int totalRoles = ((Element) setElement.getElementsByTagName("parts").item(0)).getElementsByTagName("part").getLength();
              Role[] tempRoles = new Role[totalRoles];
              for (int j = 0; j < totalRoles; j++) {
                Element Role = ((Element) ((Element) setElement.getElementsByTagName("parts").item(j)).getElementsByTagName("part").item(j));
                String roleName = Role.getAttribute("name");
                int rank = Integer.parseInt(Role.getAttribute("level"));
                String catchPhrase = Role.getAttribute("line");
                tempRoles[j] = new Role(roleName,rank,catchPhrase,2);
              }
              int shots = ((Element) setElement.getElementsByTagName("takes").item(0)).getElementsByTagName("take").getLength();

              

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