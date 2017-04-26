package jui_lib;

import processing.core.PConstants;
import processing.core.PFont;

public class MenuItem extends Label implements Controllable {
    private Runnable onClickMethod;
    private boolean mousePressedOnButton;
    private MenuDropdown menu;
    private boolean expandable;

    public MenuItem(String id, String content) {
        super(id);
        super.setContent(content);
        onClickMethod = () -> {
        };
    }

    public MenuItem onClick(Runnable r) {
        onClickMethod = r;
        return this;
    }

    public void mousePressed() {
        if (isMouseOver() && getParent().mouseButton == PConstants.LEFT)
            mousePressedOnButton = true;
        if (menu != null && expandable) {
            menu.mousePressed();
        }
    }

    public void mouseReleased() {
        if (isVisible() && isMouseOver() && mousePressedOnButton) onClickMethod.run();
        mousePressedOnButton = false;
        if (menu != null && expandable) {
            menu.mouseReleased();
        }
    }

    //implement keyboard shortcuts here
    public void keyPressed() {
        if (menu != null && expandable)
            menu.keyPressed();
    }

    public void keyReleased() {
        if (menu != null && expandable)
            menu.keyReleased();
    }

    public void mouseDragged() {
        //deprecated
    }

    public MenuItem bind(MenuDropdown menuDropdown) {
        expandable = true;
        menu = menuDropdown;
        menu.relocate(getX() + getWidth(), getY());
        menu.setVisible(false);

        //modified Jan 25th
    /*
    menu.setDisplayCoutour(this.displayContour);
     menu.setBackgroundColor(this.backgroundColor);
     menu.setContourThickness(this.contourThickness);
     menu.setContourColor(this.contourColor);
     menu.setTextColor(this.textColor);
     menu.setMouseOverBackgroundColor(this.mouseOverBackgroundColor);
     menu.setAlign(this.alignment);
     menu.setTextFont(this.font);
     menu.setTextSize(this.textSize);
     */
        return this;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public boolean hasFocus() {
        return (this.isMouseOver() && isVisible()) || (menu != null && menu.hasFocus());
    }

    //fixed!!!! three hours of work, only because I forgot to add setVisible!!! Jan 28th.
    private void update() {
        if (isMouseOver())
            menu.triggered = true;
        else if (!isMouseOver() && !menu.hasFocus())
            menu.triggered = false;
        if (hasFocus()) {
            menu.relocate(getX() + getWidth(), getY());
            menu.arrange();
            menu.setVisible(true);
            menu.run();//modified Jan 25th
        } else {
            menu.setVisible(false);
        }
    }

    public void display() {
        if (expandable && menu != null) update();
        //drawing the background
        if (font != null) getParent().textFont(font);
        if (displayContour) {
            getParent().strokeWeight(contourThickness);
            getParent().stroke(contourColor);
        } else {
            getParent().noStroke();
        }
        if (expandable) {
            if (menu == null) {
                getParent().fill(hasFocus() ? mouseOverBackgroundColor : backgroundColor);
            } else {
                getParent().fill(hasFocus() && menu.triggered ? mouseOverBackgroundColor : backgroundColor);
            }
        } else {
            getParent().fill(hasFocus() ? mouseOverBackgroundColor : backgroundColor);
        }
        if (isRounded) {
            getParent().rect(x, y, w, h, rounding);
        } else {
            getParent().rect(x, y, w, h);
        }
        super.displayText();
    }

    @Deprecated
    public void adjustTextSize() {
    }

    @Override
    public MenuItem setVisible(boolean temp) {
        if (menu != null) menu.setVisible(temp);
        this.isVisible = temp;
        return this;
    }

    @Override
    public MenuItem setRounded(boolean temp) {
        this.isRounded = temp;
        if (menu != null) menu.setRounded(temp);
        return this;
    }

    @Override
    public MenuItem setContourColor(int c) {
        contourColor = c;
        if (menu != null) menu.setContourColor(contourColor);
        return this;
    }


    @Override
    public MenuItem setContourThickness(float thickness) {
        contourThickness = thickness;
        if (menu != null) menu.setContourThickness(contourThickness);
        return this;
    }

    @Override
    public MenuItem setContourVisible(boolean temp) {
        displayContour = temp;
        if (menu != null) menu.setContourVisible(displayContour);
        return this;
    }


    @Override
    public MenuItem setBackgroundColor(int c) {
        backgroundColor = c;
        if (menu != null) menu.setBackgroundColor(backgroundColor);
        return this;
    }


    @Override
    public MenuItem setMouseOverBackgroundColor(int c) {
        mouseOverBackgroundColor = c;
        if (menu != null)
            menu.setMouseOverBackgroundColor(mouseOverBackgroundColor);
        return this;
    }


    @Override
    public MenuItem setMousePressedBackgroundColor(int c) {
        mousePressedBackgroundColor = c;
        if (menu != null)
            menu.setMousePressedBackgroundColor(mousePressedBackgroundColor);
        return this;
    }

    @Override
    public MenuItem setTextColor(int c) {
        super.setTextColor(c);
        if (menu != null) menu.setTextColor(getTextColor());
        return this;
    }

    @Override
    public MenuItem setTextFont(PFont font) {
        this.font = font;
        if (menu != null) menu.setTextFont(this.font);
        return this;
    }

    @Override
    public MenuItem setTextSize(int textSize) {
        this.textSize = textSize;
        if (menu != null) menu.setTextSize(textSize);
        return this;
    }

    @Override
    public MenuItem setAlign(int align) {
        this.alignment = align;
        if (menu != null) menu.setAlign(alignment);
        return this;
    }
}