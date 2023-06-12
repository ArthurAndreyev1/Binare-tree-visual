// KND-21 Андреєв Артур,2023
import javax.swing.*;
import java.awt.*;

public class BinaryTreeSimulator {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BinaryTreeSimulator::run);
    }

    private static void run() {
        JFrame frame = new JFrame("Binary Tree Simulator"); // Створення головного вікна
        frame.setSize(1080, 720); // Встановлення розміру вікна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Встановлення дії при закритті вікна

        BinaryTreePanel binaryTreePanel = new BinaryTreePanel(); // Створення панелі для відображення бінарного дерева
        ControlPanel controlPanel = new ControlPanel(binaryTreePanel); // Створення панелі управління

        frame.getContentPane().setLayout(new BorderLayout()); // Встановлення менеджера розташування для контейнера головного вікна
        frame.getContentPane().add(binaryTreePanel, BorderLayout.CENTER); // Додавання панелі бінарного дерева у центр головного вікна
        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH); // Додавання панелі управління у нижню частину головного вікна

        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                binaryTreePanel.repositionNodes(); // Перерахунок позицій вузлів при зміні розміру вікна
                binaryTreePanel.repaint(); // Перемалювання панелі бінарного дерева
            }
        });

        frame.setVisible(true); // Відображення головного вікна
    }

}

