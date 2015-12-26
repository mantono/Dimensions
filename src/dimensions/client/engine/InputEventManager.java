package dimensions.client.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dimensions.client.engine.spriteinterfaces.Player;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputEventManager
{
	private final Map<KeyBinding, EventHandler<KeyEvent>> keyBindings = new HashMap<KeyBinding, EventHandler<KeyEvent>>();
	private final Set<KeyCode> boundKeys = new HashSet<KeyCode>();
	private final Map<MouseEvent, EventHandler<MouseEvent>> mouseBindings = new HashMap<MouseEvent, EventHandler<MouseEvent>>();
	
	private final KeyInputHandler keyHandler = new KeyInputHandler();
	private final MouseInputHandler mouseHandler = new MouseInputHandler();
	
	public InputEventManager(Scene scene)
	{
		listenTo(scene);
	}
	
	public void listenTo(Scene scene)
	{
		scene.addEventFilter(KeyEvent.ANY, keyHandler);
		scene.addEventFilter(MouseEvent.ANY, mouseHandler);
	}
	
	public void listenToKeyEvent(Scene scene, EventType<KeyEvent> type)
	{
		scene.addEventFilter(type, keyHandler);
	}
	
	public void listenToMouseEvent(Scene scene, EventType<MouseEvent> type)
	{
		scene.addEventFilter(type, mouseHandler);
	}
	
	public void createDefaultKeyBindings(Player player)
	{
		addKeyBinding(KeyCode.RIGHT, e -> player.rightPressed());
		addKeyBinding(KeyCode.LEFT, e -> player.leftPressed());
		addKeyBinding(KeyCode.UP, e -> player.upPressed());
		addKeyBinding(KeyCode.DOWN, e -> player.downPressed());

		KeyBinding eventStop = new KeyBinding(KeyEvent.KEY_RELEASED, KeyCode.RIGHT);
		addKeyBinding(eventStop, e -> player.rightReleased());
		eventStop = new KeyBinding(KeyEvent.KEY_RELEASED, KeyCode.LEFT);
		addKeyBinding(eventStop, e -> player.leftReleased());
		eventStop = new KeyBinding(KeyEvent.KEY_RELEASED, KeyCode.UP);
		addKeyBinding(eventStop, e -> player.upReleased());
		eventStop = new KeyBinding(KeyEvent.KEY_RELEASED, KeyCode.DOWN);
		addKeyBinding(eventStop, e -> player.downReleased());

	}

	public boolean removeKeyBinding(KeyBinding binding)
	{
		if(keyBindings.remove(binding) != null)
		{
			boolean removeKeyCodeFromBindings = true;
			for(KeyBinding kb : keyBindings.keySet())
				if(kb.getCode() == binding.getCode())
					removeKeyCodeFromBindings = false;
			if(removeKeyCodeFromBindings)
				return boundKeys.remove(binding.getCode());
		}

		return false;
	}

	public void addKeyBinding(KeyBinding binding, EventHandler<KeyEvent> handler)
	{
		boundKeys.add(binding.getCode());
		keyBindings.put(binding, handler);
	}

	public void addKeyBinding(KeyEvent event, EventHandler<KeyEvent> handler)
	{
		addKeyBinding(new KeyBinding(event), handler);
	}

	public void addKeyBinding(KeyCode code, EventHandler<KeyEvent> handler)
	{
		final KeyBinding binding = new KeyBinding(KeyEvent.KEY_PRESSED, code);
		addKeyBinding(binding, handler);
	}

	class KeyInputHandler implements EventHandler<KeyEvent>
	{

		@Override
		public void handle(KeyEvent event)
		{
			if(!boundKeys.contains(event.getCode()))
				return;

			final KeyBinding binding = new KeyBinding(event);
			final EventHandler<KeyEvent> eventHandler = keyBindings.get(binding);
			if(eventHandler == null)
				return;

			eventHandler.handle(event);
		}

	}
	
	class MouseInputHandler implements EventHandler<MouseEvent>
	{
		
		@Override
		public void handle(MouseEvent event)
		{
			return;
		}
		
	}
}
