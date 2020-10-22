package cz.vsb.application.processors;

import cz.vsb.application.selects.SelectWithPaths;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class PathGenerator {

    public static ArrayList<SelectWithPaths> generate(String line){
        ArrayList<SelectWithPaths> selectsWithPaths = new ArrayList<>();

        if(line.contains("<sqlSelect>")){
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document document = dBuilder.parse(new InputSource(new StringReader(line)));
                NodeList nodeList = document.getElementsByTagName("sqlStatement");
                String selectCode = document.getElementsByTagName("selectCode").item(0).getFirstChild().getNodeValue();

                for(int i = 0; i < nodeList.getLength(); i++){
                    ArrayList<String> pathsInTree = new ArrayList<>();
                    XmlTreeView.getLeafPaths((Element)nodeList.item(i), new StringBuilder(), pathsInTree);
                    selectsWithPaths.add(new SelectWithPaths(selectCode, pathsInTree));
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return selectsWithPaths;
    }
}
