
package com.app.retrofittutorial.categories;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data implements Serializable
{

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    private final static long serialVersionUID = -3667179674338133491L;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

}
