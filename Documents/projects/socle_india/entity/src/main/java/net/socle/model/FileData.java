package net.socle.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.socle.enums.FileType;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "file_data")
public class FileData extends BaseEntity{

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_content_type")
    private String fileContentType;

    @Column(name = "file_size", nullable = false, columnDefinition = "int default 0")
    private Long fileSizeInBytes = 0L;

    @Enumerated(EnumType.STRING)
    @Column(length = 40, columnDefinition = "varchar(40) default 'FILE'")
    private FileType fileType = FileType.FILE;
}
