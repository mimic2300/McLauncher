package cz.mimic.mclauncher.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import cz.mimic.mclauncher.minecraft.MinecraftVersion;
import cz.mimic.mclauncher.ui.component.util.Executor;

@SuppressWarnings("serial")
public class VersionSelector extends JDialog
{
    private final JPanel contentPanel = new JPanel();

    @SuppressWarnings("rawtypes")
    private JComboBox cbVersion;

    @SuppressWarnings("unchecked")
    public VersionSelector(final Executor executor)
    {
        super();

        setTitle("McLauncher");
        setModal(true);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                executor.execute(this, null);
            }
        });

        JLabel lbVersion = new JLabel("Select minecraft version:");
        lbVersion.setFont(new Font("Verdana", Font.BOLD, 11));

        cbVersion = new JComboBox<MinecraftVersion>();
        for (MinecraftVersion v : MinecraftVersion.getSortedVersions()) {
            cbVersion.addItem(v);
        }

        JButton btnSelect = new JButton("Select");
        btnSelect.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                executor.execute(this, cbVersion.getSelectedItem());
                setVisible(false);
            }
        });

        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_contentPanel.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                .addComponent(cbVersion, 0, 297, Short.MAX_VALUE)
                                .addComponent(lbVersion, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSelect, Alignment.TRAILING))
                        .addContainerGap()));
        gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPanel.createSequentialGroup()
                        .addComponent(lbVersion)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(cbVersion,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(btnSelect)
                        .addContainerGap()));
        contentPanel.setLayout(gl_contentPanel);

        pack();
        setLocationRelativeTo(this);
        setMinimumSize(getSize());
    }
}
