package TicTacToe.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class BrownButton extends JButton {
    // переопределение части свойств стандартной кнопки
    protected final static Color buttonBackgroundColor = new Color(176, 145, 78);
    protected final static Color buttonTextColor = new Color(242, 223, 176);
    protected final static Color pressedButtonColor = new Color(204, 169, 96);
    protected static SoftBevelBorder border = new SoftBevelBorder(BevelBorder.RAISED, new Color(242, 223, 176), new Color(68, 44, 33), new Color(103, 81, 49), new Color(242, 223, 176));
    protected final static CompoundBorder buttonBorder = BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 10, 5, 10));

    public BrownButton() {
        this(null);
    }

    public BrownButton(String name) {
        super(name);
        // установка цвета текста и рамки, отмена стандартного изменения цвета при клике на кнопку
        super.setForeground(buttonTextColor);
        super.setBorder(buttonBorder);
        super.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // установка нужного цвета фона при клике и в обычном состоянии
        if (getModel().isPressed()) {
            g.setColor(pressedButtonColor);
        } else {
            g.setColor(buttonBackgroundColor);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    // получение цвета текста
    public static Color getButtonTextColor() {
        return buttonTextColor;
    }
}
