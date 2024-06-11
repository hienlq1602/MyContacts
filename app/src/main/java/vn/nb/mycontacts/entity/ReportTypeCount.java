package vn.nb.mycontacts.entity;

public class ReportTypeCount {
    private int type;
    private int totalReports;

    public ReportTypeCount(int type, int totalReports) {
        this.type = type;
        this.totalReports = totalReports;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotalReports() {
        return totalReports;
    }

    public void setTotalReports(int totalReports) {
        this.totalReports = totalReports;
    }
}

