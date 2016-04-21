package ddto;

/**
 * класс с постоянными и необходимыми полями в дата провайдере
 * сюда входят те поля что присутствуют в любом тесте
 */
public abstract class AbstractDdtoSet {
    private Integer setId;
    private Integer testCaseId;
    private String description;

// true - not removed in precondition
    private boolean notRemoved;

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public Integer getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(Integer testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNotRemoved() {
        return notRemoved;
    }

    public boolean isRemoved() {
        return !notRemoved;
    }

    public void setNotRemoved(boolean notRemoved) {
        this.notRemoved = notRemoved;
    }
}
