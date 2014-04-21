package cz.mimic.mclauncher.util;

import javax.swing.JOptionPane;

public final class Message
{
    private Message()
    {}

    public static void info(String message)
    {
        JOptionPane.showMessageDialog(null, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warning(String message)
    {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void error(String message)
    {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
