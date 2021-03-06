package jui;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.ArrayList;

//code refactored Jan 18,the Displayable interface is changed into a superclass. Modified by Jiachen Ren
//add setBackground. Task completed. Background enum added April 22nd.
//modified April 22nd. Took me half an hour, I eliminated all rounding errors for containers!
//primitive type for coordinate and dimension is changed from int to float. Proved to be helpful!
//refresh requesting technique applied April 23rd

/**
 * add mousePressedTextColor(), mousePressedContourColor(), mouseOverTextColor(), mouseOverContourColor();
 * completed April 30th.
 */
public class Displayable implements MouseControl {
    public boolean displayContour = JNode.DISPLAY_CONTOUR;
    public boolean isVisible = true;

    public int colorMode = JNode.COLOR_MODE;
    public int backgroundColor = JNode.BACKGROUND_COLOR;
    public int mouseOverBackgroundColor = JNode.MOUSE_OVER_BACKGROUND_COLOR;
    public int mousePressedBackgroundColor = JNode.MOUSE_PRESSED_BACKGROUND_COLOR;
    public int contourColor = JNode.CONTOUR_COLOR;
    public int mousePressedContourColor = JNode.MOUSE_PRESSED_CONTOUR_COLOR;
    public int mouseOverContourColor = JNode.MOUSE_OVER_CONTOUR_COLOR;

    public float contourThickness = JNode.CONTOUR_THICKNESS;
    public float rounding = JNode.ROUNDING;

    public float x, y, w, h;
    public float relativeW = 1, relativeH = 1;

    public JStyle backgroundStyle = JStyle.CONSTANT;
    public JStyle contourStyle = JStyle.CONSTANT;
    public ImgStyle imgStyle = ImgStyle.RESERVED;

    public PImage backgroundImg;
    private Runnable attachedMethod;

    private boolean refreshRequested;

    public boolean isRounded = JNode.ROUNDED;
    public boolean isRelative, isUndeclared;

    public String id;

    private ArrayList<EventListener> eventListeners;

    private boolean mouseIsInScope;

    {
        eventListeners = new ArrayList<>();
    }

    public enum ImgStyle {
        RESERVED, STRETCH
    }

    public Displayable(String id, float relativeW, float relativeH) {
        this.id = id;
        setRelativeW(relativeW);
        setRelativeH(relativeH);
        this.isRelative = true;
    }

    public Displayable(String id) {
        this.id = id;
        this.isRelative = true;
        this.isUndeclared = true;
    }

    public Displayable(String id, float x, float y, float w, float h) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean isMouseOver() {
        return JNode.getParent().mouseX >= x && (JNode.getParent().mouseX <= x + w && (JNode.getParent().mouseY >= y && (JNode.getParent().mouseY <= y + h)));
    }

    public boolean isUndeclared() {
        return isUndeclared;
    }

    public Displayable setUndeclared(boolean temp) {
        isUndeclared = temp;
        return this;
    }

    public Displayable setRounded(boolean temp) {
        isRounded = temp;
        return this;
    }

    public Displayable setRounding(float temp) {
        rounding = temp;
        //setRounded(true); removed April 26th.
        return this;
    }

    public boolean isRelative() {
        return isRelative;
    }

    public float getRelativeW() {
        return relativeW;
    }

    public float getRelativeH() {
        return relativeH;
    }

    public Displayable setRelative(boolean temp) {
        isRelative = temp;
        refreshRequested = true;
        return this;
    }

    public Displayable setRelativeW(float temp) {
        relativeW = temp;
        isUndeclared = false;
        refreshRequested = true;/*this might take long. Consider optimization.*/
        return this;
    }

    public Displayable setRelativeH(float temp) {
        relativeH = temp;
        isUndeclared = false;
        refreshRequested = true;
        return this;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isDisplayingContour() {
        return displayContour;
    }

    public Displayable setVisible(boolean temp) {
        isVisible = temp;
        return this;
    }

    public Displayable setId(String temp) {
        id = temp;
        return this;
    }

    public String getId() {
        return id;
    }

    public float[] getDimension() {
        return new float[]{w, h};
    }

    public float[] getCoordinate() {
        return new float[]{x, y};
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Displayable setContourColor(int r, int g, int b) {
        contourColor = JNode.getParent().color(r, g, b);
        return this;
    }

    public Displayable setContourColor(int c) {
        contourColor = c;
        return this;
    }

    public Displayable setContourColor(int r, int g, int b, int t) {
        contourColor = JNode.getParent().color(r, g, b, t);
        return this;
    }

    public Displayable setMousePressedContourColor(int r, int g, int b) {
        mousePressedContourColor = JNode.getParent().color(r, g, b);

        return this;
    }

    public Displayable setMousePressedContourColor(int c) {
        mousePressedContourColor = c;
        return this;
    }

    public Displayable setMousePressedContourColor(int r, int g, int b, int t) {
        mousePressedContourColor = JNode.getParent().color(r, g, b, t);
        return this;
    }

    public Displayable setMouseOverContourColor(int r, int g, int b) {
        mouseOverContourColor = JNode.getParent().color(r, g, b);
        return this;
    }

    public Displayable setMouseOverContourColor(int c) {
        mouseOverContourColor = c;
        return this;
    }

    public Displayable setMouseOverContourColor(int r, int g, int b, int t) {
        mouseOverContourColor = JNode.getParent().color(r, g, b, t);
        return this;
    }

    public Displayable setContourThickness(float thickness) {
        contourThickness = thickness;
        return this;
    }

    public Displayable setContourVisible(boolean temp) {
        displayContour = temp;
        return this;
    }

    public Displayable setBackgroundColor(int r, int g, int b) {
        backgroundColor = JNode.getParent().color(r, g, b);
        return this;
    }

    public Displayable setBackgroundColor(int c) {
        backgroundColor = c;
        return this;
    }

    public Displayable setBackgroundColor(int r, int g, int b, int t) {
        backgroundColor = JNode.getParent().color(r, g, b, t);
        return this;
    }

    public Displayable setMouseOverBackgroundColor(int r, int g, int b, int t) {
        mouseOverBackgroundColor = JNode.getParent().color(r, g, b, t);
        return this;
    }

    public Displayable setMouseOverBackgroundColor(int c) {
        mouseOverBackgroundColor = c;
        return this;
    }

    public Displayable setMouseOverBackgroundColor(int r, int g, int b) {
        mouseOverBackgroundColor = JNode.getParent().color(r, g, b);
        return this;
    }

    public Displayable setMousePressedBackgroundColor(int r, int g, int b, int t) {
        mousePressedBackgroundColor = JNode.getParent().color(r, g, b, t);
        return this;
    }

    public Displayable setMousePressedBackgroundColor(int c) {
        mousePressedBackgroundColor = c;
        return this;
    }

    public Displayable setMousePressedBackgroundColor(int r, int g, int b) {
        mousePressedBackgroundColor = JNode.getParent().color(r, g, b);
        return this;
    }

    public Displayable setColorMode(int colorMode) {
        this.colorMode = colorMode;
        return this;
    }

    /*modified March 8th*/
    public Displayable attachMethod(Runnable runnable) {
        attachedMethod = runnable;
        return this;
    }

    public Runnable getAttachedMethod() {
        return attachedMethod;
    }

    public void run() {
        display();
        if (attachedMethod != null) {
            attachedMethod.run();
        }
        updateEventListeners();
    }

    private void updateEventListeners() {
        if (eventListeners.size() == 0) return;
        if (isMouseOver()) {
            eventListeners.forEach(eventListener -> {
                if (eventListener.getEvent().equals(Event.MOUSE_OVER))
                    eventListener.invoke();
            });
            if (!mouseIsInScope) {
                mouseIsInScope = true;
                eventListeners.forEach((eventListener) -> {
                    if (eventListener.getEvent().equals(Event.MOUSE_ENTERED))
                        eventListener.invoke();
                });
            }
        } else {
            if (mouseIsInScope) {
                mouseIsInScope = false;
                eventListeners.forEach((eventListener) -> {
                    if (eventListener.getEvent().equals(Event.MOUSE_LEFT))
                        eventListener.invoke();
                });
            }
        }
    }

    public void mousePressed() {
        eventListeners.forEach(eventListener -> {
            if (eventListener.getEvent().equals(Event.MOUSE_PRESSED))
                eventListener.invoke();
        });
    }

    public void mouseDragged() {

    }

    public void mouseReleased() {
        eventListeners.forEach(eventListener -> {
            if (eventListener.getEvent().equals(Event.MOUSE_RELEASED))
                eventListener.invoke();
        });
    }

    /**
     * TODO not working yet. Debug April 30th.
     */
    public void mouseWheel() {
        eventListeners.forEach(eventListener -> {
            if (eventListener.getEvent().equals(Event.MOUSE_WHEEL))
                eventListener.invoke();
        });
    }

    public void applyContourStyle() {
        getParent().strokeWeight(contourThickness);
        switch (contourStyle) {
            case VOLATILE:
                if (displayContour) {
                    if (isMouseOver()) {
                        getParent().stroke(getParent().mousePressed ? mousePressedContourColor : mouseOverContourColor);
                    } else getParent().stroke(contourColor);
                } else {
                    getParent().noStroke();
                }
                break;
            case CONSTANT:
                if (displayContour) getParent().stroke(contourColor);
                else getParent().noStroke();
                break;
        }
    }

    public void applyBackgroundStyle() {
        switch (backgroundStyle) {
            case CONSTANT:
                getParent().fill(backgroundColor);
                break;
            case VOLATILE:
                if (isMouseOver()) {
                    getParent().fill(getParent().mousePressed ? mousePressedBackgroundColor : mouseOverBackgroundColor);
                } else {
                    getParent().fill(backgroundColor);
                }
                break;
            case DISABLED:
                break;
        }
    }

    public void drawRect() {
        getParent().rectMode(PConstants.CORNER);
        if (isRounded) {
            getParent().rect(x, y, w, h, rounding);
        } else {
            getParent().rect(x, y, w, h);
        }
    }

    public void displayImg() {
        if (backgroundImg != null) {
            getParent().imageMode(PConstants.CENTER);
            float tx = x + w / 2, ty = y + h / 2;
            switch (imgStyle) {
                case RESERVED:
                    float imgWidth = backgroundImg.width;
                    float imgHeight = backgroundImg.height;
                    if (imgWidth > w) {
                        float scale = w / imgWidth;
                        imgWidth = w;
                        imgHeight *= scale;
                    }
                    if (imgHeight > h) {
                        float scale = h / imgHeight;
                        imgHeight = h;
                        imgWidth *= scale;
                    }
                    getParent().image(backgroundImg, tx, ty, imgWidth, imgHeight);
                    break;
                case STRETCH:
                    break;
            }
        }
    }

    public void display() {
        /*default displaying method. Overriding recommended*/
        getParent().pushStyle();

        applyContourStyle();
        applyBackgroundStyle();
        drawRect();

        displayImg();

        getParent().popStyle();
    }

    public void relocate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void resize(float w, float h) {
        this.w = w;
        this.h = h;
    }

    boolean refreshRequested() {
        return refreshRequested;
    }

    void requestProcessed() {
        refreshRequested = false;
    }

    public Displayable setBackgroundStyle(JStyle backgroundStyle) {
        this.backgroundStyle = backgroundStyle;
        return this;
    }

    public Displayable setContourStyle(JStyle contourStyle) {
        this.contourStyle = contourStyle;
        return this;
    }

    public Displayable setWidth(float temp) {
        resize(temp, h);
        return this;
    }

    public Displayable setHeight(float temp) {
        resize(w, temp);
        return this;
    }

    public Displayable setX(float temp) {
        relocate(temp, y);
        return this;
    }

    public Displayable setY(float temp) {
        relocate(x, temp);
        return this;
    }

    public PApplet getParent() {
        return JNode.getParent();
    }

    public PImage getBackgroundImg() {
        return backgroundImg;
    }

    public Displayable setBackgroundImg(PImage backgroundImg) {
        this.backgroundImg = backgroundImg;
        return this;
    }

    public ArrayList<EventListener> getEventListeners() {
        return eventListeners;
    }

    public ArrayList<EventListener> getEventListeners(Event event) {
        ArrayList<EventListener> matched = new ArrayList<>();
        eventListeners.forEach(eventListener -> {
            if (eventListener.getEvent().equals(event))
                matched.add(eventListener);
        });
        return matched;
    }

    public Displayable addEventListener(String id, Event event, Runnable attachedMethod) {
        EventListener eventListener = new EventListener(id, event);
        eventListener.attachMethod(attachedMethod);
        this.eventListeners.add(eventListener);
        return this;
    }

    public EventListener getEventListener(String id) {
        for (EventListener eventListener : eventListeners)
            if (eventListener.getId().equals(id))
                return eventListener;
        return null;
    }

    public Displayable removeEventListener(String id) {
        for (int i = eventListeners.size() - 1; i >= 0; i--) {
            if (eventListeners.get(i).getId().equals(id))
                eventListeners.remove(i);
        }
        return this;
    }

    public Displayable removeEventListeners(Event event) {
        for (int i = eventListeners.size() - 1; i >= 0; i--) {
            if (eventListeners.get(i).getEvent().equals(event))
                eventListeners.remove(i);
        }
        return this;
    }

    public Displayable addEventListener(Event event, Runnable attachedMethod) {
        addEventListener("", event, attachedMethod);
        return this;
    }
}