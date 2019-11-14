package dev.moltencube.mygame.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Text {
	
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, boolean alignRight, Color c, Font font) {
		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		if(center) {
			FontMetrics fm = g.getFontMetrics();
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
		}else if(alignRight) {
			FontMetrics fm = g.getFontMetrics();
			x = xPos - fm.stringWidth(text);
		}
		g.drawString(text, x, y);
	}
	
	public static void drawWrappedString(Graphics g, String text, Rectangle rect, Color c, Font font) {
		g.setColor(c);
		g.setFont(font);
		
		StringTokenizer st =  new  StringTokenizer(text);
	    ArrayList<String> list = new ArrayList<String>();
	    FontMetrics fm = g.getFontMetrics();
	    
	    String line = "";
	    String lineBeforeAppend = "";
	    while (st.hasMoreTokens()){
	       String seg = st.nextToken();
	       lineBeforeAppend = line;
	       line += seg + " ";
	       int width = fm.stringWidth(line);
	       if(width  < rect.width){
	           continue;
	       }else {
	           list.add(lineBeforeAppend);
	           line += seg + " ";
	       }
	    }
	    
	    for(int i = 0; i < list.size(); i++) {
	    	g.drawString(list.get(i), rect.x, rect.y);
	    }
	}
}
