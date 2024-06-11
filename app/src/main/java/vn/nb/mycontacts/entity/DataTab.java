package vn.nb.mycontacts.entity;

public class DataTab {
    private String nameRes;
    private int iconRes;
    private boolean isSelected = false;

    public DataTab(String nameRes, int iconRes) {
        this.nameRes = nameRes;
        this.iconRes = iconRes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public DataTab() {
    }

    public String getNameRes() {
        return nameRes;
    }

    public void setNameRes(String nameRes) {
        this.nameRes = nameRes;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
