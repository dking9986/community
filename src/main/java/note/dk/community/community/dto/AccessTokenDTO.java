package note.dk.community.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {//dto是前端网络模型
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
