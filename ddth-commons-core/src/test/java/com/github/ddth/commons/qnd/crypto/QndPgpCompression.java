package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.PGPUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class QndPgpCompression {

    public static void main(String[] args) throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder dataBuilder = new StringBuilder();
        for (int i = 0; i < 1024 * 1024; i++) {
            dataBuilder.append(rand.nextInt(1024));
        }
        byte[] orgData = dataBuilder.toString().getBytes(StandardCharsets.UTF_8);
        PGPUtils.CompressionAlgorithm[] algorithms = { PGPUtils.CompressionAlgorithm.ZIP,
                PGPUtils.CompressionAlgorithm.ZLIB, PGPUtils.CompressionAlgorithm.BZIP2,
                PGPUtils.CompressionAlgorithm.NOCOMPRESSED };
        for (PGPUtils.CompressionAlgorithm algorithm : algorithms) {
            long t1 = System.currentTimeMillis();
            byte[] compressedData = PGPUtils.compress(orgData, "name", algorithm);
            long t2 = System.currentTimeMillis();
            byte[] decompressedData = PGPUtils.decompress(compressedData);
            long t3 = System.currentTimeMillis();
            long d1 = t2 - t1;
            long d2 = t3 - t2;
            System.out.printf("Original Data: %d - Compressed Data: %d (%s) (%d ms) - Decompressed Data: %d (%d ms)\n",
                    orgData.length, compressedData.length, algorithm, d1, decompressedData.length, d2);
        }
    }
}
