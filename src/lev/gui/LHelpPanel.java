/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lev.gui;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import lev.gui.resources.LImages;

/**
 *
 * @author Justin Swanson
 */
public class LHelpPanel extends LPanel {

    LLabel setting;
    LTextPane help;
    Image arrow;
    int arrowx;
    int y;
    LPanel bottomArea;
    Boolean textVisible = false;
    Boolean hideArrow = false;
    static int spacing = 30;

    public LHelpPanel(Rectangle bounds, Font titleFont, Color titleC, Color contentC, boolean leftArrow, int arrowX) {
	y = - 100;
	arrowx = arrowX;
	arrow = (new ImageIcon(LImages.arrow(leftArrow))).getImage();

	setting = new LLabel("  Setting Name", titleFont, titleC);
	setBounds(bounds);

	help = new LTextPane(new Dimension(getWidth() - 35, getHeight()), contentC);
	help.setVisible(true);
	help.addScroll();
	help.setOpaque(true);
	add(setting);
	add(help);
	setting.setVisible(textVisible);
	help.setVisible(textVisible);

	bottomArea = new LPanel();
	bottomArea.setSize((int) bounds.getWidth(), 190);
	bottomArea.setLocation(0, (int) bounds.getHeight() - bottomArea.getHeight());
	bottomArea.setVisible(false);
	add(bottomArea);

	setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
	if (textVisible && !hideArrow) {
	    g.drawImage(arrow, arrowx, y - arrow.getHeight(null) / 2, null);
	}
    }

    public void setSetting(String title_) {
	setting.setText("   " + title_);
	setting.setSize(setting.getPreferredSize());
    }

    public void setSetting(LLabel l) {
	setSetting(l.getText());
    }

    public void setSettingPos(int y_) {
	y = y_;
	setting.setLocation(17, y - setting.getHeight() / 2);
	help.setLocation(35, y + setting.getHeight() / 2);
	evalPositioning();

    }

    public void setContent(String text) {
	hideArrow = false;
	help.setText(text);
	evalPositioning();
    }

    private void evalPositioning() {
	int min = getLimit() - setting.getHeight() - spacing;
	if (min > help.getPreferredSize().height){
	    min = help.getPreferredSize().height;
	}
	
	help.setSize(help.getWidth(), min);
//	int helpReach = help.getY() + help.getHeight() + spacing;
//	if (helpReach > getLimit()) {
//	    int move = help.getY() + help.getHeight() + spacing - getLimit();
//	    help.setLocation(help.getX(), help.getY() - move);
//	    setting.setLocation(setting.getX(), setting.getY() - move);
//	}
    }

    private int getLimit() {
	if (bottomArea != null && bottomArea.isVisible()) {
	    return getHeight() - bottomArea.getHeight();
	} else {
	    return getHeight();
	}
    }

    public void addContent(String text) {
	help.append(text);
	evalPositioning();
    }

    public void clearBottomArea() {
	bottomArea.removeAll();
	bottomArea.setVisible(false);
    }

    public void addToBottomArea(Component c) {
	bottomArea.Add(c);
	bottomArea.setVisible(true);
    }

    public void setBottomAreaHeight(int y) {
	int limit = bottomArea.getY() + bottomArea.getHeight();
	bottomArea.setSize(bottomArea.getWidth(), y);
	bottomArea.setLocation(0, limit - bottomArea.getHeight());
    }

    public void setBottomAreaVisible(boolean on) {
	bottomArea.setVisible(on);
    }

    public void textVisible(Boolean b) {
	textVisible = b;
	setting.setVisible(b);
	help.setVisible(b);
	repaint();
    }

    public void hideArrow() {
	hideArrow = true;
    }

    public Dimension getBottomSize() {
	return bottomArea.getSize();
    }
}
