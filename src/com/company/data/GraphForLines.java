package com.company.data;

import com.company.dataElements.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphForLines {
    private static final long serialVersionUID = 1L;

    private List<Line> lines;

    public GraphForLines() {
        this.lines = new ArrayList<Line>();
    }

    public void addLine(Line line){
        lines.add(line);
    }

    public void drawLine(Graphics g){
        for(Line line: lines){
            line.draw(g);
        }
    }


}
