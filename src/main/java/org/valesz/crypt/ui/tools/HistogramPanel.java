package org.valesz.crypt.ui.tools;

import javax.swing.*;
import java.awt.*;

/**
 * A simple panel which will display a histogram of double values.
 *
 * Created by Zdenek Vales on 27.4.2017.
 */
public class HistogramPanel extends JPanel {

    private double[] data;
    private int[] normalizedData;
    private int[] xVals;

    public HistogramPanel(double[] data, int[] xVals) {
        this.data = data;
        this.xVals = xVals;
    }

    private void normalizeData() {
        normalizedData = new int[data.length];
        int height = getHeight();
        double maxHeight = 0.7;
        double maxData = Double.MIN_VALUE;
        for(double d : data) {
            if (maxData < d) {
                maxData = d;
            }
        }
        double magicHeightConstant = height*maxHeight/maxData;
        for(int i = 0; i < data.length; i++) {
            normalizedData[i] = (int)(data[i]*magicHeightConstant);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        addHistogram((Graphics2D) g);
    }

    private void addHistogram(Graphics2D graphics2D) {
        // histogram will consist of 2*data.length+1 columns of same length
        // odd columns will be black data columns
        normalizeData();

        graphics2D.setColor(Color.white);
        graphics2D.fillRect(0,0,getWidth(),getHeight());

        int columnCount = 2*data.length+1;
        double columnWidth = getWidth() / ((double) columnCount);


        int valCntr = 0;
        for(int i = 0; i < data.length; i++) {

            int colHeight = normalizedData[valCntr];
            double value = data[valCntr];
            int xData = xVals[valCntr];
            fillRectangle((int)columnWidth*(2*i+1),300-colHeight,(int)columnWidth,colHeight , graphics2D, value, xData);
            valCntr++;
        }
    }



    private void fillRectangle(int x1, int y1, int width, int height, Graphics2D g2, double val, int xData) {
        g2.setColor(Color.black);
        g2.fillRect(x1,y1,width, height);
        if(val != -1) {
            // add value
            g2.setColor(Color.BLACK);
            g2.drawString(String.format("%.4f",val),x1,y1-10);
        }

        if(xData != -1) {
            // add x value
            g2.setColor(Color.WHITE);
            g2.drawString(Integer.toString(xData),x1+width/4,y1+height-30);
        }
    }
}
