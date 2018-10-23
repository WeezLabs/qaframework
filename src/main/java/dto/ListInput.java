package dto;

public class ListInput {
    private String title;
    private String notes;
    private Integer sort_type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getSort_type() {
        return sort_type;
    }

    public void setSort_type(Integer sort_type) {
        this.sort_type = sort_type;
    }
}
