package Controller;

import java.util.concurrent.TimeUnit;

import Controller.Interfaces.iShop;
import Controller.Interfaces.iView;

public class Controller {
    private iShop toyShop;
    private iView view;

    public Controller(iShop toyShop, iView view) {
        this.toyShop = toyShop;
        this.view = view;
    }

    private boolean testFabrics() {
        if (toyShop.getToyFabrics().isEmpty())
            return false;
        return true;
    }

    private boolean testToys() {
        if (toyShop.getQueueToys().isEmpty())
            return false;
        return true;
    }

    private void timeSleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean convertToNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void run() {
        view.titleProgram();
        Command com = Command.NONE;
        boolean nextRun = true;
        view.showMenu();
        while (nextRun) {
            String command = view.prompt(view.inputMessage());
            try {
                com = Command.valueOf(command.toUpperCase());
                switch (com) {
                    case EXIT:
                        nextRun = false;
                        view.exitMessage();
                        break;
                    case MENU:
                        view.showMenu();
                        break;
                    case ADD:
                        if (testFabrics()) {
                            boolean availableOption = false;
                            while (!availableOption) {
                                int choiceFabrics = view.choiceNewToy(toyShop.toyFabrics()) - 1;
                                if (choiceFabrics >= 0 && choiceFabrics < toyShop.getToyFabrics().size()) {
                                    availableOption = true;
                                    boolean inputChancePossible = false;
                                    while (!inputChancePossible) {
                                        int inputChance = view.chanceToy();
                                        if (inputChance > 0 && inputChance <= 100) {
                                            inputChancePossible = true;
                                            boolean create = toyShop.createToy(choiceFabrics, inputChance);
                                            if (create)
                                                view.toyAddedSuccessfully();
                                            else
                                                view.errorPathToDirectoryFabric();
                                            timeSleep(1);
                                        } else {
                                            view.impossibleChance();
                                            timeSleep(1);
                                        }
                                    }
                                } else {
                                    view.errorChoiceNewToy();
                                    timeSleep(1);
                                }
                            }
                        }
                        break;
                    case ASSORTIMENT:
                        if (testToys()) {
                            String nameToy = view.nameToys();
                            if (nameToy.toLowerCase().equals("all*")) {
                                if (toyShop.assortiment().isEmpty())
                                    view.notFound();
                                else
                                    System.out.println(toyShop.assortiment());
                            } else {
                                String copiesToyAssortiment = toyShop.assortiment(nameToy);
                                if (copiesToyAssortiment.isEmpty())
                                    view.notFound();
                                else
                                    System.out.println(copiesToyAssortiment);
                            }
                        } else
                            view.assortimentIsEmpty();
                        break;
                    case GET:
                        if (testToys()) {
                            int countGame = view.countGame();
                            for (int i = 0; i < countGame; i++) {
                                String nameToy = toyShop.toyFabrics();
                                int yourChoice = view.choiceGetToy(nameToy);
                                if (yourChoice > 0 && yourChoice <= toyShop.getToyFabrics().size()) {
                                    String getToy = toyShop.get(toyShop.getToyFabrics().get(yourChoice - 1).getNameCopies());
                                    if (getToy.isEmpty())
                                        view.unluckyWin();
                                    else if (getToy.equals("-1"))
                                        view.outOfAssortiment();
                                    else {
                                        view.congratulations();
                                        System.out.println(getToy);
                                        timeSleep(1);
                                    }
                                    System.out.println();
                                }
                            }
                        } else
                            view.assortimentIsEmpty();
                        timeSleep(1);
                        break;
                    case CHANCE:
                        if (testToys()) {
                            String fabrics = toyShop.toyFabrics();
                            String choiceToy = view.calculateChance(fabrics);
                            String nameToy = new String();
                            String[] fabricsArray = fabrics.split(";");
                            for (int i = 0; i < fabricsArray.length; i++) {
                                String[] fabricElement = fabricsArray[i].split(" - ");
                                for (int j = 0; j < fabricElement[0].length(); j++) {
                                    if (convertToNumber((Character.toString(fabricElement[0].charAt(j)))))
                                        if (choiceToy.equals(Character.toString(fabricElement[0].charAt(j))))
                                            nameToy = fabricElement[1];
                                }
                            }
                            if (!nameToy.isEmpty()) {
                                System.out.println(view.resultChancee(toyShop.getProbabilityCopiesToys(nameToy)));
                            }
                        } else
                            view.assortimentIsEmpty();
                        break;
                    default:
                        view.commandNotFound();
                }
            } catch (Exception e) {
                view.commandNotFound();
            }
        }
    }

}
