package cn.smlcx.template.bean;

import java.io.Serializable;

/**
 * Created by lcx on 2017/7/2 0002.
 */

public class NotePad implements Serializable {
    private int npId;
    private String npTitle;
    private String npContent;
    private long npAdddate;
    private long npUpdatedate;
    private int npState;

    public int getNpId() {
        return npId;
    }

    public String getNpTitle() {
        return npTitle;
    }

    public void setNpTitle(String npTitle) {
        this.npTitle = npTitle;
    }

    public void setNpId(int npId) {
        this.npId = npId;
    }

    public String getNpContent() {
        return npContent;
    }

    public void setNpContent(String npContent) {
        this.npContent = npContent;
    }

    public long getNpAdddate() {
        return npAdddate;
    }

    public void setNpAdddate(long npAdddate) {
        this.npAdddate = npAdddate;
    }

    public long getNpUpdatedate() {
        return npUpdatedate;
    }

    public void setNpUpdatedate(long npUpdatedate) {
        this.npUpdatedate = npUpdatedate;
    }

    public int getNpState() {
        return npState;
    }

    public void setNpState(int npState) {
        this.npState = npState;
    }

    public NotePad(int npId,String npTitle, String npContent, long npAdddate, long npUpdatedate, int npState) {
        this.npId = npId;
        this.npTitle = npTitle;
        this.npContent = npContent;
        this.npAdddate = npAdddate;
        this.npUpdatedate = npUpdatedate;
        this.npState = npState;
    }

    public NotePad() {
    }
}
