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
                                            toyShop.createToy(choiceFabrics, inputChance);
                                            view.toyAddedSuccessfully();
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
                        if (view.allOrNotAllAssortiment() == 1) {
                            if (toyShop.assortiment().isEmpty())
                                view.notFound();
                            else
                                System.out.println(toyShop.assortiment());
                        } else {
                            if (toyShop.assortiment(toyShop.getCountCopiesToys(view.nameToys())).isEmpty())
                                view.notFound();
                            else
                                System.out.println(toyShop.assortiment(toyShop.getCountCopiesToys(view.nameToys())));
                        }
                        break;
                    case GET:

                        break;
                    case CHANCE:

                        break;
                    default:

                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

}
