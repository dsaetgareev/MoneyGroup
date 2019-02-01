package moneygroup.devufa.ru.moneygroup.model;

public class Settings {

    private String number;

    private String name;

    private String controlQuestion;

    private boolean autoWriteOff;

    private boolean newDebt;

    private boolean chainNotif;

    private boolean diffCur;

    public Settings() {
    }

    public String getNumber() {
        return number;
    }

    public Settings(String number, String name, String controlQuestion, boolean autoWriteOff,
                    boolean newDebt, boolean chainNotif, boolean diffCur) {
        this.number = number;
        this.name = name;
        this.controlQuestion = controlQuestion;
        this.autoWriteOff = autoWriteOff;
        this.newDebt = newDebt;
        this.chainNotif = chainNotif;
        this.diffCur = diffCur;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControlQuestion() {
        return controlQuestion;
    }

    public void setControlQuestion(String controlQuestion) {
        this.controlQuestion = controlQuestion;
    }

    public boolean isAutoWriteOff() {
        return autoWriteOff;
    }

    public void setAutoWriteOff(boolean autoWriteOff) {
        this.autoWriteOff = autoWriteOff;
    }

    public boolean isNewDebt() {
        return newDebt;
    }

    public void setNewDebt(boolean newDebt) {
        this.newDebt = newDebt;
    }

    public boolean isChainNotif() {
        return chainNotif;
    }

    public void setChainNotif(boolean chainNotif) {
        this.chainNotif = chainNotif;
    }

    public boolean isDiffCur() {
        return diffCur;
    }

    public void setDiffCur(boolean diffCur) {
        this.diffCur = diffCur;
    }
}
