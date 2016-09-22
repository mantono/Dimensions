package dimensions.client.engine;

import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyBinding
{
	private final EventType<KeyEvent> eventType;
	private final KeyCode code;
	private final boolean shiftDown, controlDown, altDown, metaDown;
	
	public KeyBinding(final KeyEvent event)
	{
		this.eventType = event.getEventType();
		this.code = event.getCode();
		this.shiftDown = event.isShiftDown();
		this.controlDown = event.isControlDown();
		this.altDown = event.isAltDown();
		this.metaDown = event.isMetaDown();
	}
	
	public KeyBinding(EventType<KeyEvent> eventType, KeyCode code, boolean shift, boolean control, boolean alt, boolean meta)
	{
		this.eventType = eventType;
		this.code = code;
		this.shiftDown = shift;
		this.controlDown =  control;
		this.altDown = alt;
		this.metaDown = meta;
	}
	
	public KeyBinding(EventType<KeyEvent> eventType, KeyCode code)
	{
		this(eventType, code, false, false, false, false);
	}
	
	public KeyBinding(EventType<KeyEvent> eventType, KeyCode code, int flags)
	{
		this.eventType = eventType;
		this.code = code;
		this.shiftDown = 0x1000 == (flags & 0x1000);
		this.controlDown = 0x0100 == (flags & 0x0100);
		this.altDown = 0x0010 == (flags & 0x0010);
		this.metaDown = 0x001 == (flags & 0x0001);
	}
	
	public KeyBinding(KeyCode code)
	{
		this(KeyEvent.KEY_PRESSED, code, false, false, false, false);
	}
	
	public KeyBinding(final String key)
	{
		this(KeyCode.getKeyCode(key));
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(object == null)
			return false;
		if(this == object)
			return true;
		if(!(object instanceof KeyBinding))
			return false;
		
		KeyBinding other = (KeyBinding) object;
		
		final boolean sameEventType = this.eventType.equals(other.eventType);
		final boolean sameCode = this.code == other.code;
		final boolean sameShift = this.shiftDown == other.shiftDown;
		final boolean sameControl = this.controlDown == other.controlDown;
		final boolean sameAlt = this.altDown == other.altDown;
		final boolean sameMeta = this.metaDown == other.metaDown;
		
		return sameEventType && sameCode && sameShift && sameControl && sameAlt && sameMeta;		
	}
	
	@Override
	public int hashCode()
	{
		final byte prime = 17;
		int hash = prime;
		hash += eventType.hashCode();
		hash *= prime;
		hash += code.hashCode();
		hash *= prime;
		if(shiftDown)
			hash <<= 1;
		if(controlDown)
			hash <<= 2;
		if(altDown)
			hash <<= 4;
		if(metaDown)
			hash <<= 8;
		
		return hash;
	}

	public KeyCode getCode()
	{
		return code;
	}
}
