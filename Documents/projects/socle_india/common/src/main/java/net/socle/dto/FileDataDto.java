package net.socle.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 24-03-2022
 */

@Setter
@Getter
@NoArgsConstructor
public class FileDataDto {

    private String fileId;

    private String fileName;

    private String fileContentType;

    private Long fileSizeInBytes;

    private String fileType;

    private String filePreviewUrl;

    private String fileDownloadUrl;

    private String bytes;

    private Boolean isUploadedSuccess = Boolean.TRUE;

    private Boolean isActive = Boolean.TRUE;
}
