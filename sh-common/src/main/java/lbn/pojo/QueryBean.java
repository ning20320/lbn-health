package lbn.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryBean{
    private Integer currentPage;
    private Integer pageSize;
    private String queryString;
}
