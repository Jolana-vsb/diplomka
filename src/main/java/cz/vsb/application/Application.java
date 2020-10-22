package cz.vsb.application;

import cz.vsb.application.files.InputFileReader;
import cz.vsb.application.processors.InputPreparator;
import cz.vsb.application.processors.PathGenerator;
import cz.vsb.application.processors.SimilarityCalculator;
import cz.vsb.application.selects.SelectComparator;
import cz.vsb.application.selects.SelectWithPaths;
import cz.vsb.application.selects.SelectWithSimilarity;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

public class Application {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public static void runApplication(){
        InputPreparator.prepareInput("SELECT p.ProductID, (\n" +
                "\t\tSELECT SUM(LineTotal) \n" +
                "\t\tFROM Sales.SalesOrderDetail AS sod \n" +
                "\t\tWHERE p.ProductID = sod.ProductID \n" +
                "\t) AS SubTotal \n" +
                "\tFROM Production.Product AS p \n" +
                "\tWHERE p.Color = ’Black’ \n" +
                "\tGROUP BY p.ProductID;", 0);

        Vector<SelectWithSimilarity> resultList = new Vector<>();   //do vektoru se muze pristupovat nekolika vlakny naraz, do arraylistu ne (vysledky by se prepsaly)

        Stream<String> lines = InputFileReader.readFile("F:/diplomka/data/data_on_line/MySQL.xml");
        lines.forEach(e ->{
            ArrayList<SelectWithPaths> selectWithTrees = PathGenerator.generate(e);
            for(SelectWithPaths s : selectWithTrees){
                SelectWithSimilarity selectWithSimilarity = new SimilarityCalculator(s, InputPreparator.getInputPaths()).calculate();
                resultList.add(selectWithSimilarity);
            }
        });

        System.out.println("res " + resultList.size());
        Collections.sort(resultList, new SelectComparator());
        for(int i = 0; i < 20; i++){
            System.out.println("Query: " + resultList.get(i).getQuery());
            System.out.println("Similarity: " + Math.round(resultList.get(i).getSimilarity() * 100.0) / 100.0 + " %");
        }
    }
}
