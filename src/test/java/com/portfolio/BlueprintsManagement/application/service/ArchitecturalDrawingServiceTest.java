package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.FailedToPutObjectException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArchitecturalDrawingServiceTest {

    private ArchitecturalDrawingService sut;

    @Mock
    private IArchitecturalDrawingRepository architecturalDrawingRepository;

    @Mock
    private S3Client s3;

    private String blueprintId;
    private String architecturalDrawingId;
    private byte[] dummyImageFile;

    private ArchitecturalDrawing architecturalDrawing;
    private ArchitecturalDrawingRequest architecturalDrawingRequest;

    private MockedStatic<ArchitecturalDrawing> architecturalDrawingMockedStatic;

    @BeforeEach
    public void before() {
        sut = new ArchitecturalDrawingService(architecturalDrawingRepository, s3, "test-bucket");
        createInitialSampleDate();

        architecturalDrawingMockedStatic = mockStatic(ArchitecturalDrawing.class);
        architecturalDrawingMockedStatic.when(() -> ArchitecturalDrawing.formArchitecturalDrawing(any(), any())).thenReturn(architecturalDrawing);
    }

    @AfterEach
    public void after() {
        architecturalDrawingMockedStatic.close();
    }

    public void createInitialSampleDate() {
        blueprintId = "10000000-0000-1000-8000-000000000001";
        architecturalDrawingId = "11000000-0000-1000-8000-000000000001";
        dummyImageFile = new byte[100];
        MockMultipartFile mockImage = new MockMultipartFile("imageFile", "dummy.png", "image/png", dummyImageFile);

        architecturalDrawing = new ArchitecturalDrawing(architecturalDrawingId, blueprintId, "2025-01-01", "image/hoge.png");
        architecturalDrawingRequest = new ArchitecturalDrawingRequest(blueprintId, "2025-01-01", mockImage);
    }

    @Nested
    class addArchitecturalDrawingTest {

        @Test
        void 図面画像情報の追加＿リポジトリとputObjectメソッドが実行されること() throws IOException, FailedToPutObjectException {
            sut.addArchitecturalDrawing(architecturalDrawingRequest);

            verify(s3).putObject(any(PutObjectRequest.class), any(RequestBody.class));
            verify(architecturalDrawingRepository, times(1)).addArchitecturalDrawing(architecturalDrawing);
        }

        @Test
        void 図面画像情報の追加＿putObjectメソッドが失敗したとき例外処理が発生すること() throws IOException, FailedToPutObjectException {
            when(s3.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenThrow(new FailedToPutObjectException(ErrorMessage.FAILED_TO_PUT_OBJECT.getMessage()));

            FailedToPutObjectException result = assertThrows(FailedToPutObjectException.class, () -> sut.addArchitecturalDrawing(architecturalDrawingRequest));

            assertEquals("ファイルのアップロードに失敗しました", result.getMessage());
        }
    }


}
