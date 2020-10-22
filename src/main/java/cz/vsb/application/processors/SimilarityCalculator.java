package cz.vsb.application.processors;

import cz.vsb.application.selects.SelectWithPaths;
import cz.vsb.application.selects.SelectWithSimilarity;
import java.util.ArrayList;
import java.util.HashSet;

public class SimilarityCalculator{

    private SelectWithPaths selectWithPaths;
    private ArrayList<String> inputPaths;
    private HashSet<String> inputHash = new HashSet<>();
    private HashSet<String> fileHash = new HashSet<>();
    private HashSet<String> totalHash = new HashSet<>();

    public SimilarityCalculator(SelectWithPaths selectWithPaths, ArrayList<String> input){
        this.inputPaths = input;
        this.selectWithPaths = selectWithPaths;
    }

    public SelectWithSimilarity calculate() {

        prepareHashSets();

        int intersection = 0;

        for(String s : inputHash){
            if(fileHash.contains(s))
                intersection++;
        }

        SelectWithSimilarity selectWithSimilarity = new SelectWithSimilarity(selectWithPaths.getQuery(), ((double)intersection / (double) totalHash.size()) * 100);

        totalHash = null;
        inputHash = null;
        fileHash = null;
        return selectWithSimilarity;
    }

    private void prepareHashSets(){
        for(String s : inputPaths){
            int i = 0;
            while(inputHash.contains(s+i)){
                i++;
            }
            inputHash.add(s+i);
            totalHash.add(s+i);
        }

        for(String s : selectWithPaths.getPaths()){
            int i = 0;
            while(fileHash.contains(s+i)){
                i++;
            }
            fileHash.add(s+i);
            totalHash.add(s+i);
        }
    }
}
