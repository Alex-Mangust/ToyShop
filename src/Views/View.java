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
        System.out.print(msg + " ");
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
            return Integer.parseInt(prompt("Задайте вероятность выпадения игрушки(от 1% до 100%):"));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void toyAddedSuccessfully() {
        System.out.println("Игрушка успешно добавлена в ассортимент!");
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
        return prompt("Введите наименование игрушки: ");
    }

    @Override
    public void notFound() {
        System.out.println("Поиск не дал результатов!");
    }
}
