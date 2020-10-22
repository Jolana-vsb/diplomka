package cz.vsb.application.selects;

import java.util.ArrayList;

public class SelectWithPaths {

    private String query;
    private ArrayList<String> paths;

    public SelectWithPaths(String query, ArrayList<String> paths) {
        this.query = query;
        this.paths = paths;
    }

    public String getQuery() {
        return query;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }
}
