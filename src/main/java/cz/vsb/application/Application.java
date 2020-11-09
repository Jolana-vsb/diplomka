package cz.vsb.application;

import cz.vsb.application.files.InputFileReader;
import cz.vsb.application.files.PathFileWriter;
import cz.vsb.application.processors.*;
import cz.vsb.application.selects.SelectComparator;
import cz.vsb.application.selects.SelectWithSimilarity;
import cz.vsb.database.Database;
import cz.vsb.database.Path;
import cz.vsb.database.PathDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Stream;

public class Application {

    private static ArrayList<String> inputPathsNums = new ArrayList<>();

    public static void runApplication(){
        Database.setConnectionString();
        //writePathsTofile();
        calculateSimilarity();
        //clearPathTable();
    }

    private static void writePathsTofile(){
        PathsMap pathsMap = new PathsMap();
        PathFileWriter.startWriting();

        Stream<String> lines = InputFileReader.readFile("data/MySQL.xml");
        lines.forEach(e ->{
            PathGenerator.generate(e, pathsMap);
        });

        PathFileWriter.stopWriting();
        PathDAO.insert(pathsMap.pathsWithNums);
    }

    private static void calculateSimilarity(){
        InputPreparator.prepareInput("SELECT * FROM products WHERE (price BETWEEN 1.0 AND 2.0) AND (quantity BETWEEN 1000 AND 2000)", 0);

        Vector<SelectWithSimilarity> resultList = new Vector<>();

        Stream<String> lines = InputFileReader.readFile("data/outputPaths2.txt");
        lines.forEach(e ->{
            SelectWithSimilarity selectWithSimilarity = new SimilarityCalculator(e, InputPreparator.getInputPaths()).calculate();
            resultList.add(selectWithSimilarity);
        });

        //System.out.println("res " + resultList.size());

        Collections.sort(resultList, new SelectComparator());

        for(int i = 0; i < 10; i++){
            System.out.println(resultList.get(i).getQuery());
            System.out.println(resultList.get(i).getSimilarity());
        }
    }

    private static void clearPathTable(){
        PathDAO.clearTable();
    }
}
