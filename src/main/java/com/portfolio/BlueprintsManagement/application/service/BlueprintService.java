package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.domain.repository.IBlueprintRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.DeleteBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlueprintService {

    private final IBlueprintRepository blueprintRepository;
    private final IArchitecturalDrawingRepository architecturalDrawingRepository;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
    AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

    private String bucketName = "blueprints-management-bucket";
    Region region = Region.AP_NORTHEAST_1;
    S3Client s3 = S3Client.builder().region(region).credentialsProvider(credentialsProvider).build();


    public List<Blueprint> getBlueprintsBySiteId(String siteId) throws NotFoundException {
        if (!blueprintRepository.existBlueprintBySiteId(siteId)) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND_BLUEPRINT_BY_SITE_ID.getMessage());
        }
        return blueprintRepository.getBlueprintsBySiteId(siteId);
    }

    public BlueprintInfo getBlueprintInfo(String id) throws NotFoundException {
        if (!blueprintRepository.existBlueprint(id)) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND_BLUEPRINT_BY_ID.getMessage());
        }
        Blueprint blueprint = blueprintRepository.getBlueprint(id);
        List<ArchitecturalDrawing> architecturalDrawingList = architecturalDrawingRepository.getArchitecturalDrawingsByBlueprintId(id);
        return new BlueprintInfo(blueprint, architecturalDrawingList);
    }

    @Transactional
    public String addBlueprint(AddBlueprintRequest request) throws IOException {
        MultipartFile imageFile = request.getImageFile();
        String imageFileName = imageFile.getOriginalFilename();
//        Resource resource = new ClassPathResource("static/image");
//        Path imageDir = resource.getFile().toPath();
        byte[] content = imageFile.getBytes();
//        Path filePath = imageDir.resolve(Objects.requireNonNull(imageFileName));

        Blueprint blueprint = Blueprint.formBlueprint(request);
        String filePath = blueprint.getSiteId() + "/" + blueprint.getId() + "/" + imageFileName;

        ArchitecturalDrawing architecturalDrawing = ArchitecturalDrawing.formArchitecturalDrawingFromBlueprintRequest(request, blueprint.getId(), filePath);

        blueprintRepository.addBlueprint(blueprint);
        architecturalDrawingRepository.addArchitecturalDrawing(architecturalDrawing);


        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName).key(filePath).build();

        try {
            s3.putObject(objectRequest, RequestBody.fromBytes(content));
        } catch (Exception e) {
            throw new RuntimeException("ファイルのS3へのアップロードに失敗しました", e);
        }

//      Files.write(filePath, content);
        return blueprint.getId();
    }

    @Transactional
    public void updateBlueprint(UpdateBlueprintRequest request) {
        blueprintRepository.updateBlueprint(request);
    }

    @Transactional
    public boolean deleteBlueprint(DeleteBlueprintRequest request) {
        String architecturalDrawingId = request.getId();
        String blueprintId = request.getBlueprintId();
        architecturalDrawingRepository.deleteArchitecturalDrawing(architecturalDrawingId);
        if (architecturalDrawingRepository.existArchitecturalDrawingByBlueprintId(blueprintId)) {
            return false;
        } else {
            blueprintRepository.deleteBlueprint(blueprintId);
            return true;
        }
    }
}
