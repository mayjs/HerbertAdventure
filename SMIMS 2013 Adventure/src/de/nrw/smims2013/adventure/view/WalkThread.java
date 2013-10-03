package de.nrw.smims2013.adventure.view;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import de.nrw.smims2013.adventure.main.Main;
import de.nrw.smims2013.adventure.model.Player;
import de.nrw.smims2013.adventure.model.Point;
import de.nrw.smims2013.adventure.model.Scene;

public class WalkThread extends Thread {
	private static final long DAUER_PRO_SCHRITT = 100;
	private Point zielPosition = null;
	private Player player = null;
	private ScenePanel scenePanel = null;

	private boolean stop = false;

	public WalkThread(Player player, Point zielPosition, ScenePanel scenePanel) {
		this.player = player;
		this.zielPosition = zielPosition;
		this.scenePanel = scenePanel;
	}

	private static void init() {

	}

	@Override
	public synchronized void run() {
		init();

		Point oldPosition = null;
		Point currentPosition = player.getPosition();

		long t0, t1;

		Scene oldScene = this.player.getScene();

		while (((oldPosition == null) || (currentPosition != oldPosition))
				&& (!this.stop)) {
			t0 = System.currentTimeMillis();

			oldPosition = currentPosition;
			currentPosition = player.walkTo(zielPosition);
			
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public synchronized void run() {
						scenePanel.repaintPlayer();
						scenePanel.getAdventureFrame().getOptionPanel().dispose();
					}
				});
			} catch (InterruptedException e) {
			} catch (InvocationTargetException e) {
			}

			if (oldScene != this.player.getScene()) {
				this.stop = true;
			}

			do {
				t1 = System.currentTimeMillis();
			} while (t1 - (Main.DEBUG_ON ? 5 : DAUER_PRO_SCHRITT) < t0);

		}

		scenePanel.removeFromThreadList(this);
		scenePanel.repaint();
	}

	@Override
	public void interrupt() {
		this.stop = true;
		super.interrupt();
	}

}
