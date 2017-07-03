package cn.smlcx.template.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lcx on 2017/7/2 0002.
 */

public class NotePad implements Serializable {
    private int npId;
    private String npContent;
    private Date npAdddate;
    private Date npUpdatedate;
    private int npState;

    public int getNpId() {
        return npId;
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

    public Date getNpAdddate() {
        return npAdddate;
    }

    public void setNpAdddate(Date npAdddate) {
        this.npAdddate = npAdddate;
    }

    public Date getNpUpdatedate() {
        return npUpdatedate;
    }

    public void setNpUpdatedate(Date npUpdatedate) {
        this.npUpdatedate = npUpdatedate;
    }

    public int getNpState() {
        return npState;
    }

    public void setNpState(int npState) {
        this.npState = npState;
    }

    public NotePad(int npId, String npContent, Date npAdddate, Date npUpdatedate, int npState) {
        this.npId = npId;
        this.npContent = npContent;
        this.npAdddate = npAdddate;
        this.npUpdatedate = npUpdatedate;
        this.npState = npState;
    }

    public NotePad() {
    }
}
