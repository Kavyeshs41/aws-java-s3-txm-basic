package com.example;

import java.io.File;
import java.io.Writer;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.junit.jupiter.api.Test;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
          
public class s3Upload {

    @Test
    public void uploadIssue() {
        try {

            String fileName = "test.txt";
            AmazonS3ClientBuilder amazonS3ClientBuilder = AmazonS3Client.builder()
            .withEndpointConfiguration(new EndpointConfiguration("http://localhost:4566", "us-east-1"));
            amazonS3ClientBuilder.enablePathStyleAccess();
       
            AmazonS3 s3 = amazonS3ClientBuilder.build();
            s3.listBuckets();
            s3.createBucket("test11");
            
            TransferManager txm = TransferManagerBuilder.standard()
                .withS3Client(s3)
                .build();
            
            txm.upload(new PutObjectRequest("test11", "test_account/user/111/content/file.txt", createSampleFile()));
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
}