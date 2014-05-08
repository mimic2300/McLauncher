package cz.mimic.mclauncher.ui.component.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.LineBorder;

public final class ComponentUtil
{
    public static final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    public static final Color BACKGROUND_COLOR_HOVER = new Color(28, 28, 28);
    public static final Color BACKGROUND_COLOR_ACTIVE = new Color(28, 28, 28);
    public static final Color FOREGROUND_COLOR = new Color(240, 160, 100);
    public static final Color FOREGROUND_COLOR_HOVER = new Color(220, 180, 120);
    public static final Color FOREGROUND_COLOR_ACTIVE = new Color(220, 180, 120);
    public static final Color CARET_COLOR = new Color(160, 160, 160);
    public static final Color CARET_COLOR_HOVER = new Color(240, 240, 240);

    public static final LineBorder BORDER = new LineBorder(new Color(60, 60, 60));
    public static final LineBorder BORDER_HOVER = new LineBorder(new Color(68, 68, 68));

    public static final Font FONT = new Font("verdana", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font("verdana", Font.BOLD, 13);

    private ComponentUtil()
    {}

    public static void addBorderSpaces(JComponent com)
    {
        com.setBorder(BorderFactory.createCompoundBorder(com.getBorder(), BorderFactory.createEmptyBorder(2, 4, 2, 4)));
    }
}
