package cz.mimic.mclauncher.ui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cz.mimic.mclauncher.ui.component.util.ComponentUtil;
import cz.mimic.mclauncher.ui.component.util.Executor;

@SuppressWarnings("serial")
public class Button extends JPanel
{
    private List<Executor> executors = new ArrayList<Executor>();
    private MouseTrigger mouse = new MouseTrigger();

    private JLabel label;

    public Button(String text)
    {
        super();

        label = new JLabel(text, SwingConstants.CENTER);

        setLayout(new BorderLayout(0, 0));
        setBackground(ComponentUtil.BACKGROUND_COLOR);
        setForeground(ComponentUtil.FOREGROUND_COLOR);
        setFont(ComponentUtil.FONT);

        updateBorder(false);

        addMouseListener(mouse);
        add(label);
    }

    public void addExecutor(Executor executor)
    {
        executors.add(executor);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if (mouse.mouseDown) {
            g.setColor(ComponentUtil.BACKGROUND_COLOR_ACTIVE);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void setBackground(Color color)
    {
        super.setBackground(color);

        if (label != null) {
            label.setBackground(color);
        }
    }

    @Override
    public void setForeground(Color color)
    {
        super.setForeground(color);

        if (label != null) {
            label.setForeground(color);
        }
    }

    @Override
    public void setFont(Font font)
    {
        super.setFont(font);

        if (label != null) {
            label.setFont(font);
        }
    }

    private void updateBorder(boolean hover)
    {
        setBorder(hover ? ComponentUtil.BORDER_HOVER : ComponentUtil.BORDER);
        ComponentUtil.addBorderSpaces(this);
    }

    private void fireExecute()
    {
        for (Executor e : executors) {
            e.execute(this, null);
        }
    }

    private class MouseTrigger extends MouseAdapter
    {
        private boolean mouseDown = false;

        @Override
        public void mouseEntered(MouseEvent e)
        {
            setBackground(ComponentUtil.BACKGROUND_COLOR_HOVER);
            setForeground(ComponentUtil.FOREGROUND_COLOR_HOVER);

            updateBorder(true);
            updateUI();
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            setBackground(ComponentUtil.BACKGROUND_COLOR);
            setForeground(ComponentUtil.FOREGROUND_COLOR);
            setFont(ComponentUtil.FONT);

            updateBorder(false);
            updateUI();
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            mouseDown = true;
            setBackground(ComponentUtil.BACKGROUND_COLOR_ACTIVE);
            setForeground(ComponentUtil.FOREGROUND_COLOR_ACTIVE);
            setFont(ComponentUtil.FONT_BOLD);
            updateUI();
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            final Point screen = getLocationOnScreen();
            final Rectangle rect = new Rectangle(screen.x, screen.y, getWidth(), getHeight());

            if (rect.contains(e.getLocationOnScreen())) {
                fireExecute();
            }
            mouseDown = false;
            setBackground(ComponentUtil.BACKGROUND_COLOR);
            setForeground(ComponentUtil.FOREGROUND_COLOR);
            setFont(ComponentUtil.FONT);
            updateUI();
        }
    }
}
