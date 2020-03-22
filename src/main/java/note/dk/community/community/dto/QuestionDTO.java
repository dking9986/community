package note.dk.community.community.dto;

import lombok.Data;
import note.dk.community.community.model.User;

@Data
public class QuestionDTO {
        private Integer id;
        private String title;
        private String description;
        private String tag;
        private Long gmtCreate;
        private Long gmtModified;
        private String creator;
        private Integer viewCount;
        private Integer commentCount;
        private Integer likeCount;
        private User user;


}
