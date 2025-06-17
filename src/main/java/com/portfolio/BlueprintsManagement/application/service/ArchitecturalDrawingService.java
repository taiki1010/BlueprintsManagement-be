package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ArchitecturalDrawingService {

    private final IArchitecturalDrawingRepository architecturalDrawingRepository;

    @Value("${s3.bucket.name}")
    private String bucketName;
    Region region = Region.AP_NORTHEAST_1;
    S3Client s3 = S3Client.builder().region(region).build();

    @Transactional
    public ArchitecturalDrawing addArchitecturalDrawing(ArchitecturalDrawingRequest request) throws IOException {

        MultipartFile imageFile = request.getImageFile();
        String imageFileName = imageFile.getOriginalFilename();
//        Resource resource = new ClassPathResource("static/image");
//        Path imageDir = resource.getFile().toPath();
        byte[] content = imageFile.getBytes();
//        Path filePath = imageDir.resolve(Objects.requireNonNull(imageFileName));
        String filePath = request.getSiteId() + "/" + request.getBlueprintId() + "/" + imageFileName;

        ArchitecturalDrawing architecturalDrawing = ArchitecturalDrawing.formArchitecturalDrawing(request, filePath);

        architecturalDrawingRepository.addArchitecturalDrawing(architecturalDrawing);

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName).key(filePath).build();

        try {
            s3.putObject(objectRequest, RequestBody.fromBytes(content));
        } catch (Exception e) {
            throw new RuntimeException("ファイルのS3へのアップロードに失敗しました", e);
        }

//        Files.write(filePath, content);

        return architecturalDrawing;

    }
}
