package graphics.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PaintDisplay extends JPanel {

    private static final LayoutManager H = new GridLayout(1, 0);
    private static final LayoutManager V = new GridLayout(0, 1);

    public PaintDisplay() {
        this.setLayout(H);
        this.setPreferredSize(new Dimension(320, 240));
        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("Label " + String.valueOf(i), JLabel.CENTER));
        }
    }

    private void display() {
        JFrame f = new JFrame("DynamicLayout");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        JPanel p = new JPanel();
        p.add(new JButton(new AbstractAction("Horizontal") {

            @Override
            public void actionPerformed(ActionEvent e) {
                PaintDisplay.this.setLayout(H);
                PaintDisplay.this.validate();
            }
        }));
        p.add(new JButton(new AbstractAction("Vertical") {

            @Override
            public void actionPerformed(ActionEvent e) {
                PaintDisplay.this.setLayout(V);
                PaintDisplay.this.validate();
            }
        }));
        f.add(p, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new PaintDisplay().display());
    }
}
