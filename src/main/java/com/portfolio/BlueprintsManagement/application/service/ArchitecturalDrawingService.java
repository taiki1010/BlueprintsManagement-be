package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.FailedToPutObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class ArchitecturalDrawingService {

    private final IArchitecturalDrawingRepository architecturalDrawingRepository;

    private final S3Client s3;
    private final String bucketName;

    @Autowired
    public ArchitecturalDrawingService(
            IArchitecturalDrawingRepository architecturalDrawingRepository,
            S3Client s3,
            @Value("${s3.bucket.name}") String bucketName
    ) {
        this.architecturalDrawingRepository = architecturalDrawingRepository;
        this.s3 = s3;
        this.bucketName = bucketName;
    }

    /**
     * 図面画像を追加します。
     * 画像データをS3にアップロードし、画像ファイルパスをMySQLに保存します。
     *
     * @param request 図面画像リクエスト（図面ID, 作成日, 図面画像データ）
     * @return 図面画像
     * @throws IOException アップロードに失敗した場合400エラーメッセージ
     */
    @Transactional
    public ArchitecturalDrawing addArchitecturalDrawing(ArchitecturalDrawingRequest request) throws IOException {

        MultipartFile imageFile = request.getImageFile();
        String imageFileName = imageFile.getOriginalFilename();
        byte[] content = imageFile.getBytes();
        String filePath = request.getBlueprintId() + "/" + imageFileName;

        ArchitecturalDrawing architecturalDrawing = ArchitecturalDrawing.formArchitecturalDrawing(request, filePath);

        architecturalDrawingRepository.addArchitecturalDrawing(architecturalDrawing);

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName).key(filePath).build();

        try {
            s3.putObject(objectRequest, RequestBody.fromBytes(content));
        } catch (Exception e) {
            throw new FailedToPutObjectException(ErrorMessage.FAILED_TO_PUT_OBJECT.getMessage());
        }

        return architecturalDrawing;

    }
}
