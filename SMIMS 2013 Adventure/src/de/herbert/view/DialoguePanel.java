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
import de.herbert.parser.FormattedText;
import de.herbert.parser.Text;

public class DialoguePanel extends Component{

	ScrollableFormattedTextPanel textPanel;
	ScrollableFormattedTextPanel textPanelNew;
	List<FormattedTextButton> buttons = new LinkedList<FormattedTextButton>();
	List<FormattedTextButton> buttonsNew = new LinkedList<FormattedTextButton>();
	Dialogue dialogue;
	Rectangle tpBoundings = new Rectangle(0, 0, 0, 0);
	
	float buttonY;
	float buttonYOld;
	float buttonX;
	float buttonGap = 10;
	float rowHeight = 20;
	int maxButtonsPerRow = 3;
	
	
	public DialoguePanel(Rectangle boundings, Dialogue dialogue) {
		super(boundings);
		this.dialogue = dialogue;
		textPanel = new ScrollableFormattedTextPanel(boundings, dialogue.getCurPart().getContent());
		textPanelNew = new ScrollableFormattedTextPanel(boundings, new FormattedText(Text.emptyText));
		setCurPart(dialogue.getFirstPartId());
		// make the animation start in the right way
		pos = 0;
		buttonYOld = buttonY;
	}
	
	private void updateAnswerButtons(){
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
		//textPanelNew.setBoundings(new Rectangle(boundings.getX(), boundings.getY(), boundings.getWidth(), buttonY - boundings.getY()));
	}
	
	public void setCurPart(String id){
		dialogue.setCurPart(id);
		updateAnswerButtons();
		textPanelNew.setText(dialogue.getCurPart().getContent());
		pos = -buttonYOld + boundings.getMaxY();
		b= true;
	}

	float pos = 0;
	float step = 0;
	boolean b = true;
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		if(Math.abs(pos) > 1) {
			step = Math.signum(pos) * (float) (0.3* delta);
			pos -= step;
		}
		else{
			if(b){
				pos = buttonYOld - boundings.getMaxY();
				buttonYOld = buttonY;
				b = false;
				buttons = buttonsNew;
				textPanel.setBoundings(textPanelNew.getBoundings());
				textPanel.setText(textPanelNew.getText());
			}
			else
				pos = 0;
			step = 0;
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
		
		Rectangle oldClip = g.getWorldClip();
		g.setWorldClip(boundings.getX(),boundings.getY(),boundings.getWidth(), boundings.getHeight());

		
		
		Color color = new Color(50,50, 50, 0);
		if(step != 0){//textPanel.getBoundings().getHeight() != tpBoundings.getHeight()) {
		if(pos > 0){
			tpBoundings = new Rectangle(textPanel.getBoundings().getX(), textPanel.getBoundings().getY(), textPanel.getBoundings().getWidth(), buttonYOld - boundings.getY() - pos - buttonYOld +boundings.getMaxY());
			color.add(new Color(0, 0, 0, Math.abs(pos - boundings.getMaxY() + buttonYOld) * (1 /(boundings.getMaxY() - buttonYOld))));
		}else{
			tpBoundings = new Rectangle(textPanel.getBoundings().getX(), textPanel.getBoundings().getY(), textPanel.getBoundings().getWidth(), buttonYOld - boundings.getY() -pos);
			color.add(new Color(0, 0, 0, Math.abs(pos) * (1 /(boundings.getMaxY() - buttonYOld))));
		}
		
		
			textPanel.setBoundings(tpBoundings);
		}
		
		textPanel.render(container,g);
		// make textPanel gray
		if(step != 0){
			g.setColor(color);
			g.fill(tpBoundings);
		}
		
		// translate and render buttons
		for(FormattedTextButton b : buttons){
				if(pos > 0)
					b.translate(0, - pos - buttonYOld +boundings.getMaxY());
				else 
					b.translate(0, -pos);

			b.render(container, g);
				if(pos > 0)
					b.translate(0,  pos + buttonYOld -boundings.getMaxY());
				else 
					b.translate(0, pos);
		}
		
		
		g.setWorldClip(oldClip);
		
		
	}
	
	ButtonListener answerClicked = new ButtonListener(){
		public void buttonClicked(String buttonId){
			// TODO pass value to control
			setCurPart(buttonId);
		}
	};
}
