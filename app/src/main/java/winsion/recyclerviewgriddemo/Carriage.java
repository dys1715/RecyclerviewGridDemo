package winsion.recyclerviewgriddemo;

/**
 * Created by dys on 2017/6/26 0026.
 */
public class Carriage {
    private int type;
    private String line;
    private String column;
    private String detail;
    private boolean isChecked;

    public Carriage(int type, String detail) {
        this.type = type;
        this.detail = detail;
    }

    public Carriage(int type, String line, String column, String detail,boolean isChecked) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.detail = detail;
        this.isChecked = isChecked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
