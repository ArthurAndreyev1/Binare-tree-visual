// KND-21 Андреєв Артур,2023
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BinaryTreePanel extends JPanel {
    protected static final int NODE_RADIUS = 20; // Радіус вузла дерева

//    protected static final int HORIZONTAL_GAP = 40; // Горизонтальний відступ між вузлами (закоментовано, можливо, не використовується)

    protected static final int VERTICAL_GAP = 100; // Вертикальний відступ між рівнями дерева

    protected Map<BinaryTreeNode, Point> nodePositions; // Зберігає позиції вузлів дерева
    protected Map<BinaryTreeNode, Integer> subtreeSizes; // Зберігає розміри піддерев

    private BinaryTreeNode root; // Кореневий вузол дерева



    public BinaryTreePanel() {
        setLayout(new BorderLayout());

        nodePositions = new HashMap<>();// Ініціалізуємо HashMap для зберігання позицій вузлів дерева
        subtreeSizes = new HashMap<>();// Ініціалізуємо HashMap для зберігання розмірів піддерев
        JPanel panel = new JPanel();// Створюємо панель для розміщення міток та інформації про обход дерева
        JLabel traversalLabel = new JLabel("");// Ініціалізуємо мітку для відображення обходу дерева
        // Додаємо пусту мітку та мітку для обходу дерева до панелі
        panel.add(new JLabel(""));
        panel.add(traversalLabel);
        // Додаємо панель з мітками до панелі BinaryTreePanel у верхню частину (північна частина) BorderLayout
        add(panel, BorderLayout.NORTH);
    }


    public void setRoot(BinaryTreeNode root) {
        this.root = root; // Встановлює кореневий вузол дерева
        updateSubtreeSizes(root); // Оновлює розміри піддерев
        repositionNodes(); // Перепозиціоновує вузли дерева
        repaint(); // Перемальовує компоненту для оновлення відображення дерева
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (root != null) {
            drawTree(g, root, getWidth() / 2, 30, getWidth() / 4); // Малює дерево на компоненті
        }
    }
    private void drawTree(Graphics g, BinaryTreeNode node, int x, int y, int xOffset) {
        int nodeX = x - NODE_RADIUS; // Визначаємо координату X верхнього лівого кута вузла
        int nodeY = y - NODE_RADIUS; // Визначаємо координату Y верхнього лівого кута вузла
        int nodeWidth = 2 * NODE_RADIUS; // Визначаємо ширину вузла
        int nodeHeight = 2 * NODE_RADIUS; // Визначаємо висоту вузла

        g.setColor(Color.decode("#EAEAEA")); // Задаємо колір заливки вузлів
        g.fillOval(nodeX, nodeY, nodeWidth, nodeHeight); // Заповнюємо овал за допомогою вказаних координат і розмірів

        g.setColor(Color.BLACK); // Задаємо колір обводки
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1)); // Задаємо ширину обводки
        g2d.drawOval(nodeX, nodeY, nodeWidth, nodeHeight); // Малюємо овал за допомогою вказаних координат і розмірів

        String nodeValue = String.valueOf(node.getValue()); // Отримуємо значення вузла у вигляді рядка
        FontMetrics fm = g.getFontMetrics(); // Отримуємо метри шрифту
        int textWidth = fm.stringWidth(nodeValue); // Визначаємо ширину тексту
        int textHeight = fm.getHeight(); // Визначаємо висоту тексту
        int textX = x - textWidth / 2; // Визначаємо координату X для центру тексту
        int textY = y + textHeight / 2; // Визначаємо координату Y для центру тексту

        g.setColor(Color.BLACK); // Задаємо колір тексту
        g.drawString(nodeValue, textX, textY); // Малюємо текст на вузлі

        Point leftChildPos = nodePositions.get(node.getLeftChild()); // Отримуємо позицію лівого дитини вузла
        if (leftChildPos != null) {
            drawConnector(g, x, y, leftChildPos.x, leftChildPos.y); // Малюємо з'єднувальну лінію між батьківським вузлом і лівим дитиною
            drawTree(g, node.getLeftChild(), leftChildPos.x, leftChildPos.y, xOffset / 2); // Рекурсивно малюємо ліве піддерево
        }

        Point rightChildPos = nodePositions.get(node.getRightChild()); // Отримуємо позицію правого дитини вузла
        if (rightChildPos != null) {
            drawConnector(g, x, y, rightChildPos.x, rightChildPos.y); // Малюємо з'єднувальну лінію між батьківським вузлом і правим дитиною
            drawTree(g, node.getRightChild(), rightChildPos.x, rightChildPos.y, xOffset / 2); // Рекурсивно малюємо праве піддерево
        }
    }


    private void drawConnector(Graphics g, int x1, int y1, int x2, int y2) {
        int nodeY2 = y2 - 20 + NODE_RADIUS; // Визначаємо Y-координату нижньої точки вузла

        int angle = (int) Math.toDegrees(Math.atan2(nodeY2 - y1, x2 - x1)); // Визначаємо кут між двома точками
        int xOffset = (int) (NODE_RADIUS * Math.cos(Math.toRadians(angle))); // Визначаємо зміщення X для з'єднувальної лінії
        int yOffset = (int) (NODE_RADIUS * Math.sin(Math.toRadians(angle))); // Визначаємо зміщення Y для з'єднувальної лінії

        g.drawLine(x1 + xOffset, y1 + yOffset, x2 - xOffset, nodeY2 - yOffset); // Малюємо з'єднувальну лінію між двома точками
    }

    private void updateSubtreeSizes(BinaryTreeNode node) {
        if (node == null) return; // Базовий випадок: якщо вузол є пустим, повертаємося

        int leftSubtreeSize = calculateSubtreeSize(node.getLeftChild()); // Розраховуємо розмір лівого піддерева
        int rightSubtreeSize = calculateSubtreeSize(node.getRightChild()); // Розраховуємо розмір правого піддерева

        subtreeSizes.put(node, leftSubtreeSize + rightSubtreeSize + 1); // Зберігаємо розмір піддерева для поточного вузла

        updateSubtreeSizes(node.getLeftChild()); // Рекурсивно оновлюємо розмір лівого піддерева
        updateSubtreeSizes(node.getRightChild()); // Рекурсивно оновлюємо розмір правого піддерева
    }
    private int calculateSubtreeSize(BinaryTreeNode node) {
        if (node == null) return 0; // Базовий випадок: якщо вузол є пустим, повертаємо 0
        return subtreeSizes.getOrDefault(node, 0); // Повертаємо розмір піддерева для даного вузла, або 0, якщо розмір не визначено
    }

    public void repositionNodes() {
        nodePositions.clear(); // Очищаємо мапу позицій вузлів
        positionNode(root, getWidth() / 2, 30, getWidth() / 4); // Позиціонуємо вузли, починаючи з кореня дерева
    }

    private void positionNode(BinaryTreeNode node, int x, int y, int xOffset) {
        if (node == null) return; // Базовий випадок: якщо вузол є пустим, повертаємося

        nodePositions.put(node, new Point(x, y)); // Зберігаємо позицію вузла

        positionNode(node.getLeftChild(), x - xOffset, y + VERTICAL_GAP, xOffset / 2); // Рекурсивно позиціонуємо ліве піддерево
        positionNode(node.getRightChild(), x + xOffset, y + VERTICAL_GAP, xOffset / 2); // Рекурсивно позиціонуємо праве піддерево
    }

    public void animateNode(BinaryTreeNode node, int x, int y) {
        Graphics g = getGraphics(); // Отримуємо об'єкт Graphics для малювання
        int diameter = 40; // Діаметр круга вузла
        int centerX = x + diameter / 2; // Координата центру вузла по осі X
        int centerY = y + diameter / 2; // Координата центру вузла по осі Y

        g.setColor(Color.decode("#FFAA88")); // Задаємо колір заповнення вузла
        g.fillOval(x, y, diameter, diameter); // Заповнюємо круг вузла відповідним кольором

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED); // Задаємо червоний колір для контуру
        g2d.setStroke(new BasicStroke(3)); // Задаємо ширину контуру

        g2d.drawOval(x + 1, y + 1, diameter - 2, diameter - 2); // Малюємо контур кола вузла

        g.setColor(Color.RED); // Задаємо червоний колір тексту
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(Integer.toString(node.getValue())); // Отримуємо ширину тексту
        int stringHeight = fm.getAscent(); // Отримуємо висоту тексту

        g.drawString(Integer.toString(node.getValue()), centerX - stringWidth / 2, centerY + stringHeight / 2); // Малюємо значення вузла

        try {
            Thread.sleep(400); // Затримка на 0.4 секунди
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Очищаємо вузол
        g.clearRect(x, y, diameter + 1, diameter + 1); // Очищаємо область вузла
        g.setColor(Color.decode("#696969")); // Задаємо сірий колір для контуру
        g2d.setStroke(new BasicStroke(1)); // Задаємо ширину контуру
        g2d.drawOval(x, y, diameter, diameter); // Малюємо коло, що представляє вузол

        g.setColor(Color.decode("#696969"));// Встановлюємо сірий колір тексту
        g.drawString(Integer.toString(node.getValue()), centerX - stringWidth / 2, centerY + stringHeight / 2); // Малюємо значення вузла
    }
}