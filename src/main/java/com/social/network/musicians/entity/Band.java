package com.social.network.musicians.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import springfox.documentation.annotations.ApiIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Band")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiIgnore
@EqualsAndHashCode
public class Band {

    private static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy="bands")
    private Set<Artist> artists = new HashSet<>();

    @OneToMany(mappedBy="artist")
    private Set<ProfileMessage> profileMessages = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATEFORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @UpdateTimestamp
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATEFORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

}
