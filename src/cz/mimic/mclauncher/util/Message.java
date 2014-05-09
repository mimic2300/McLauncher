package cz.mimic.mclauncher.util;

import javax.swing.JOptionPane;

/**
 * Zpravy v podobe MessageBox.
 * 
 * @author mimic
 */
public final class Message
{
    private Message()
    {}

    /**
     * Zobrazi okno s chybou.
     * 
     * @param message
     * @param params
     */
    public static void error(String message, Object... params)
    {
        JOptionPane.showMessageDialog(null, String.format(message, params), "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Zobrazi okno s chybou.
     * 
     * @param ex
     */
    public static void error(Exception ex)
    {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Zobrazi okno s varovanim.
     * 
     * @param message
     * @param params
     */
    public static void warning(String message, Object... params)
    {
        JOptionPane.showMessageDialog(null, String.format(message, params), "Warning", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Zobrazi okno s oznamenim.
     * 
     * @param message
     * @param params
     */
    public static void info(String message, Object... params)
    {
        JOptionPane.showMessageDialog(null, String.format(message, params), "Notice", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Zobrazi okno s debugem.
     * 
     * @param message
     * @param params
     */
    public static void debug(String message, Object... params)
    {
        JOptionPane.showMessageDialog(null, String.format(message, params), "Debug", JOptionPane.INFORMATION_MESSAGE);
    }
}
