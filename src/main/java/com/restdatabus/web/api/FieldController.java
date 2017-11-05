package com.restdatabus.web.api;

import com.restdatabus.model.data.Field;
import com.restdatabus.model.data.dvo.EntityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FieldController {

    private static final Logger LOG = LoggerFactory.getLogger(FieldController.class);


    @RequestMapping(
            value = "/admin/todo", // TODO
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<FileValueData> uploadFiles(
            @PathVariable(value = "id") Long entityId,
            @PathVariable(value = "name") String fieldname,
            @RequestParam(value = "type") String entityType,
            @RequestParam("file") MultipartFile multipartFiles[]
    ) {

        LOG.debug("create - {}/{}/{} -> {} files", entityType, entityId, fieldname, multipartFiles.length);

        try {

            for(MultipartFile multipartFile: multipartFiles) {

                if (!multipartFile.isEmpty() && multipartFile.getBytes() != null && multipartFile.getBytes().length != 0) {

                    String mimeType = multipartFile.getContentType();

                    String filename = multipartFile.getOriginalFilename();
                    byte[] bytes = multipartFile.getBytes();

                    LOG.debug("filename: {}, mimeType: {}", filename, mimeType);

                }
            }

            FieldData field = new FieldData();
            field.name = "toto";
            field.value = 9;
            FileValueData data = new FileValueData();
            data.field = field;
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        catch (Exception e) {
            LOG.error("Error handling file upload", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class FileValueData {
        FieldData field;

        public FieldData getField() {
            return field;
        }

        public void setField(FieldData field) {
            this.field = field;
        }

        public Object[] getFiles() {
            return files;
        }

        public void setFiles(Object[] files) {
            this.files = files;
        }

        Object[] files;
    }
    public static class FieldData {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        Object value;
    }
}
