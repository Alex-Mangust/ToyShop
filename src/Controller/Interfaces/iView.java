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
    void impossibleChance();
    int allOrNotAllAssortiment();
    String nameToys();
    void notFound();
}
