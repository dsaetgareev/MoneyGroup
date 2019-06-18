package moneygroup.devufa.ru.moneygroup.model.dto;

public class SettingDto {
    private String name;
    private boolean autoCancel;
    private boolean newDebtNotification;
    private boolean newCycleNotification;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAutoCancel() {
        return autoCancel;
    }

    public void setAutoCancel(boolean autoCancel) {
        this.autoCancel = autoCancel;
    }

    public boolean isNewDebtNotification() {
        return newDebtNotification;
    }

    public void setNewDebtNotification(boolean newDebtNotification) {
        this.newDebtNotification = newDebtNotification;
    }

    public boolean isNewCycleNotification() {
        return newCycleNotification;
    }

    public void setNewCycleNotification(boolean newCycleNotification) {
        this.newCycleNotification = newCycleNotification;
    }
}
