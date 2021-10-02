package sunflower.entity;

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
    private String id;

    @Column(name = "sun_username")
    private String username;

    @Column(name = "sun_password")
    private String password;

    @CreatedDate
    @Column(name = "sun_create_time")
    @JsonIgnore
    private Date createdTime;

    @Column(name = "sun_enable")
    @JsonIgnore
    private boolean enable;
}
