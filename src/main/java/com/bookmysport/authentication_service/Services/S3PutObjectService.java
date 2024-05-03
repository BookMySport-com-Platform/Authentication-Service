package com.bookmysport.authentication_service.Services;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookmysport.authentication_service.Models.AvatarModel;
import com.bookmysport.authentication_service.Models.ResponseMessage;
import com.bookmysport.authentication_service.Repository.AvatarUploadRepository;
import com.bookmysport.authentication_service.StaticInfo.S3Data;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class S3PutObjectService {

    @Autowired
    private ResponseMessage responseMessage;

    @Autowired
    private AvatarUploadRepository avatarUploadRepository;

    @SuppressWarnings("null")
    @Scheduled(fixedRate = 86400)
    public void deleteExpiredRecords() {
        LocalDate expiryTime = LocalDate.now();

        List<AvatarModel> imagesFromDB = avatarUploadRepository.findAll();
        String folderName = System.getenv("USER_AVATAR_FOLDER_NAME");

        for (int i = 0; i < imagesFromDB.size(); i++) {

            if (imagesFromDB.get(i).getDateOfGenration().isBefore(expiryTime)) {
                String key = folderName + '/' + imagesFromDB.get(i).getUserId() + '/'
                        + imagesFromDB.get(i).getAvatarId();
                ResponseEntity<ResponseMessage> newPresignedURL = preSignedURLService(key);
                imagesFromDB.get(i).setAvatarUrl(newPresignedURL.getBody().getMessage());
                imagesFromDB.get(i).setDateOfGenration(LocalDate.now());
                avatarUploadRepository.save(imagesFromDB.get(i));
            }

        }
    }

    public boolean checkObjectInBucket(String bucketName, String key) {
        S3Client s3Client = S3Data.s3Client;

        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.headObject(headObjectRequest);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<ResponseMessage> preSignedURLService(String key) {
        S3Presigner s3Client = S3Presigner.builder().region(S3Data.region).build();

        try {
            if (checkObjectInBucket(S3Data.bucketName, key)) {
                GetObjectRequest request = GetObjectRequest.builder()
                        .bucket(S3Data.bucketName)
                        .key(key)
                        .build();

                GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofHours(10))
                        .getObjectRequest(request)
                        .build();

                PresignedGetObjectRequest presignedGetObjectRequest = s3Client.presignGetObject(presignRequest);

                responseMessage.setSuccess(true);
                responseMessage.setMessage(presignedGetObjectRequest.url().toString());

                return ResponseEntity.ok().body(responseMessage);
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Object '" + key + "' does not exists!");
                return ResponseEntity.ok().body(responseMessage);
            }
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error " + e.getMessage());
            return ResponseEntity.ok().body(responseMessage);
        }
    }

    @SuppressWarnings("null")
    public ResponseEntity<ResponseMessage> putObjectService(String userId, String key, MultipartFile avatar) {
        S3Client client = S3Data.s3Client;
        String avatarFolderName = System.getenv("USER_AVATAR_FOLDER_NAME");

        try {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(S3Data.bucketName)
                    .key(avatarFolderName + '/' + userId + '/' + key)
                    .contentType(avatar.getContentType())
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(avatar.getInputStream(), avatar.getSize());

            PutObjectResponse response = client.putObject(putOb, requestBody);

            if (response.eTag().isEmpty()) {
                responseMessage.setSuccess(false);
                responseMessage
                        .setMessage("Object " + avatarFolderName + userId + '/' + key + " insertion falied "
                                + response.eTag());
                responseMessage.setToken(null);
                return ResponseEntity.ok().body(responseMessage);
            } else {
                responseMessage.setSuccess(true);
                responseMessage
                        .setMessage(preSignedURLService(avatarFolderName + '/' + userId + '/' + key).getBody()
                                .getMessage());
                responseMessage.setToken(null);

                return ResponseEntity.ok().body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage(
                    "Object " + avatarFolderName + userId + '/' + key + " insertion falied " + e.getMessage());

            responseMessage.setToken(null);
            return ResponseEntity.ok().body(responseMessage);
        }
    }
}