import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControlPanel extends JPanel {
    private final BinaryTreePanel binaryTreePanel; // Посилання на панель бінарного дерева
    private final JTextField textField; // Поле введення для значення вузла
    private BinaryTreeNode binaryTreeRoot; // Корінь бінарного дерева
    private boolean animationInProgress = false; // Прапорець, що вказує на поточний стан анімації

    private final JButton insertButton; // Кнопка для вставки вузла
    private final JButton deleteButton; // Кнопка для видалення вузла
    private final JButton inorderButton; // Кнопка для інордер-обходу
    private final JButton preorderButton; // Кнопка для преордер-обходу
    private final JButton postorderButton; // Кнопка для постордер-обходу


    public ControlPanel(BinaryTreePanel binaryTreePanel) {
        this.binaryTreePanel = binaryTreePanel; // Збереження посилання на панель бінарного дерева
        setLayout(new FlowLayout()); // Встановлення менеджера розташування FlowLayout для панелі управління

        JLabel label = new JLabel("Enter a key:"); // Мітка для поля введення
        textField = new JTextField(20); // Поле введення для значення вузла

        // Створення кнопок з використанням методу createButton
        insertButton = createButton("Insert");
        deleteButton = createButton("Delete");
        inorderButton = createButton("Inorder Traversal");
        preorderButton = createButton("Preorder Traversal");
        postorderButton = createButton("Postorder Traversal");

        // Додавання слухача подій для кнопки Insert
        insertButton.addActionListener(e -> insertNodeFromTextField());

        // Додавання слухача подій для кнопки Delete
        deleteButton.addActionListener(e -> deleteNodeFromTextField());

        // Додавання слухача подій клавіатури для поля введення
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    insertNodeFromTextField();
                }
            }
        });

        // Додавання слухача подій для кнопки Inorder Traversal
        inorderButton.addActionListener(e -> {
            if (!animationInProgress) {
                String inorder = inorderTraversal(binaryTreeRoot);
                inorder = String.format("%s", inorder.substring(0, inorder.length() - 1));
                JOptionPane.showMessageDialog(ControlPanel.this, "Inorder Traversal: " + inorder);
                disableButtonsDuringAnimation(); // Вимкнення кнопок під час анімації
                animationInProgress = true;
                inorderChange(binaryTreeRoot, getWidth() / 2 - 20, 11, getWidth() / 4);
                enableButtonsAfterAnimation(); // Увімкнення кнопок після анімації
            }
        });

        // Додавання елементів до панелі управління
        add(label);
        add(textField);
        add(insertButton);
        add(deleteButton);
        add(inorderButton);
        add(preorderButton);
        add(postorderButton);
    }


    private void disableButtonsDuringAnimation() {
        setButtonsEnabled(false); // Вимкнення кнопок
        setButtonsColor(Color.GRAY); // Встановлення сірого кольору кнопок
    }

    private void enableButtonsAfterAnimation() {
        setButtonsEnabled(true); // Увімкнення кнопок
        setButtonsColor(null); // Скидання кольору кнопок на значення за замовчуванням
    }

    private void setButtonsEnabled(boolean enabled) {
        insertButton.setEnabled(enabled); // Встановлення доступності кнопки Insert
        deleteButton.setEnabled(enabled); // Встановлення доступності кнопки Delete
        inorderButton.setEnabled(enabled); // Встановлення доступності кнопки Inorder Traversal
        preorderButton.setEnabled(enabled); // Встановлення доступності кнопки Preorder Traversal
        postorderButton.setEnabled(enabled); // Встановлення доступності кнопки Postorder Traversal
    }

    private void setButtonsColor(Color color) {
        insertButton.setBackground(color); // Встановлення кольору фону кнопки Insert
        deleteButton.setBackground(color); // Встановлення кольору фону кнопки Delete
        inorderButton.setBackground(color); // Встановлення кольору фону кнопки Inorder Traversal
        preorderButton.setBackground(color); // Встановлення кольору фону кнопки Preorder Traversal
        postorderButton.setBackground(color); // Встановлення кольору фону кнопки Postorder Traversal
    }


    private void insertNodeFromTextField() {
        String input = textField.getText().trim(); // Отримання текстового введення з текстового поля
        if (!input.isEmpty() && isNumeric(input)) { // Перевірка, чи введений рядок не є порожнім і складається лише з чисел
            int value = Integer.parseInt(input); // Парсинг числового значення з рядка
            binaryTreeRoot = insertNode(binaryTreeRoot, value); // Вставка вузла зі значенням у бінарне дерево
            binaryTreePanel.setRoot(binaryTreeRoot); // Оновлення кореня бінарного дерева на панелі
            textField.setText(""); // Очищення текстового поля
        } else {
            JOptionPane.showMessageDialog(ControlPanel.this,
                    """
                   　　　　　／\\____ / フ
                   　　　　　| 　_　 _ l  Ви ,
                   　 　　　／` ミ＿x ノ  ввели
                   　　 　 /　　　　 |   не вірно
                   　　　 /　 ヽ　　 ﾉ ❤
                   　 　 │　　 |　|　|
                   　／￣|　　 |　|　|
                   　| (￣ヽ＿_ヽ_)__)
                   　＼二つ\
                    """
            ); // Виведення повідомлення про некоректне введення числа
        }
    }

    private void deleteNodeFromTextField() {
        String input = textField.getText().trim(); // Отримання текстового введення з текстового поля
        if (!input.isEmpty() && isNumeric(input)) { // Перевірка, чи введений рядок не є порожнім і складається лише з чисел
            int value = Integer.parseInt(input); // Парсинг числового значення з рядка
            binaryTreeRoot = deleteNode(binaryTreeRoot, value); // Видалення вузла зі значенням з бінарного дерева
            binaryTreePanel.setRoot(binaryTreeRoot); // Оновлення кореня бінарного дерева на панелі
            textField.setText(""); // Очищення текстового поля
        } else {
            JOptionPane.showMessageDialog(ControlPanel.this,
                    """
                   　　　　　／\\____ / フ
                   　　　　　| 　_　 _ l  Ви ,
                   　 　　　／` ミ＿x ノ  ввели
                   　　 　 /　　　　 |   не вірно
                   　　　 /　 ヽ　　 ﾉ ❤
                   　 　 │　　 |　|　|
                   　／￣|　　 |　|　|
                   　| (￣ヽ＿_ヽ_)__)
                   　＼二つ\
                    """
            ); // Виведення повідомлення про некоректне введення числа
        }
    }
    private BinaryTreeNode insertNode(BinaryTreeNode root, int value) {
        if (root == null) {
            return new BinaryTreeNode(value);
        }

        if (value < root.getValue()) { // Якщо вставлюване значення менше значення поточного вузла
            root.setLeftChild(insertNode(root.getLeftChild(), value)); // Рекурсивно вставляємо вузол в ліве піддерево
        } else if (value > root.getValue()) { // Якщо вставлюване значення більше значення поточного вузла
            root.setRightChild(insertNode(root.getRightChild(), value)); // Рекурсивно вставляємо вузол в праве піддерево
        }

        return root; // Повертаємо початковий корінь бінарного дерева
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private BinaryTreeNode deleteNode(BinaryTreeNode root, int value) {
        if (root == null) {
            return null;
        }

        if (value < root.getValue()) { // Якщо видаляється значення менше значення поточного вузла
            root.setLeftChild(deleteNode(root.getLeftChild(), value)); // Рекурсивно видаляємо вузол з лівого піддерева
        } else if (value > root.getValue()) { // Якщо видаляється значення більше значення поточного вузла
            root.setRightChild(deleteNode(root.getRightChild(), value)); // Рекурсивно видаляємо вузол з правого піддерева
        } else {
            // Знайдено вузол, який потрібно видалити
            if (root.getLeftChild() == null) {
                return root.getRightChild(); // Якщо вузол має лише правого нащадка, повертаємо правого нащадка як новий корінь піддерева
            } else if (root.getRightChild() == null) {
                return root.getLeftChild(); // Якщо вузол має лише лівого нащадка, повертаємо лівого нащадка як новий корінь піддерева
            }

            // Вузол, який потрібно видалити, має двох нащадків
            BinaryTreeNode successor = findMinValueNode(root.getRightChild()); // Знаходимо наступника вузла, який має найменше значення у правому піддереві
            root.getValue(); // Замінюємо значення поточного вузла значенням наступника
            root.setRightChild(deleteNode(root.getRightChild(), successor.getValue())); // Рекурсивно видаляємо наступника з правого піддерева
        }

        return root; // Повертаємо початковий корінь бінарного дерева
    }

    private BinaryTreeNode findMinValueNode(BinaryTreeNode node) {
        BinaryTreeNode current = node;
        while (current.getLeftChild() != null) { // Проходимо вглиб до лівого кінця піддерева для знаходження вузла з найменшим значенням
            current = current.getLeftChild();
        }
        return current; // Повертаємо вузол з найменшим значенням
    }
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str); // Спроба перетворити рядок на ціле число
            return true; // Якщо успішно, повертаємо true
        } catch (NumberFormatException e) {
            return false; // Якщо виникає виключення, рядок не може бути перетворений на ціле число, повертаємо false
        }
    }


    private String inorderTraversal(BinaryTreeNode node) {
        if (node == null) {
            return ""; // Якщо вузол є порожнім, повертаємо порожній рядок
        }

        String left = inorderTraversal(node.getLeftChild()); // Рекурсивний виклик для лівого піддерева
        String current = node.getValue() + " "; // Поточне значення вузла
        String right = inorderTraversal(node.getRightChild()); // Рекурсивний виклик для правого піддерева

        return left + current + right; // Повертаємо об'єднання рядків: лівого піддерева, поточного значення та правого піддерева
    }

    private void inorderChange(BinaryTreeNode node, int x, int y, int xOffset) {
        if (node != null) {
            inorderChange(node.getLeftChild(), x - xOffset, y + BinaryTreePanel.VERTICAL_GAP, xOffset / 2); // Рекурсивний виклик для лівого піддерева
            binaryTreePanel.animateNode(node, x, y); // Анімація вузла

            try {
                Thread.sleep(400); // Затримка на 0.4 секунди
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            inorderChange(node.getRightChild(), x + xOffset, y + BinaryTreePanel.VERTICAL_GAP, xOffset / 2); // Рекурсивний виклик для правого піддерева
        } else {
            animationInProgress = false; // Анімація завершена
            binaryTreePanel.repaint(); // Оновити відображення панелі
        }
    }


    private JButton createButton(String text) {
        JButton button = new JButton(text);// Створення кнопки з вказаним текстом
        button.setFocusPainted(false);// Вимкнення фокусування кнопки при натисканні
        button.setPreferredSize(new Dimension(150, 30));// Встановлення пріоритетного розміру кнопки
        return button; // Повертаємо створену кнопку
    }
}
