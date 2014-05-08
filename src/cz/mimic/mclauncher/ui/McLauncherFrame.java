package cz.mimic.mclauncher.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cz.mimic.mclauncher.util.Message;

@SuppressWarnings("serial")
public class McLauncherFrame extends JFrame
{
    public static final String TITLE = "McLauncher";
    public static final Dimension SIZE = new Dimension(480, 720);
    public static final Color BACKGROUND_COLOR = new Color(40, 40, 40);

    private JPanel contentPane;
    private ViewPane viewPane;

    public McLauncherFrame()
    {
        super();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIZE);
        setTitle(TITLE);
        setBackground(BACKGROUND_COLOR);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBackground(getBackground());

        setContentPane(contentPane);

        viewPane = new ViewPane(contentPane);
        contentPane.add(viewPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(this);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    McLauncherFrame frame = new McLauncherFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    Message.error(e.getMessage());
                }
            }
        });
    }
}
