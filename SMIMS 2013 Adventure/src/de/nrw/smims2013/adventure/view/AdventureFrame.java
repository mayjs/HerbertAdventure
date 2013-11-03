package de.nrw.smims2013.adventure.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.nrw.smims2013.adventure.model.Inventory;
import de.nrw.smims2013.adventure.model.Item;
import de.nrw.smims2013.adventure.model.Player;

public class AdventureFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private InventoryPanel inventoryPanel;
	private MenuPanel menuPanel;
	private PausePanel pausePanel;
	private ScenePanel scenePanel;
	private OptionPanel optionPanel;
	private DialogPanel dialogPanel;
	private DescriptionPanel descriptionPanel;
	private WelcomePanel welcomePanel;
	private GoodbyePanel goodbyePanel;
	
	

	public GoodbyePanel getGoodbyePanel() {
		return goodbyePanel;
	}

	public AdventureFrame(String pTitle, Player pPlayer) {
		super(pTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        setLayout(null);
        setSize(1006,728);
        Dimension dim = new Dimension(1006,728);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setLocation(10,10);       
        setResizable(false);
        setIconImage(ImageLoader.getImage("Zigarette"));
        setBackground(Color.RED);
        // Die vier Panels (ScenePanel, InventoryPanel, MenuPanel und PausePanel) werden erstellt.
        welcomePanel=new WelcomePanel(this);
        goodbyePanel=new GoodbyePanel(this);
        add(welcomePanel);
        add(goodbyePanel);
        pausePanel = new PausePanel(this);
        add(pausePanel);    
        descriptionPanel = new DescriptionPanel(this);
        add(descriptionPanel);
        optionPanel = new OptionPanel(this);
        add(optionPanel);
        dialogPanel = new DialogPanel(this);
        add(dialogPanel);
        scenePanel = new ScenePanel(this);
        add(scenePanel);       
        inventoryPanel = new InventoryPanel(this);
        add(inventoryPanel);
        menuPanel = new MenuPanel(this);
        add(menuPanel);      
	}
	
	public Inventory getInventory() {
		return getPlayer().getInventory();
	}
	
	public WelcomePanel getWelcomePanel() {
		return welcomePanel;
	}
	
	public InventoryPanel getInventoryPanel() {
		return inventoryPanel;
	}

	public DescriptionPanel getDescriptionPanel()
	{
		return descriptionPanel;
	}
	
	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public PausePanel getPausePanel() {
		return pausePanel;
	}

	public OptionPanel getOptionPanel() {
		return optionPanel;
	}
	
	public DialogPanel getDialogPanel(){
		return dialogPanel;
	}
	
	public Player getPlayer() {
		return scenePanel.getPlayer();	
	}

	public ScenePanel getScenePanel() {
		return scenePanel;
	}
	
	/**
	 * Herbert stops walking when this method is called.
	 */
	public void stopWalking() {
		scenePanel.stopWalking();
	}
	
	/**
	 * Notifies the user about whatever you have to say.
	 * @param txt
	 */
	public void notification(String txt) {
		this.getDescriptionPanel().notification(txt);
	}
	
	/**
	 * Repaint the whole window (attention, may take some time when called in a loop!)
	 */
	@Override
	public void repaint(){
		super.repaint();
		scenePanel.paintImmediately(0, 0, scenePanel.getWidth(), scenePanel.getHeight());
		inventoryPanel.reassignIcons();
		inventoryPanel.repaint();
	}
	
//	private Cursor cursor;
	public void setCursorFromItem(Item i){
//		System.out.println("Starting to create the custom cursor");
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		System.out.println("got toolkit");
//		Image img = ImageLoader.getImageIcon("cursor").getImage();
//		System.out.println("got image");
//		cursor = toolkit.createCustomCursor(img, new java.awt.Point(5,5), "ItemCursor");
//		System.out.println("created cursor");
//		setCursor(cursor);
//		System.out.println("Finished");
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
