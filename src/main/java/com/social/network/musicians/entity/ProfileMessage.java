package com.social.network.musicians.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import springfox.documentation.annotations.ApiIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Profile_Message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiIgnore
public class ProfileMessage {

    private static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    private Band band;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private Artist artist;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATEFORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @UpdateTimestamp
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATEFORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

}
