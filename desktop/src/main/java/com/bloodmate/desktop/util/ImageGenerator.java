package com.bloodmate.desktop.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {
    
    public static void main(String[] args) {
        // Create blood drop images of different sizes
        createBloodDropImage(45, 50, "blood_drop_small.png");
        createBloodDropImage(80, 90, "blood_drop_medium.png");
        createBloodDropImage(100, 110, "blood_drop_large.png");
        
        System.out.println("Blood drop images generated successfully!");
    }
    
    private static void createBloodDropImage(int width, int height, String filename) {
        // Create a buffered image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Enable anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw blood drop shape
        drawBloodDrop(g2d, width, height);
        
        // Dispose of graphics context
        g2d.dispose();
        
        // Save the image
        try {
            File outputFile = new File("c:\\Users\\Lalit Punjabi\\BloodMate-Advanced\\desktop\\src\\main\\resources\\images\\" + filename);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Created: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void drawBloodDrop(Graphics2D g2d, int width, int height) {
        // Draw the main blood drop shape with gradient-like effect
        
        // Top circle (darker red)
        g2d.setColor(new Color(204, 0, 0)); // Dark red
        g2d.fillOval((int)(width * 0.3), (int)(height * 0.1), (int)(width * 0.4), (int)(height * 0.4));
        
        // Main body (brighter red)
        g2d.setColor(new Color(220, 38, 38)); // Primary red
        int[] xPoints = {
            (int)(width * 0.3),    // Left top
            (int)(width * 0.7),    // Right top
            (int)(width * 0.5)     // Bottom point
        };
        
        int[] yPoints = {
            (int)(height * 0.3),   // Left top
            (int)(height * 0.3),   // Right top
            (int)(height * 0.9)    // Bottom point
        };
        
        g2d.fillPolygon(xPoints, yPoints, 3);
        
        // Add highlight for 3D effect
        g2d.setColor(new Color(255, 100, 100, 180)); // Semi-transparent light red
        g2d.fillOval((int)(width * 0.4), (int)(height * 0.15), (int)(width * 0.15), (int)(height * 0.15));
        
        // Add subtle outline
        g2d.setColor(new Color(153, 0, 0)); // Darkest red for outline
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval((int)(width * 0.3), (int)(height * 0.1), (int)(width * 0.4), (int)(height * 0.4));
        g2d.drawPolygon(xPoints, yPoints, 3);
    }
}