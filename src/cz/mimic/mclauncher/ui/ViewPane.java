package cz.mimic.mclauncher.ui;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import cz.mimic.mclauncher.ui.component.Button;
import cz.mimic.mclauncher.ui.component.TextBox;
import cz.mimic.mclauncher.ui.component.util.Executor;

@SuppressWarnings("serial")
public class ViewPane extends JPanel
{
    private TextBox textBox;
    private Button button;

    public ViewPane(JPanel parent)
    {
        super();

        setBackground(parent.getBackground());

        textBox = new TextBox();
        textBox.setColumns(10);

        button = new Button("Click me");
        button.addExecutor(new Executor()
        {
            @Override
            public void execute(Object source, Object userData)
            {
                System.out.println("Clicked!");
            }
        });

        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(textBox,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(button, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(229, Short.MAX_VALUE)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                .addComponent(button,
                                        Alignment.LEADING,
                                        GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(textBox,
                                        Alignment.LEADING,
                                        GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(266, Short.MAX_VALUE)));
        setLayout(groupLayout);
    }
}
