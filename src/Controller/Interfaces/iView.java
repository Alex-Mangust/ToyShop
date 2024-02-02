package Controller.Interfaces;

public interface iView {
    void titleProgram();
    String prompt(String msg);
    String inputMessage();
    void exitMessage();
    void showMenu();
    int choiceNewToy(String msg);
    void errorChoiceNewToy();
    int chanceToy();
    void toyAddedSuccessfully();
    void errorPathToDirectoryFabric();
    void impossibleChance();
    int allOrNotAllAssortiment();
    String nameToys();
    void notFound();
    int countGame();
    int choiceGetToy(String msg);
    void congratulations();
    void unluckyWin();
    void outOfAssortiment();
    String calculateChance(String msg);
    String resultChancee(String msg);
    void assortimentIsEmpty();
    void commandNotFound();
}
