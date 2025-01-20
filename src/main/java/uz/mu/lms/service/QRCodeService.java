package uz.mu.lms.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRCodeService {

    byte[] generateQRCode(String content) throws IOException, WriterException;

    /**
    increase width and height if larger data is inserted
     */
    byte[] generateQRCode(String content, int width, int height) throws IOException, WriterException;
}
