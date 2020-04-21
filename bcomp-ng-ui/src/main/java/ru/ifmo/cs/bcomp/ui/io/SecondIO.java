package ru.ifmo.cs.bcomp.ui.io;

import ru.ifmo.cs.bcomp.IOCtrl;
import ru.ifmo.cs.bcomp.ui.components.ComponentManager;
import ru.ifmo.cs.bcomp.ui.components.InputRegisterViewByte;
import ru.ifmo.cs.components.DataDestination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ru.ifmo.cs.bcomp.ui.components.DisplayStyles.*;

public class SecondIO extends IODevice {
    private ComponentManager componentManager;
    private InputRegisterViewByte input;

    public SecondIO(IOCtrl ioCtrl, ComponentManager componentManager) {
        super(ioCtrl, "input");
        this.componentManager = componentManager;
    }

    @Override
    protected Component getContent() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(500, 150));
        panel.setBackground(COLOR_BACKGROUND);
        input = new InputRegisterViewByte(componentManager, ioctrl.getRegisters()[0]);
        input.setProperties(0, 0, false, false);
        input.setPreferredSize(input.getSize());
        input.setMinimumSize(input.getSize());
        input.setTitle("ВУ");
        JButton button = new JButton(getRes().getString("ready"));
        button.setFont(FONT_COURIER_PLAIN_12);
        button.setBackground(COLOR_VALUE);
        button.setForeground(COLOR_TEXT);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ioctrl.setReady();
            }
        });
        ioctrl.addDestination(ioctrl.getRegisters()[1], new DataDestination() {
            @Override
            public void setValue(long value) {
                button.setForeground(value == 1 ? COLOR_ACTIVE : COLOR_TEXT);
            }

        });
        GridBagConstraints constraints = new GridBagConstraints() {{
            gridy = 0;
            gridx = 3;
            gridwidth = GridBagConstraints.REMAINDER;
        }};
        panel.add(input, constraints);
        constraints.gridy++;
        constraints.insets.top += 30;
        panel.add(button, constraints);
        return panel;
    }

    @Override
    public void activate() {
        super.activate();
        input.setActive();
    }
}