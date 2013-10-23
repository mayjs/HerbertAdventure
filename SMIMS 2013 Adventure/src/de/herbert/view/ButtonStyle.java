package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

public class ButtonStyle {
	public final static ButtonStyle BS_Default = 	new ButtonStyle(new Color(30,30,30,90), new Color(0, 0, 0, 100),
																	new Color(30,30,30,90),  new Color(0, 0, 0, 150),
																	new Color(30,30,30,90), new Color(100, 50, 0, 400),
																	new Color(30,30,30,90), Color.lightGray.darker());
	
	public final static ButtonStyle BS_Inventory = new ButtonStyle(new Color(30,30,30,90), Color.lightGray,
																	new Color(30,30,30,90),  Color.lightGray.darker(),
																	new Color(30,30,30,90), new Color(100, 50, 0, 400),
																	new Color(30,30,30,90), new Color(0, 0, 0, 150));
	
	Color defaultColor1;
	Color defaultColor2;
	Color mouseOverColor1;
	Color mouseOverColor2;
	Color mousePressedColor1;
	Color mousePressedColor2;
	Color disabledColor1;
	Color disabledColor2;
	
	public ButtonStyle(Color defaultColor1, Color defaultColor2,
			Color mouseOverColor1, Color mouseOverColor2,
			Color mousePressedColor1, Color mousePressedColor2,
			Color disabledColor1, Color disabledColor2) {
		super();
		this.defaultColor1 = defaultColor1;
		this.defaultColor2 = defaultColor2;
		this.mouseOverColor1 = mouseOverColor1;
		this.mouseOverColor2 = mouseOverColor2;
		this.mousePressedColor1 = mousePressedColor1;
		this.mousePressedColor2 = mousePressedColor2;
		this.disabledColor1 = disabledColor1;
		this.disabledColor2 = disabledColor2;
	}
	
	public GradientFill getDefaultFill(Rectangle boundings){
		return new GradientFill(boundings.getX(), boundings.getY(), defaultColor1, boundings.getX(), boundings.getY() + boundings.getHeight() / 2, defaultColor2);
	}
	
	public GradientFill getMouseOverFill(Rectangle boundings){
		return new GradientFill(boundings.getX(), boundings.getY(), mouseOverColor1, boundings.getX(), boundings.getY() + boundings.getHeight() / 2, mouseOverColor2);
	}
	
	public GradientFill getMousePressedFill(Rectangle boundings){
		return new GradientFill(boundings.getX(), boundings.getY(), mousePressedColor1, boundings.getX(), boundings.getY() + boundings.getHeight() / 2, mousePressedColor2);
	}
	
	public GradientFill getDisabledFill(Rectangle boundings){
		return new GradientFill(boundings.getX(), boundings.getY(), disabledColor1, boundings.getX(), boundings.getY() + boundings.getHeight() / 2, disabledColor2);
	}
	
	public Color getDefaultColor1() {
		return defaultColor1;
	}
	public void setDefaultColor1(Color defaultColor1) {
		this.defaultColor1 = defaultColor1;
	}
	public Color getDefaultColor2() {
		return defaultColor2;
	}
	public void setDefaultColor2(Color defaultColor2) {
		this.defaultColor2 = defaultColor2;
	}
	public Color getMouseOverColor1() {
		return mouseOverColor1;
	}
	public void setMouseOverColor1(Color mouseOverColor1) {
		this.mouseOverColor1 = mouseOverColor1;
	}
	public Color getMouseOverColor2() {
		return mouseOverColor2;
	}
	public void setMouseOverColor2(Color mouseOverColor2) {
		this.mouseOverColor2 = mouseOverColor2;
	}
	public Color getMousePressedColor1() {
		return mousePressedColor1;
	}
	public void setMousePressedColor1(Color mousePressedColor1) {
		this.mousePressedColor1 = mousePressedColor1;
	}
	public Color getMousePressedColor2() {
		return mousePressedColor2;
	}
	public void setMousePressedColor2(Color mousePressedColor2) {
		this.mousePressedColor2 = mousePressedColor2;
	}
	public Color getDisabledColor1() {
		return disabledColor1;
	}
	public void setDisabledColor1(Color disabledColor1) {
		this.disabledColor1 = disabledColor1;
	}
	public Color getDisabledColor2() {
		return disabledColor2;
	}
	public void setDisabledColor2(Color disabledColor2) {
		this.disabledColor2 = disabledColor2;
	}
	
	
}
