import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;


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

   private Set parseSet(Element e, Board b, Controller c) {
     String name = e.getAttribute("name");
     int shots = e.getElementsByTagName("take").getLength();

    //Parse coords for set area
     Element areaElement = (Element) e.getElementsByTagName("area").item(0);
     int x = Integer.parseInt(areaElement.getAttribute("x"));
     int y = Integer.parseInt(areaElement.getAttribute("y"));
     int w = Integer.parseInt(areaElement.getAttribute("w"));
     int h = Integer.parseInt(areaElement.getAttribute("h"));
     int[] area = {x, y, w, h};

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
     ShotCounter[] tshots = getShots((Element)e.getElementsByTagName("takes").item(0));
     Set s = new Set(b,roleList.toArray(new Role[0]),shots,new Set[neighbors],name,c, area,tshots);
     stringMap.put(name, s);
     return s;
   }

   private Role parseRole(Element e) {
     String name = e.getAttribute("name");
     int rank = Integer.parseInt(e.getAttribute("level"));
     String line = e.getElementsByTagName("line").item(0).getTextContent();
     Element a = (Element) e.getElementsByTagName("area").item(0);
     int x = Integer.parseInt(a.getAttribute("x"));
     int y = Integer.parseInt(a.getAttribute("y"));
     return new Role(name, rank, line, 2, x, y);
   }

   //Expects a Element parameter that is the "takes" field
   private ShotCounter[] getShots(Element e) {
      List<Element> aList = new ArrayList<>();
      NodeList n = e.getElementsByTagName("area");
      for (int i = 0; i < n.getLength();i++) {
        if (n.item(i).getNodeType() == Node.ELEMENT_NODE) {
          aList.add((Element)n.item(i));
        }
      }
      ShotCounter[] tempShots = new ShotCounter[aList.size()];
      for (int i = 0; i < tempShots.length;i++) {
        int x = Integer.parseInt(aList.get(i).getAttribute("x"));
        int y = Integer.parseInt(aList.get(i).getAttribute("y"));
        tempShots[i] = new ShotCounter(x, y);
      }
      return tempShots;
   }

   public void getNeighbors(Set s, Element e) {
     for (int i = 0; i < e.getElementsByTagName("neighbor").getLength();i++) {
        Node n = e.getElementsByTagName("neighbor").item(i);
        if (n.getNodeType() == Node.ELEMENT_NODE) {
          s.addNeighbor(stringMap.get(((Element)n).getAttribute("name")));
        }
     }
   }

   public Set[] parse(String fileName, Board b, Controller c) {
      try {
          stringMap = new HashMap<String,Set>();
          elist = new ArrayList<>();
          File file = new File(fileName);
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(file);

          NodeList setList = doc.getElementsByTagName("set");

          List<Set> allsets = new ArrayList<>();
          //This for loop will get al data besides neighbors and create the sets(2 special sets not included)===========================//
          for (int i = 0; i < setList.getLength(); i++) {
              Node setElement = setList.item(i);
              if (setElement.getNodeType()==Node.ELEMENT_NODE) {
                elist.add((Element)setElement);
                allsets.add(parseSet((Element)setElement,b,c));
              }
          }
          //do special cases =====================================

          Element trailer = null;
          trailer = (Element) doc.getElementsByTagName("trailer").item(0);
          int neighbors = 0;
          for (int i = 0 ; i < trailer.getElementsByTagName("neighbor").getLength();i++) {
            if (trailer.getElementsByTagName("neighbor").item(i).getNodeType() == Node.ELEMENT_NODE) {
             neighbors++;
            }
          }

          // Parse coordinates for trailer
          Element areaElement = (Element) trailer.getElementsByTagName("area").item(0);
          int x = Integer.parseInt(areaElement.getAttribute("x"));
          int y = Integer.parseInt(areaElement.getAttribute("y"));
          int w = Integer.parseInt(areaElement.getAttribute("w"));
          int h = Integer.parseInt(areaElement.getAttribute("h"));
          int[] area = {x, y, w, h};
          Set trailerSet = new Set(b, null, 0, new Set[neighbors], "trailer",c, area,null);
          stringMap.put("trailer", trailerSet);
          //=======        +                     =====  //
          NodeList oList = doc.getElementsByTagName("office");
          Element office = (Element) oList.item(0);
          neighbors = 0;
          for (int i = 0 ; i < office.getElementsByTagName("neighbor").getLength();i++) {
            if (office.getElementsByTagName("neighbor").item(i).getNodeType() == Node.ELEMENT_NODE) {
             neighbors++;
            }
          }
          
          // Parse coordinates for office
          areaElement = (Element) office.getElementsByTagName("area").item(0);
          x = Integer.parseInt(areaElement.getAttribute("x"));
          y = Integer.parseInt(areaElement.getAttribute("y"));
          w = Integer.parseInt(areaElement.getAttribute("w"));
          h = Integer.parseInt(areaElement.getAttribute("h"));
          area = new int[]{x, y, w, h};

          Set officeSet = new Set(b, null, 0, new Set[neighbors], "office",c, area,null);
          stringMap.put("office", officeSet);
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