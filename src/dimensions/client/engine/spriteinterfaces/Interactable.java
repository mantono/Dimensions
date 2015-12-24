package dimensions.client.engine.spriteinterfaces;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface Interactable
{
	void setOnKeyPressed(EventHandler<? super KeyEvent> value);
	void setOnKeyReleased(EventHandler<? super KeyEvent> value);
	void setOnKeyTyped(EventHandler<? super KeyEvent> value);
	
	void setOnMouseClicked(EventHandler<? super MouseEvent> value);
	void setOnMouseEntered(EventHandler<? super MouseEvent> value);
	void setOnMouseExited(EventHandler<? super MouseEvent> value);
	void setOnMouseMoved(EventHandler<? super MouseEvent> value);
	void setOnMousePressed(EventHandler<? super MouseEvent> value);
	void setOnMouseReleased(EventHandler<? super MouseEvent> value);
}
