package com.sax.views.components.libraries;/*
 * $Id: GradientPanel.java,v 1.1 2005/05/25 23:13:24 rbair Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class GradientPanel extends JPanel {
    private int value;

    public GradientPanel(int value) {
        this.value = value;
    }

    public GradientPanel() {
    }

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Color gradientStart = new Color(182, 219, 136);
        Color gradientEnd = new Color(158, 211, 102);

        Graphics2D g2 = (Graphics2D) g;
        g2.setClip(new RoundRectangle2D.Double(0, 0, width, height, value, value));
        GradientPaint painter = new GradientPaint(0, 0, gradientStart,
                0, height, gradientEnd);
        Paint oldPainter = g2.getPaint();
        g2.setPaint(painter);
        g2.fill(g2.getClip());

        gradientStart = new Color(32, 39, 64);
        gradientEnd = new Color(32, 39, 64);

        painter = new GradientPaint(0, 0, gradientEnd,
                0, height / 2, gradientStart);
        g2.setPaint(painter);
        g2.fill(g2.getClip());

        painter = new GradientPaint(0, height / 2, gradientStart,
                0, height, gradientEnd);
        g2.setPaint(painter);
        g2.fill(g2.getClip());

        g2.setPaint(oldPainter);
    }
}
