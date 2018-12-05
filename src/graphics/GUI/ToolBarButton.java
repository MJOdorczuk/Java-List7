package graphics.GUI;

import javax.swing.*;

public class ToolBarButton extends JButton {
    public final String ID;

    public ToolBarButton(String ID, Icon image)
    {
        this.ID = ID;
        setIcon(image);
    }
}
