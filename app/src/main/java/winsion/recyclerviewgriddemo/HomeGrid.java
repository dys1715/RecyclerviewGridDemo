package winsion.recyclerviewgriddemo;

/**
 * Created by dys on 2017/6/29 0029.
 */
public class HomeGrid {
    private int type;
    private int iconPicId;
    private String iconName;
    private int iconBgColor;

    public HomeGrid(int type, int iconPicId, String iconName, int iconBgColor) {
        this.type = type;
        this.iconPicId = iconPicId;
        this.iconName = iconName;
        this.iconBgColor = iconBgColor;
    }

    public int getType() {
        return type;
    }

    public int getIconPicId() {
        return iconPicId;
    }

    public String getIconName() {
        return iconName;
    }

    public int getIconBgColor() {
        return iconBgColor;
    }
}
