package cz.mimic.mclauncher.util;

import javax.swing.JOptionPane;

public final class Message
{
    private Message()
    {}

    public static void error(String message, Object... params)
    {
        JOptionPane.showMessageDialog(null, String.format(message, params), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void error(Exception ex)
    {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void warning(String message, Object... params)
    {
        JOptionPane.showMessageDialog(null, String.format(message, params), "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void info(String message, Object... params)
    {
        JOptionPane.showMessageDialog(null, String.format(message, params), "Notice", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void debug(String message, Object... params)
    {
        JOptionPane.showMessageDialog(null, String.format(message, params), "Debug", JOptionPane.INFORMATION_MESSAGE);
    }
}
