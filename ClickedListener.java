package a;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * Interface for MouseLIstener this allows us to ignore all these other 
 * methods that would have to be otherwise implemented 
 * **/
	public interface ClickedListener extends MouseListener
	{
	    @Override
	    public default void mouseEntered(MouseEvent e) {}

	    @Override
	    public default void mouseExited(MouseEvent e) {}

	    @Override
	    public default  void mousePressed(MouseEvent e) {}

	    @Override
	    public default void mouseReleased(MouseEvent e) {}
	}

