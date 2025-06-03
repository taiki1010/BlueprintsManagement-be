package Portfolio.BlueprintsManagement.presentation.exception.validation.fileValidation;

import Portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return isValidFileSize(file, context) && isValidFileType(file, context);
    }

    public boolean isValidFileSize(MultipartFile file, ConstraintValidatorContext context) {
        boolean isFileSizeAcceptable = file.getSize() < 5 * 1024 * 1024; // 5MBまで対応可能
        if (!isFileSizeAcceptable) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.NOT_FILE_SIZE_ACCEPTABLE.getMessage())
                    .addConstraintViolation();
        }
        return isFileSizeAcceptable;
    }

    public boolean isValidFileType(MultipartFile file, ConstraintValidatorContext context) {
        String contentType = file.getContentType();
        boolean isFileTypeAcceptable = contentType.startsWith("image/");
        if(!isFileTypeAcceptable) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.NOT_FILE_TYPE_ACCEPTABLE.getMessage())
                    .addConstraintViolation();
        }
        return isFileTypeAcceptable;
    }

}
