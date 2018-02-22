package ddto;

public abstract class AbstractDdtoSet {
    private Integer setId;
    private Integer testCaseId;
    private String description;
    private Boolean skip;
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

    public Boolean getSkip() {
        return skip;
    }

    public void setSkip(Boolean skip) {
        this.skip = skip;
    }
}
