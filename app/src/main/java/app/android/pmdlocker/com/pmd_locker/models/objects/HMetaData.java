package app.android.pmdlocker.com.pmd_locker.models.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tuanhoang on 3/27/17.
 */

public class HMetaData implements Serializable {
    @SerializedName("total_page")
    private final Integer totalPage;
    @SerializedName("current_page")
    private final Integer currentPage;
    @SerializedName("total_row")
    private final Integer totalRow;
    @SerializedName("next_link")
    private final String nextLink;

    public HMetaData(Integer totalPage, Integer currentPage, Integer totalRow, String nextLink) {
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.totalRow = totalRow;
        this.nextLink = nextLink;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public String getNextLink() {
        return nextLink;
    }
}
