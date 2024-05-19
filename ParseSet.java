import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ParseSet{

   private HashMap<String,Set> stringMap; //used for neighbors
   private List<Element> elist; //keeps track of all set elements

   private Set parseSet(Element e, Board b) {
     String name = e.getAttribute("name");
     System.out.println(name + " - dsetName");
     int shots = e.getElementsByTagName("take").getLength();
     List<Role> roleList = new ArrayList<>();
     for (int i = 0; i < e.getElementsByTagName("part").getLength();i++) {
        Node er = e.getElementsByTagName("part").item(i);
         if (er.getNodeType() == Node.ELEMENT_NODE) {
          roleList.add(parseRole((Element) er));
         }
     }
     int neighbors = 0;
     for (int i = 0 ; i < e.getElementsByTagName("neighbor").getLength();i++) {
       if (e.getElementsByTagName("neighbor").item(i).getNodeType() == Node.ELEMENT_NODE) {
        neighbors++;
       }
     }
     Set s = new Set(b,roleList.toArray(new Role[0]),shots,new Set[neighbors],name);
     stringMap.put(name, s);
     return s;
   }
   private Role parseRole(Element e) {
     String name = e.getAttribute("name");
     System.out.println("name" + name);
     int rank = Integer.parseInt(e.getAttribute("level"));
     String line = "";
     for (int i = 0 ; i < e.getElementsByTagName("line").getLength();i++){
      if(e.getElementsByTagName("line").item(i).getNodeType() == Node.ELEMENT_NODE) {
        line = ((Element)e.getElementsByTagName("line").item(i)).getAttribute("line");
      }
     }
     return new Role(name, rank, line, 2);
   }

   public void getNeighbors(Set s, Element e) {
    List<Element> neighborElements = new ArrayList<>();
     for (int i = 0; i < e.getElementsByTagName("neighbor").getLength();i++) {
        Node n = e.getElementsByTagName("neighbor").item(i);
        if (n.getNodeType() == Node.ELEMENT_NODE) {
          s.addNeighbor(stringMap.get(((Element)n).getAttribute("name")));
        }
     }
   }

   public Set[] parse(String fileName, Board b) {
      try {
          stringMap = new HashMap<String,Set>();
          elist = new ArrayList<>();
          File file = new File(fileName);
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(file);

          NodeList setList = doc.getElementsByTagName("set");

          HashMap<String,Set> nameMap = new HashMap<String,Set>();
          List<Set> allsets = new ArrayList<>();
          //This for loop will get al data besides neighbors and create the sets(2 special sets not included)===========================//
          for (int i = 0; i < setList.getLength(); i++) {
              Node setElement = setList.item(i);
              if (setElement.getNodeType()==Node.ELEMENT_NODE) {
                elist.add((Element)setElement);
                allsets.add(parseSet((Element)setElement,b));
              }
          }
          //do special cases =====================================
          NodeList tList = doc.getElementsByTagName("trailer");
          Element trailer = null;
          for (int i = 0; i < tList.getLength();i++) {
            Node e = tList.item(i);
            if(e.getNodeType() == Node.ELEMENT_NODE) {
              trailer = (Element) e;
            }
          }
          Set trailerSet = parseSet(trailer, b);
          //=======        +                     =====  //
          NodeList oList = doc.getElementsByTagName("office");
          Element office = null;
          for (int i = 0; i < oList.getLength();i++) {
            Node e = oList.item(i);
            if(e.getNodeType() == Node.ELEMENT_NODE) {
              office = (Element) e;
            }
          }
          Set officeSet = parseSet(office, b);
          //=====================
          for (int i = 0 ; i < allsets.size();i++) {
             getNeighbors(allsets.get(i), elist.get(i));
          }
          getNeighbors(officeSet, office);
          getNeighbors(trailerSet, trailer);
          allsets.add(officeSet);
          allsets.add(trailerSet);
          return allsets.toArray(new Set[0]);


      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }



}//class