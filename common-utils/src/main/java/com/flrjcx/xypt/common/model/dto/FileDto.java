package com.flrjcx.xypt.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author aftermath
 * @date 2022-11-27 14:02:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto implements Serializable {
    private static final long serialVersionUID = 352304476910270167L;

    private MultipartFile file;
    private String headPath;
    private String path;
}
