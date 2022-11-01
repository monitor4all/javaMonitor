package cn.monitor4all.monitor4java.domain;

public abstract class TimerWrapper {

    private Long startUnixTime;
    private Long finishUnixTime;
    private Long timeCosts;

    public void startTimer() {
        this.startUnixTime = System.currentTimeMillis();
    }

    public void finishTimer() {
        this.finishUnixTime = System.currentTimeMillis();
        if (startUnixTime != null) {
            timeCosts = finishUnixTime - startUnixTime;
        }
    }

    public Long getTimeCosts() {
        return timeCosts;
    }

    public long getStartUnixTime() {
        return startUnixTime;
    }

    public void setStartUnixTime(long startUnixTime) {
        this.startUnixTime = startUnixTime;
    }

    public Long getFinishUnixTime() {
        return finishUnixTime;
    }

    public void setFinishUnixTime(Long finishUnixTime) {
        this.finishUnixTime = finishUnixTime;
    }
}
