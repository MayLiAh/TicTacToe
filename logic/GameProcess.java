package TicTacToe.logic;

import TicTacToe.gui.Interface;
import java.util.Random;

public class GameProcess {
    protected static final String EMPTY_FIELD_SIGN = "*";
    protected static String userSign; // символ пользователя
    protected static String computerSign; // символ компьютера
    protected static String computerTurn; // координаты хода компьютера
    protected static String mode; // режим игры
    protected static String [][] field; // массив-игровое поле
    protected static int fieldSize; // размер игрового поля
    protected static int lastIndex; // максимальный индекс игрового поля
    protected static int countToWin; // из скольки клеток состоит линия, достаточная для победы
    protected static Random random;

    // конструктор, инициализирующий основные переменные и массив-игровое поле
    public GameProcess() {
        userSign = Interface.getUserSign();
        computerSign = Interface.getComputerSign();
        mode = Interface.getMode();
        random = new Random();

        fieldSize = Interface.getFieldSize();
        countToWin = fieldSize;
        lastIndex = fieldSize - 1;


        field = new String[fieldSize][fieldSize];

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = EMPTY_FIELD_SIGN;
            }
        }
    }

    // добавление хода игрока в массив-игровое поле
    public static void userTurn(String[] userTurn) {
        int row = Integer.parseInt(userTurn[0]) - 1;
        int col = Integer.parseInt(userTurn[1]) - 1;

        if (field[row][col] == EMPTY_FIELD_SIGN) {
            field[row][col] = userSign;
        }
    }

    // ход компьютера
    public static void computerTurn() {
        int randomChoice = random.nextInt(2); // для средней сложности, 0 - случайный ход, 1 - "умный"
        int row;
        int col;
        String gameMode = Interface.getMode();

        // генерация хода в зависимости от выбранной игроком сложности игры
        if (gameMode == "light" || (gameMode == "middle" && randomChoice == 0)) {
            // легкий режим - компьютер генерирует случайный ход
            // средний режим - если случайное число равно 0, компьютер генерирует случайный ход
            row = random.nextInt(fieldSize);
            col = random.nextInt(fieldSize);

            System.out.println(row + "\n" + col);
            while (!isValid(row, col)) {
                row = random.nextInt(fieldSize);
                col = random.nextInt(fieldSize);
            }
        } else {
            // сложный режим - компьютер генерирует "лучший ход"
            // средний режим - если случайное число равно 1, компьютер генерирует "лучший" ход
            int [] bestTurn = getBestTurn();
            row = bestTurn[0];
            col = bestTurn[1];
        }

        field[row][col] = computerSign;
        computerTurn = (row + 1) + "," + (col + 1);
    }

    // проверка игрового поля на заполненность
    public static boolean isFilled() {
        for(String[] row : field) {
            for(String cell : row) {
                if (cell == EMPTY_FIELD_SIGN) {
                    return false;
                }
            }
        }
        return true;
    }

    // проверка хода на корректность (используется для компьютера)
    public static boolean isValid(int row, int col) {
        if (field[row][col] == EMPTY_FIELD_SIGN) {
                return true;
        }
        return false;
    }

    // получение "лучшего хода"
    private static int[] getBestTurn() {
        int count = 0;
        int occupiedCells = 0;
        int index = 10; // специально задано невозможное в условиях игры значение

        for (int i = 1; i < fieldSize; i++) {
            int computerLineCount = i; // величина, которая отнимается от countToWin чтобы получить минимальную линию, которую стоит достраивать
            int userLineCount = i; // величина, которая отнимается от countToWin чтобы получить минимальную линию, которую стоит перекрывать
            if (i == lastIndex) {
                userLineCount = i - 1;
            }
            // проверка для достраивания линий компьютера

            // проверка диагонали из верхнего левого угла в нижний правый
            for (int j = 0; j < fieldSize; j++) {
                if (field[j][j] == computerSign) {
                    count += 1;
                }
            }
            if (count == countToWin - computerLineCount) {
                for (int j = 0; j < fieldSize; j++) {
                    if (field[j][j] == EMPTY_FIELD_SIGN) {
                        index = j;
                    } else if (field[j][j] == userSign) {
                        occupiedCells += 1;
                    }
                }
                if (occupiedCells == 0 && index != 10) {
                    // если в линии уже есть символ противника, нет смысла достраивать
                    int [] result = {index, index};
                    return result;
                }
            }
            count = 0;
            occupiedCells = 0;
            index = 10;

            // проверка диагонали из нижнего левого угла в верхний правый
            for (int j = 0; j < fieldSize; j ++) {
                if (field[j][lastIndex - j] == computerSign) {
                    count += 1;
                }
            }
            if (count == countToWin - computerLineCount) {
                for (int j = 0; j < fieldSize; j++) {
                    if (field[j][lastIndex - j] == EMPTY_FIELD_SIGN) {
                        index = lastIndex - j;
                    } else if (field[j][lastIndex - j] == userSign) {
                        occupiedCells += 1;
                    }
                }
                if (occupiedCells == 0 && index != 10) {
                    int [] result = {lastIndex - index, index};
                    return result;
                }
            }
            count = 0;
            occupiedCells = 0;
            index = 10;

            // проверка строк
            for (int j = 0; j < fieldSize; j ++) {
                for (int k = 0; k < fieldSize; k++) {
                    if (field[j][k] == computerSign) {
                        count += 1;
                    }
                }
                if (count == countToWin - computerLineCount) {
                    for (int k = 0; k < fieldSize; k++) {
                        if (field[j][k] == EMPTY_FIELD_SIGN) {
                            index = k;
                        } else if (field[j][k] == userSign) {
                            occupiedCells += 1;
                        }
                    }
                    if (occupiedCells == 0 && index != 10) {
                        int [] result = {j, index};
                        return result;
                    }
                }
                count = 0;
                occupiedCells = 0;
                index = 10;
            }

            // проверка столбцов
            for (int j = 0; j < fieldSize; j++) {
                for (int k = 0; k < fieldSize; k++) {
                    if (field[k][j] == computerSign) {
                        count += 1;
                    }
                }
                if (count == countToWin - computerLineCount) {
                    for (int k = 0; k < fieldSize; k++) {
                        if (field[k][j] == EMPTY_FIELD_SIGN) {
                            index = k;
                        } else if (field[k][j] == userSign) {
                            occupiedCells += 1;
                        }
                    }
                    if (occupiedCells == 0 && index != 10) {
                        int [] result = {index, j};
                        return result;
                    }
                }
                count = 0;
                occupiedCells = 0;
                index = 10;
            }

            // Проверка для перекрытия линий пользователя

            // проверка диагонали из верхнего левого угла в нижний правый
            for (int j = 0; j < fieldSize; j++) {
                if (field[j][j] == userSign) {
                    count += 1;
                }
            }
            if (count == countToWin - userLineCount) {
                for (int j = 0; j < fieldSize; j++) {
                    if (field[j][j] == EMPTY_FIELD_SIGN) {
                        index = j;
                    } else if (field[j][j] == computerSign) {
                        occupiedCells += 1;
                    }
                }
                if (occupiedCells == 0 && index != 10) {
                    // если уже есть символ компьютера, нет смысла перекрывать
                    int [] result = {index, index};
                    return result;
                }
            }
            count = 0;
            occupiedCells = 0;
            index = 10;

            // проверка диагонали из нижнего левого угла в верхний правый
            for (int j = 0; j < fieldSize; j ++) {
                if (field[j][lastIndex - j] == userSign) {
                    count += 1;
                }
            }
            if (count == countToWin - userLineCount) {
                for (int j = 0; j < fieldSize; j++) {
                    if (field[j][lastIndex - j] == EMPTY_FIELD_SIGN) {
                        index = lastIndex - j;
                    } else if (field[j][lastIndex - j] == computerSign) {
                        occupiedCells += 1;
                    }
                }
                if (occupiedCells == 0 && index != 10) {
                    int [] result = {lastIndex - index, index};
                    return result;
                }
            }
            count = 0;
            occupiedCells = 0;
            index = 10;

            // проверка строк
            for (int j = 0; j < fieldSize; j ++) {
                for (int k = 0; k < fieldSize; k++) {
                    if (field[j][k] == userSign) {
                        count += 1;
                    }
                }
                if (count == countToWin - userLineCount) {
                    for (int k = 0; k < fieldSize; k++) {
                        if (field[j][k] == EMPTY_FIELD_SIGN) {
                            index = k;
                        } else if (field[j][k] == computerSign) {
                            occupiedCells += 1;
                        }
                    }
                    if (occupiedCells == 0 && index != 10) {
                        int [] result = {j, index};
                        return result;
                    }
                }
                count = 0;
                occupiedCells = 0;
                index = 10;
            }

            // проверка столбцов
            for (int j = 0; j < fieldSize; j++) {
                for (int k = 0; k < fieldSize; k++) {
                    if (field[k][j] == userSign) {
                        count += 1;
                    }
                }
                if (count == countToWin - userLineCount) {
                    for (int k = 0; k < fieldSize; k++) {
                        if (field[k][j] == EMPTY_FIELD_SIGN) {
                            index = k;
                        } else if (field[k][j] == computerSign) {
                            occupiedCells += 1;
                        }
                    }
                    if (occupiedCells == 0 && index != 10) {
                        int [] result = {index, j};
                        return result;
                    }
                }
                count = 0;
                occupiedCells = 0;
                index = 10;
            }
        }
        // если "лучшего" хода нет - проверить, свободен ли центр поля
        if (field[lastIndex / 2][lastIndex / 2] == EMPTY_FIELD_SIGN) {
            int [] result = {lastIndex / 2, lastIndex / 2};
            return result;
        }

        // если "лучшего" хода нет, а центр занят, сгенерировать случайный ход
        int row = random.nextInt(fieldSize);
        int col = random.nextInt(fieldSize);

        while (!isValid(row, col)) {
            row = random.nextInt(fieldSize);
            col = random.nextInt(fieldSize);
        }

        int [] result = {row, col};
        return result;
    }

    // проверка выигрыша игрока с символом gamerSign
    public static boolean isWinner(String gamerSign) {
        int count = 0;

        // проверка диагонали из верхнего левого угла в нижний правый
        for (int i = 0; i < fieldSize; i++) {
            if (field[i][i] == gamerSign) {
                count += 1;
            }
        }
        if (count == countToWin) {
            return true;
        }
        count = 0;

        // проверка диагонали из нижнего левого угла в верхний правый
        for (int i = 0; i < fieldSize; i++) {
            if (field[i][lastIndex - i] == gamerSign) {
                count += 1;
            }
        }
        if (count == countToWin) {
            return true;
        }
        count = 0;

        // проверка строк
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j] == gamerSign) {
                    count += 1;
                }
            }
            if (count == countToWin) {
                return true;
            }
            count = 0;
        }

        // проверка столбцов
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[j][i] == gamerSign) {
                    count += 1;
                }
            }
            if (count == countToWin) {
                return true;
            }
            count = 0;
        }
        return false;
    }

    // получение координат хода компьютера
    public static String getComputerTurn() {
        return computerTurn;
    }
}
