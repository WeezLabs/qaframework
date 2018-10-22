package ddto;

/**
 * Abstract class with mandatory fields that must be included in test input items contents returned by data providers
 */
@SuppressWarnings("ALL")
public abstract class AbstractDdtoSet {
    private Integer setId;
    private Integer testCaseId;
    private String description;
    private Boolean skip;

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

    public Boolean getSkip() {
        return skip;
    }

    public void setSkip(Boolean skip) {
        this.skip = skip;
    }
}
