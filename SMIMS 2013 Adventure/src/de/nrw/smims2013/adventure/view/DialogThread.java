package de.nrw.smims2013.adventure.view;

import javax.swing.JLabel;

public class DialogThread extends Thread {

	private JLabel dialog;
	private String text;
	private DialogPanel dialogPanel;

	public DialogThread(DialogPanel pDialogPanel, JLabel pDialog, String pText) {
		dialogPanel = pDialogPanel;
		dialog = pDialog;
		text = pText;
	}

	@Override
	public void run() {
		dialog.setText(text);
		dialogPanel.setVisible(true);
		if (dialog.getText() != null) {
			long startTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - startTime < 1500) {
				;
			}
		}
		if (!isInterrupted()) {
			this.interrupt();
		}
	}

	@Override
	public void interrupt() {
		super.interrupt();
		dialog.setText(null);
		dialogPanel.setVisible(false);
	}

}
