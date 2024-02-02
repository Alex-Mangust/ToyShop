package Views;

import java.util.Scanner;

import Controller.Interfaces.iView;

public class View implements iView {
    private boolean exit = false;

    @Override
    public void titleProgram() {
        System.out.println("Магазин игрушек");
    }

    @Override
    public String prompt(String msg) {
        Scanner in = new Scanner(System.in);
        System.out.print(msg);
        if (exit) in.close();
        return in.nextLine();
    }

    @Override
    public String inputMessage() {
        return "Выберите команду: ";
    }
    
    @Override
    public void exitMessage() {
        System.out.println("Программа завершена!");
    }

    @Override
    public void showMenu() {
        System.out.println("\nСписок команд:\nMENU - вызвать список команд\nADD - Добавить новую игрушку\nASSORTIMENT - Посмотреть имеющийся ассортимент игрушек\nGET - Разыграть игрушку\nCHANCE - Узнать шанс выпадения игрушки\nEXIT - Завершить работу программы\n");
    }

    @Override
    public int choiceNewToy(String msg) {
        System.out.println("Выберите какую игрушку вы хотите добавить: ");
        try {
            return Integer.parseInt(prompt(msg));
            
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void errorChoiceNewToy() {
        System.out.println("Пожалуйста выберите один из представленных вариантов!");
    }

    @Override
    public int chanceToy() {
        try {
            return Integer.parseInt(prompt("Задайте вероятность выпадения игрушки(от 1% до 100%): "));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void toyAddedSuccessfully() {
        System.out.println("Игрушка успешно добавлена в ассортимент!");
    }

    @Override
    public void errorPathToDirectoryFabric() {
        System.out.println("У вас нет моделий для добавления игрушек! Пожалуйста убедитесь, что путь к моделям игрушек указан верно!");
    }

    @Override
    public void impossibleChance() {
        System.out.println("Введена неверная вероятность! Пожалуйста, введите вероятность от 1% до 100%!");
    }

    @Override
    public int allOrNotAllAssortiment() {
        try {
            return Integer.parseInt(prompt("Распечатать весь ассортимент или определенный вид игрушки? (1/2): "));
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public String nameToys() {
        return prompt("Введите наименование игрушки (Чтобы распечатать весь ассортимент введите all*): ");
    }

    @Override
    public void notFound() {
        System.out.println("Поиск не дал результатов!");
    }

    @Override
    public int countGame() {
        try {
            return Integer.parseInt(prompt("Сколько раз вы хотите сыграть: "));
        } catch (Exception e) {
            return 1;
        }
    }
    
    @Override
    public int choiceGetToy(String msg) {
        System.out.println("Выберите какую игрушку вы хотите получить: ");
        try {
            return Integer.parseInt(prompt(msg));
            
        } catch (Exception e) {
            return -1;
        }
    }


    @Override
    public void congratulations() {
        System.out.println("\n\nПоздравляю, вы выиграли!\n");
    }

    @Override
    public void unluckyWin() {
        System.out.println("К сожалению выбить игрушку не удалось.");
    }

    @Override
    public void outOfAssortiment() {
        System.out.println("К сожалению выбранной вами игрушки нет в ассортименте.");
    }

    @Override
    public String calculateChance(String msg) {
        System.out.println("Для какой игрушки вы хотите узнать вероятность выпадения?\nВыберите один вариант:");
        return prompt(msg);
    }

    @Override
    public String resultChancee(String msg) {
        String[] resultChance = msg.split(" = ");
        return String.format("Вероястность выпада игрушки %s - %s%%", resultChance[0], resultChance[1]);
    }

    @Override
    public void assortimentIsEmpty() {
        System.out.println("Ассортимент пуст!");
    }

    @Override
    public void commandNotFound() {
        System.out.println("Данной команды нет в списке команд!\nВведите \"menu\", чтобы посмотреть список команд для работы с программой!");
    }

    
}
