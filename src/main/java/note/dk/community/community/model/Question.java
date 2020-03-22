package note.dk.community.community.model;

import lombok.Data;

@Data
public class Question {//model是数据库的表模型
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;

}
