package TicTacToe.gui;

import TicTacToe.logic.GameProcess;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends JFrame implements ActionListener {
    protected static final String X_SIGN = "X";
    protected static final String ZERO_SIGN = "0";
    protected static String userSign; // символ, которым играет пользователь
    protected static String computerSign; // символ, которым играет компьютер
    protected static int fieldSize; // размер игрового поля
    protected static String mode; // режим игры
    // кнопки выбора размера поля
    protected static JButton sizeThree;
    protected static JButton sizeFive;
    protected static JButton sizeSeven;
    // кнопки выбора символа
    protected static JButton chooseXButton;
    protected static JButton chooseZeroButton;
    // кнопки выбора режима
    protected static JButton chooseLightMode;
    protected static JButton chooseMiddleMode;
    protected static JButton chooseDifficultMode;
    protected static JButton newGameButton; // кнопка для начала новой игры
    protected static JButton [] buttons; // массив с кнопками-клетками игрового поля
    // текстовые поля выбора размера поля, символа, режима
    protected static JLabel chooseFieldSize;
    protected static JLabel chooseSymbol;
    protected static JLabel chooseMode;
    // панели с кнопками выбора размера поля, символа, режима
    protected static JPanel sizeButtons;
    protected static JPanel symbolsButtons;
    protected static JPanel modesButtons;
    // панели выбора размера поля, символа, режима, новой игры и игрового поля
    protected static JPanel size;
    protected static JPanel symbols;
    protected static JPanel modes;
    protected static JPanel newGame;
    protected static JPanel field;
    protected static JPanel fullField;
    protected static JFrame frame; // окно
    protected static Color backgroundColor; // цвет фона
    protected static Font font; // стиль текста для игровых символов
    protected static Color textColor; // цвет текста

    public Interface() {
        // установка цвета текста для всех Label
        textColor = new Color(81, 52, 39);
        UIManager.getLookAndFeelDefaults().put("Label.foreground", textColor);
        // инициализация кнопок, панелей и начального интерфейса
        initButtons();
        initChoosePanels();
        buildInterface();
    }

    // метод, инициализирующий кнопки
    private void initButtons() {
        sizeThree = new BrownButton("3 x 3");
        sizeThree.setActionCommand("3");
        sizeThree.addActionListener(this::actionChooseSize);
        sizeThree.setFocusPainted(false);

        sizeFive = new BrownButton("5 x 5");
        sizeFive.setActionCommand("5");
        sizeFive.addActionListener(this::actionChooseSize);
        sizeFive.setFocusPainted(false);

        sizeSeven = new BrownButton("7 x 7");
        sizeSeven.setActionCommand("7");
        sizeSeven.addActionListener(this::actionChooseSize);
        sizeSeven.setFocusPainted(false);

        chooseXButton = new BrownButton("X");
        chooseXButton.setActionCommand("X");
        chooseXButton.addActionListener(this::actionChooseSymbol);
        chooseXButton.setFocusPainted(false);

        chooseZeroButton = new BrownButton("0");
        chooseZeroButton.setActionCommand("0");
        chooseZeroButton.addActionListener(this::actionChooseSymbol);
        chooseZeroButton.setFocusPainted(false);

        chooseLightMode = new BrownButton("Легкий");
        chooseLightMode.setActionCommand("light");
        chooseLightMode.addActionListener(this::actionChooseMode);
        chooseLightMode.setFocusPainted(false);

        chooseMiddleMode = new BrownButton("Средний");
        chooseMiddleMode.setActionCommand("middle");
        chooseMiddleMode.addActionListener(this::actionChooseMode);
        chooseMiddleMode.setFocusPainted(false);

        chooseDifficultMode = new BrownButton("Сложный");
        chooseDifficultMode.setActionCommand("difficult");
        chooseDifficultMode.addActionListener(this::actionChooseMode);
        chooseDifficultMode.setFocusPainted(false);

        newGameButton = new BrownButton("Новая игра");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(this::actionStartNewGame);
        newGameButton.setFocusPainted(false);
    }

    // метод, инициализирующий кнопки-клетки игрового поля
    private void initGameButtons() {
        font = new Font("Century Gothic", Font.BOLD, 14);
        buttons = new JButton[fieldSize * fieldSize];
        BevelBorder border = new BevelBorder(BevelBorder.LOWERED, new Color(204, 169, 96), new Color(68, 44, 33), new Color(103, 81, 49), new Color(242, 223, 176));
        // указатели на строку и столбец каждой кнопки
        int row = 1;
        int col = 1;

        for (int i = 0; i < fieldSize * fieldSize; i++) {
            buttons[i] = new BrownButton();
            buttons[i].setFont(font);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createLineBorder(new Color(142, 86, 57), 4)));
            String command = row + "," + col; // указатель на координаты кнопки, потребуется, чтобы отметить ход в массиве field класса GameProcess
            buttons[i].setActionCommand(command);
            buttons[i].addActionListener(this::actionPerformed);

            // проверка, нужно ли увеличить значение строки или столбца
            if (col % fieldSize == 0) {
                col = 1;
                row ++;
            } else {
                col++;
            }
        }
    }

    // метод, инициализирующий панели выбора размера поля, символа и режима, начала новой игры
    private static void initChoosePanels() {
        backgroundColor = new Color(204, 169, 96);
        GridLayout sizeButtonsLayout = new GridLayout(1, 3, 5, 5);
        GridLayout symbolsButtonsLayout = new GridLayout(1, 2, 5, 5);
        GridLayout modesButtonsLayout = new GridLayout(1, 3, 5, 5);

        chooseFieldSize = new JLabel("Выберите размер поля:");
        chooseFieldSize.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeButtons = new JPanel();
        sizeButtons.setBackground(backgroundColor);
        sizeButtons.setMaximumSize(new Dimension(300, 50));
        sizeButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeButtons.setLayout(sizeButtonsLayout);
        sizeButtons.add(sizeThree);
        sizeButtons.add(sizeFive);
        sizeButtons.add(sizeSeven);

        chooseSymbol = new JLabel("Выберите символ для игры:");
        chooseSymbol.setAlignmentX(Component.CENTER_ALIGNMENT);
        symbolsButtons = new JPanel();
        symbolsButtons.setBackground(backgroundColor);
        symbolsButtons.setMaximumSize(new Dimension(200, 50));
        symbolsButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        symbolsButtons.setLayout(symbolsButtonsLayout);
        symbolsButtons.add(chooseXButton);
        symbolsButtons.add(chooseZeroButton);

        chooseMode = new JLabel("Выберите сложность игры:");
        chooseMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        modesButtons = new JPanel();
        modesButtons.setBackground(backgroundColor);
        modesButtons.setMaximumSize(new Dimension(400, 50));
        symbolsButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        modesButtons.setLayout(modesButtonsLayout);
        modesButtons.add(chooseLightMode);
        modesButtons.add(chooseMiddleMode);
        modesButtons.add(chooseDifficultMode);

        size = new JPanel();
        size.setBackground(backgroundColor);
        size.setLayout(new BoxLayout(size, BoxLayout.Y_AXIS));
        size.add(Box.createVerticalStrut(15));
        size.add(chooseFieldSize);
        size.add(Box.createVerticalStrut(15));
        size.add(sizeButtons);

        symbols = new JPanel();
        symbols.setBackground(backgroundColor);
        symbols.setLayout(new BoxLayout(symbols, BoxLayout.Y_AXIS));
        symbols.add(Box.createVerticalStrut(15));
        symbols.add(chooseSymbol);
        symbols.add(Box.createVerticalStrut(15));
        symbols.add(symbolsButtons);

        modes = new JPanel();
        modes.setBackground(backgroundColor);
        modes.setLayout(new BoxLayout(modes, BoxLayout.Y_AXIS));
        modes.add(Box.createVerticalStrut(15));
        modes.add(chooseMode);
        modes.add(Box.createVerticalStrut(15));
        modes.add(modesButtons);

        newGame = new JPanel();
        newGame.setBackground(backgroundColor);
        newGame.setPreferredSize(new Dimension(400, 100));
        newGame.setLayout(new BoxLayout(newGame, BoxLayout.Y_AXIS));
    }

    // метод, инициализирующий игровое поле
    private static void initField() {
        GridLayout fieldLayout = new GridLayout(fieldSize, fieldSize, 0, 0);
        field = new JPanel();
        field.setBackground(new Color(148, 115, 70));
        field.setPreferredSize(new Dimension(400, 400));
        field.setLayout(fieldLayout);

        // добавление кнопок на поле
        for (int i = 0; i < buttons.length; i++) {
            field.add(buttons[i]);
        }
    }

    // отрисовка первого интерфейса выбора размера поля
    private static void buildSizeChooseInterface() {
        frame.getContentPane().add(size);
    }

    // отрисовка интерфейса выбора символа
    private static void buildSymbolChooseInterface() {
        // удаление прежнего содержимого фрейма и добавление нового
        frame.getContentPane().removeAll();
        frame.getContentPane().add(symbols);
        frame.validate();
        frame.repaint();
    }

    // отрисовка интерфейса выбора режима
    private static void buildModeChooseInterface() {
        // удаление прежнего содержимого фрейма и добавление нового
        frame.getContentPane().removeAll();
        frame.getContentPane().add(modes);
        frame.validate();
        frame.repaint();
    }

    // отрисовка игрового поля
    private static void buildField() {
        initField();
        fullField = new JPanel();
        UIManager.getDefaults().put("Button.disabledText", BrownButton.getButtonTextColor()); // цвет текста неактивной кнопки
        fullField.setBackground(backgroundColor);
        fullField.setPreferredSize(new Dimension(400, 500));
        fullField.setLayout(new BoxLayout(fullField, BoxLayout.Y_AXIS));
        fullField.add(field);
        newGame.removeAll(); // удаление содержимого панели новой игры
        fullField.add(newGame);
        new GameProcess(); // обновление данных класса GameProcess
        // удаление прежнего содержимого фрейма и добавление нового
        frame.getContentPane().removeAll();
        frame.getContentPane().add(fullField);
        frame.validate();
        frame.repaint();
    }

    // создание или обновление фрейма
    private static void buildInterface() {
        // если приложение только запущено
        if (frame == null) {
            frame = new JFrame("TicTacToe");
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(400, 500));
            buildSizeChooseInterface();
            frame.pack();
            frame.setVisible(true);
        } else {
            // если начата новая игра
            frame.getContentPane().removeAll();
            buildSizeChooseInterface();
            frame.validate();
            frame.repaint();
        }
    }

    // добавление панели с уведомлением о выигрыше/проигрыше\ничьей и предложением начать новую игру
    private static void addNewGamePanel(String text) {
        newGame.removeAll();
        newGame.add(Box.createVerticalStrut(15));
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.add(label);
        newGame.add(Box.createVerticalStrut(15));
        newGame.add(newGameButton);
        fullField.add(newGame);
    }

    // слушатель событий кнопок выбора размера поля
    public void actionChooseSize(ActionEvent e) {
        fieldSize = Integer.parseInt(e.getActionCommand());
        buildSymbolChooseInterface(); // после выбора поля нужно отрисовать интерфейс выбора символа
    }

    // слушатель событий кнопок выбора символа
    public void actionChooseSymbol(ActionEvent e) {
        userSign = e.getActionCommand();

        if (userSign == X_SIGN) {
            computerSign = ZERO_SIGN;
        } else {
            computerSign = X_SIGN;
        }

        buildModeChooseInterface(); // после выбора символа нужно отрисовать интерфейс выбора режима
    }

    // слушатель событий кнопок выбора режима
    public void actionChooseMode(ActionEvent e) {
        mode = e.getActionCommand();
        initGameButtons();
        buildField(); // после выбора режима нужно отрисовать игровое поле
    }

    // слушатель событий кнопки начала новой игры, при клике по ней происходит сброс присвоенных значений и отрисовываетя поле выбора размера поля
    public void actionStartNewGame(ActionEvent e) {
        buildInterface();
    }

    // слушатель событий кнопок-клеток игрового поля
    public void actionPerformed(ActionEvent e) {
        String buttonCommand = e.getActionCommand(); // координаты хода пользователя

        for (JButton button : buttons) {
            // поиск кнопки с такими координатами и проверка ее на пустоту
            if (button.getActionCommand() == buttonCommand && button.getText() == "") {
                // отображение в кнопке символа игрока, добавление хода в массив field класса GameProcess
                button.setText(userSign);
                GameProcess.userTurn(buttonCommand.split(","));
                button.setEnabled(false); // если ход сделан, кнопка становится неактивной
            }
        }


        if (GameProcess.isWinner(userSign)) { // проверка победы пользователя
            for (JButton button : buttons) {
                button.setEnabled(false);
            }
            addNewGamePanel("Вы победили!");
        } else if (GameProcess.isFilled()) { // проверка заполнения поля
            for (JButton button : buttons) {
                button.setEnabled(false);
            }
            addNewGamePanel("Ничья!");
        } else {
            // ход компьютера и получение координат
            GameProcess.computerTurn();
            String computerButtonCommand = GameProcess.getComputerTurn();

            for (JButton button : buttons) {
                if (button.getActionCommand().contains(computerButtonCommand)) {
                    // добавление символа компьютера на кнопку с соответствующими координатами
                    button.setText(computerSign);
                    button.setEnabled(false);
                }
            }

            if (GameProcess.isWinner(computerSign)) { // проверка выигрыша компьютера
                for (JButton button : buttons) {
                    button.setEnabled(false);
                }
                addNewGamePanel("Вы проиграли!");
            } else if (GameProcess.isFilled()) { // проверка заполненности поля
                for (JButton button : buttons) {
                    button.setEnabled(false);
                }
                addNewGamePanel("Ничья!");
            }
        }
    }

    // получение размера поля
    public static int getFieldSize() {
        return fieldSize;
    }

    // получение режима игры
    public static String getMode() {
        return mode;
    }

    // получение символа пользователя
    public static String getUserSign() {
        return userSign;
    }

    // получение символа компьютера
    public static String getComputerSign() {
        return computerSign;
    }
}
