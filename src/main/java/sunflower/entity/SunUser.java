package sunflower.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
@Entity
@Table(name = "sunflower_user")
public class SunUser {

    @Id
    @Column(name = "sun_id")
    @JsonIgnore
    private String id;

    @Column(name = "sun_username")
    private String username;

    @Column(name = "sun_password")
    @JsonIgnore
    private String password;

    private String nickName;

    @CreatedDate
    @Column(name = "sun_create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdTime;

    @Column(name = "sun_enable")
    @JsonIgnore
    private boolean enable;

    private String avatar_url;
}
