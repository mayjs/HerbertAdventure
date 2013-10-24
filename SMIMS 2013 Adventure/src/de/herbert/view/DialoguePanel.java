package de.herbert.view;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.model.Dialogue;

public class DialoguePanel extends Component{

	ScrollableFormattedTextPanel textPanel;
	List<FormattedTextButton> buttons = new LinkedList<FormattedTextButton>();
	List<FormattedTextButton> buttonsNew = new LinkedList<FormattedTextButton>();
	Dialogue dialogue;
	
	float buttonY;
	float buttonX;
	float buttonGap = 10;
	float rowHeight = 20;
	int maxButtonsPerRow = 3;
	
	
	public DialoguePanel(Rectangle boundings, Dialogue dialogue) {
		super(boundings);
		this.dialogue = dialogue;
		textPanel = new ScrollableFormattedTextPanel(boundings, dialogue.getCurPart().getContent());
		uAnswerButtons();
	}
	
	boolean isUpdating = false;
	private void updateAnswerButtons(){
		isUpdating = true;
		buttonsNew = new LinkedList<FormattedTextButton>();
		int j;
		float buttonWidth = boundings.getWidth() / maxButtonsPerRow - buttonGap;
		float buttonHeight = 30;
		buttonY = (float) (boundings.getMaxY() - Math.floor(dialogue.getCurPart().getCountOfAnswers()/maxButtonsPerRow + 1) * (buttonHeight + buttonGap));
		for(int i = 0; i < dialogue.getCurPart().getCountOfAnswers(); i++){
			j = i % maxButtonsPerRow;
			FormattedTextButton bttn = new FormattedTextButton(new Rectangle(boundings.getX() + buttonGap + j * buttonWidth, (buttonHeight + buttonGap)*(j>0?(int)i/j:0) + buttonY + buttonGap, buttonWidth, buttonHeight), dialogue.getCurPart().getAnswers().get(i).getText());
			bttn.addButtonListener(answerClicked, dialogue.getCurPart().getAnswers().get(i).getId());
			buttonsNew.add(bttn);
		}
		isUpdating = false;
		textPanel.setBoundings(new Rectangle(boundings.getX(), boundings.getY(), boundings.getWidth(), buttonY - boundings.getY()));
		textPanel.setText(dialogue.getCurPart().getContent());
	}
	
	private void uAnswerButtons(){
		buttonsNew = new LinkedList<FormattedTextButton>();
		float buttonWidth = (boundings.getWidth() - buttonGap) / maxButtonsPerRow - buttonGap;
		float[] posY = new float[maxButtonsPerRow];
		int curPos = 0;
		
		for(int i = 0; i < dialogue.getCurPart().getCountOfAnswers(); i++){
			while(posY[curPos % maxButtonsPerRow] > (rowHeight + buttonGap)*(curPos/maxButtonsPerRow)){
				curPos++;
			}
			
			// calculate height of button
			float buttonHeight;
			float div = (dialogue.getCurPart().getAnswers().get(i).getText().getHeight() / (rowHeight + buttonGap));
			if(div < 1)
				buttonHeight = rowHeight;
			else 
				buttonHeight = (float) (Math.floor(div) * (rowHeight + buttonGap) + rowHeight); 
			
			posY[curPos % maxButtonsPerRow] += buttonHeight + buttonGap;
			FormattedTextButton button = new FormattedTextButton(new Rectangle(0, 0, buttonWidth, buttonHeight), dialogue.getCurPart().getAnswers().get(i).getText());
			button.addButtonListener(answerClicked, dialogue.getCurPart().getAnswers().get(i).getId());
			buttonsNew.add(button);
		}
		
		float buttonsHeight = 0;
		for(float f : posY){
			buttonsHeight = f < buttonsHeight ? buttonsHeight : f;
		}
		
		buttonY = boundings.getMaxY() - buttonsHeight - buttonGap;
		
		// calculate coordinates of buttons
		posY = new float[maxButtonsPerRow];
		curPos = 0;
		for(int i = 0; i < buttonsNew.size(); i++){
			while(posY[curPos % maxButtonsPerRow] > (rowHeight + buttonGap)*(curPos/maxButtonsPerRow)){
				curPos++;
			}
			buttonsNew.get(i).setBoundings(new Rectangle(
										(curPos % maxButtonsPerRow) * (buttonWidth + buttonGap) + buttonGap + boundings.getX(),
										posY[curPos % maxButtonsPerRow] + buttonGap + buttonY,
										buttonsNew.get(i).getBoundings().getWidth(), buttonsNew.get(i).getBoundings().getHeight()));
			
			posY[i%maxButtonsPerRow] += buttonsNew.get(i).getBoundings().getHeight() + buttonGap;
		}
		
		// set textPanel size
		textPanel.setBoundings(new Rectangle(boundings.getX(), boundings.getY(), boundings.getWidth(), buttonY - boundings.getY()));
	}
	
	public void setCurPart(String id){
		dialogue.setCurPart(id);
		uAnswerButtons();
		textPanel.setText(dialogue.getCurPart().getContent());
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(!isUpdating) {
			buttons = buttonsNew;
		}
		
		for(FormattedTextButton b : buttons){
			b.update(container, delta);
		}
		textPanel.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		//g.fill(boundings, new GradientFill(0, 0, Color.black, 1, 1, Color.blue));
		g.setColor(Color.gray);
		g.draw(boundings);
		g.fill(boundings, new GradientFill(boundings.getX(), boundings.getCenterY(), Color.white, boundings.getX(), boundings.getMaxY(), new Color(0xFFEBCD)));//new Color(0xFFDAB9)));
		textPanel.render(container, g);
		for(FormattedTextButton b : buttons){
			b.render(container, g);
		}
		
		
	}
	
	ButtonListener answerClicked = new ButtonListener(){
		public void buttonClicked(String buttonId){
			// TODO pass value to control
			setCurPart(buttonId);
		}
	};
}
