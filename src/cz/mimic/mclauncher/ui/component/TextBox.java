package cz.mimic.mclauncher.ui.component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import cz.mimic.mclauncher.ui.component.util.ComponentUtil;

@SuppressWarnings("serial")
public class TextBox extends JTextField
{
    public TextBox()
    {
        super();

        setBackground(ComponentUtil.BACKGROUND_COLOR);
        setForeground(ComponentUtil.FOREGROUND_COLOR);
        setCaretColor(ComponentUtil.CARET_COLOR);
        setFont(ComponentUtil.FONT);

        updateBorder(false);

        addMouseListener(new MouseTrigger());
    }

    private void updateBorder(boolean hover)
    {
        setBorder(hover ? ComponentUtil.BORDER_HOVER : ComponentUtil.BORDER);
        ComponentUtil.addBorderSpaces(this);
    }

    private class MouseTrigger extends MouseAdapter
    {
        @Override
        public void mouseEntered(MouseEvent e)
        {
            setBackground(ComponentUtil.BACKGROUND_COLOR_HOVER);
            setForeground(ComponentUtil.FOREGROUND_COLOR_HOVER);
            setCaretColor(ComponentUtil.CARET_COLOR_HOVER);
            updateBorder(true);
            updateUI();
            setCaretPosition(getText().length());
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            setBackground(ComponentUtil.BACKGROUND_COLOR);
            setForeground(ComponentUtil.FOREGROUND_COLOR);
            setCaretColor(ComponentUtil.CARET_COLOR);
            updateBorder(false);
            updateUI();
            setCaretPosition(getText().length());
        }
    }
}
