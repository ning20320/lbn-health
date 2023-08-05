package lbn.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageSelect <T> {
    private List<T> rows;

    private Long total;
}
