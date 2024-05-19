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

   public Set[] parse(String fileName, Board b) {
      try {
          File file = new File(fileName);
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(file);

          NodeList setList = doc.getElementsByTagName("set");

          HashMap<String,Set> nameMap = new HashMap<String,Set>();
          Set[] allSets = new Set[setList.getLength()+2];

          //This for loop will get al data besides neighbors and create the sets(2 special sets not included)===========================//
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
              Set[] tempSets = new Set[((Element) setElement.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").getLength()];
              allSets[i] = new Set(b, tempRoles, shots, tempSets, name);
              nameMap.put(name, allSets[i]);
          }
          //===============================================================================================================================//
          Element trailer = (Element) doc.getElementsByTagName("trailer").item(0);
          Set[] tempSets = new Set[((Element) trailer.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").getLength()];
          allSets[setList.getLength()] = new Set(b, null, 0, tempSets, "trailer");
          nameMap.put("trailer", allSets[setList.getLength()]);
          //================================================================================================================================//
          Element office = (Element) doc.getElementsByTagName("office").item(0);
          tempSets = new Set[((Element) office.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").getLength()];
          allSets[allSets.length-1] = new Set(b, null, 0, tempSets, "office");
          nameMap.put("office", allSets[setList.getLength()+1]);
          //===================================================================================================================================//
          for (int i = 0; i < setList.getLength(); i++) {
            Element setElement = (Element) setList.item(i);
            int totalNeighbors = ((Element)setElement.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").getLength();
            for (int j = 0; j < totalNeighbors; j++) {
                allSets[i].addNeighbor(nameMap.get(((Element)((Element)setElement.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").item(j)).getAttribute("name")));
            }
          }
          //================================================================================================================================//
          for (int j = 0; j < ((Element)trailer.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").getLength(); j++) {
            allSets[setList.getLength()].addNeighbor(nameMap.get(((Element)((Element)trailer.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").item(j)).getAttribute("name")));
          }
          for (int j = 0; j < ((Element)office.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").getLength(); j++) {
            allSets[setList.getLength()].addNeighbor(nameMap.get(((Element)((Element)office.getElementsByTagName("neighbors").item(0)).getElementsByTagName("neighbor").item(j)).getAttribute("name")));
          }
          //==================================================================================================================================//
          return allSets;

      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }



}//class