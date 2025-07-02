package com.gabetechsolutions.spring.common;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidConverter {

    public static byte[] uuidToBytes(UUID uuid) {
        return ByteBuffer.wrap(new byte[16])
              .putLong(uuid.getMostSignificantBits())
              .putLong(uuid.getLeastSignificantBits())
              .array();
    }

    public static UUID bytesToUuid(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long firstLong = byteBuffer.getLong();
        long secondLong = byteBuffer.getLong();
        return new UUID(firstLong, secondLong);
    }
}
