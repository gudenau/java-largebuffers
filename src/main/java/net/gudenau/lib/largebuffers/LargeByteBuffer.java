package net.gudenau.lib.largebuffers;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import net.gudenau.lib.annotation.Unsigned;
import net.gudenau.lib.largebuffers.implementation.DirectLargeByteBuffer;

/**
 * Provides a {@link java.nio.ByteBuffer ByteBuffer} like interface that
 * supports much larger buffers, 16777216TiB instead of the
 * {@link java.nio.ByteBuffer ByteBuffer} limit of GiB.
 * */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public abstract class LargeByteBuffer{
    @Unsigned
    private final long size;
    @Unsigned
    private long offset = 0;
    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
    private boolean nativeOrder = byteOrder == ByteOrder.nativeOrder();
    
    protected LargeByteBuffer(long size){
        this.size = size;
    }
    
    /**
     * Gets the size of the {@link net.gudenau.lib.largebuffers.LargeByteBuffer}
     *
     * @return The size of the {@link net.gudenau.lib.largebuffers.LargeByteBuffer}
     * */
    @Unsigned
    public final long getSize(){
        return size;
    }
    
    /**
     * Checks if the buffer can read an amount of bytes without
     * underflowing.
     *
     * @param bytes The amount of bytes to read
     *
     * @throws java.nio.BufferUnderflowException If the buffer
     *          would underflow with the read operation
     * */
    protected final void checkCapacityRead(int bytes){
        checkCapacityRead(offset, bytes);
    }
    
    /**
     * Checks if the buffer can read an amount of bytes without
     * underflowing.
     *
     * @param offset The offset into the buffer to read
     * @param bytes The amount of bytes to read
     *
     * @throws java.nio.BufferUnderflowException If the buffer
     *          would underflow with the read operation
     * */
    protected final void checkCapacityRead(@Unsigned long offset, int bytes){
        if(Long.compareUnsigned(size, offset + bytes) < 0){
            throw new BufferUnderflowException();
        }
    }
    
    /**
     * Checks if the buffer can write an amount of bytes without
     * overflowing.
     *
     * @param bytes The amount of bytes to write
     *
     * @throws java.nio.BufferOverflowException If the buffer
     *          would overflow with the write operation
     * */
    protected final void checkCapacityWrite(int bytes){
        checkCapacityWrite(offset, bytes);
    }
    
    /**
     * Checks if the buffer can write an amount of bytes without
     * overflowing.
     *
     * @param offset The offset into the buffer to write
     * @param bytes The amount of bytes to write
     *
     * @throws java.nio.BufferOverflowException If the buffer
     *          would overflow with the write operation
     * */
    protected final void checkCapacityWrite(@Unsigned long offset, int bytes){
        if(Long.compareUnsigned(size, offset + bytes) < 0){
            throw new BufferOverflowException();
        }
    }
    
    /**
     * Allocates a {@link LargeByteBuffer LargeByteBuffer} and returns it.
     *
     * @return The allocated {@link LargeByteBuffer LargeByteBuffer}
     *
     * @throws OutOfMemoryError If the requested size could not be
     *          allocated
     * */
    public static LargeByteBuffer allocateDirect(@Unsigned long size){
        return new DirectLargeByteBuffer(size);
    }
    
    /**
     * Sets the {@link java.nio.ByteOrder ByteOrder} of the buffer.
     *
     * @param order The order to use on future operations
     * */
    public final void setByteOrder(ByteOrder order){
        byteOrder = order;
        nativeOrder = order == ByteOrder.nativeOrder();
    }
    /**
     * Gets the current {@link java.nio.ByteOrder ByteOrder} of the buffer.
     *
     * @return The current {@link java.nio.ByteOrder ByteOrder}
     * */
    public final ByteOrder getByteOrder(){
        return byteOrder;
    }
    /**
     * Returns true if the current {@link java.nio.ByteOrder} is the native
     * {@link java.nio.ByteOrder}.
     *
     * @return True if the {@link java.nio.ByteOrder} is native
     * */
    public final boolean isByteOrderNative(){
        return nativeOrder;
    }
    
    /**
     * Sets the current offset of the buffer.
     *
     * @param offset The offset to set
     *
     * @throws java.lang.IllegalArgumentException If the size is less
     *          than the offset to set
     * */
    public final void setOffset(@Unsigned long offset){
        if(Long.compareUnsigned(size, offset) < 0){
            throw new IllegalArgumentException("offset was greater than size");
        }
        this.offset = offset;
    }
    /**
     * Gets the current offset of the buffer.
     *
     * @return The current offset
     * */
    @Unsigned
    public final long getOffset(){
        return offset;
    }
    /**
     * Gets the amount of remaining bytes.
     *
     * @return The remaining byte count
     * */
    @Unsigned
    public final long getRemaining(){
        return size - offset;
    }
    
    /**
     * Gets a boolean at the current offset, then increments the offset.
     *
     * @return The boolean value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final boolean getBoolean(){
        boolean value = getBoolean(offset);
        offset += 1;
        return value;
    }
    /**
     * Gets a byte at the current offset, then increments the offset.
     *
     * @return The byte value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final byte getByte(){
        byte value = getByte(offset);
        offset += Byte.BYTES;
        return value;
    }
    /**
     * Gets a short at the current offset, then increments the offset.
     *
     * @return The short value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final short getShort(){
        short value = getShort(offset);
        offset += Short.BYTES;
        return value;
    }
    /**
     * Gets a char at the current offset, then increments the offset.
     *
     * @return The char value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final char getChar(){
        char value = getChar(offset);
        offset += Character.BYTES;
        return value;
    }
    /**
     * Gets a int at the current offset, then increments the offset.
     *
     * @return The int value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final int getInt(){
        int value = getInt(offset);
        offset += Integer.BYTES;
        return value;
    }
    /**
     * Gets a float at the current offset, then increments the offset.
     *
     * @return The float value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final float getFloat(){
        float value = getFloat(offset);
        offset += Float.BYTES;
        return value;
    }
    /**
     * Gets a long at the current offset, then increments the offset.
     *
     * @return The long value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final long getLong(){
        long value = getLong(offset);
        offset += Long.BYTES;
        return value;
    }
    /**
     * Gets a double at the current offset, then increments the offset.
     *
     * @return The double value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final double getDouble(){
        double value = getDouble(offset);
        offset += Double.BYTES;
        return value;
    }
    
    /**
     * Puts a boolean at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final void putBoolean(boolean value){
        putBoolean(value, offset);
        offset += 1;
    }
    /**
     * Puts a byte at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final void putByte(byte value){
        putByte(value, offset);
        offset += Byte.BYTES;
    }
    /**
     * Puts a short at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final void putShort(short value){
        putShort(value, offset);
        offset += Short.BYTES;
    }
    /**
     * Puts a char at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final void putChar(char value){
        putChar(value, offset);
        offset += Character.BYTES;
    }
    /**
     * Puts a int at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final void putInt(int value){
        putInt(value, offset);
        offset += Integer.BYTES;
    }
    /**
     * Puts a float at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final void putFloat(float value){
        putFloat(value, offset);
        offset += Float.BYTES;
    }
    /**
     * Puts a long at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final void putLong(long value){
        putLong(value, offset);
        offset += Long.BYTES;
    }
    /**
     * Puts a double at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final void putDouble(double value){
        putDouble(value, offset);
        offset += Double.BYTES;
    }
    
    /**
     * Gets a boolean at the supplied offset.
     *
     * @param offset The offset to read from
     *
     * @return The boolean value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public boolean getBoolean(@Unsigned long offset){
        return getByte(offset) != 0;
    }
    /**
     * Gets a byte at the supplied offset.
     *
     * @param offset The offset to read from
     *
     * @return The byte value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public abstract byte getByte(@Unsigned long offset);
    /**
     * Gets a short at the supplied offset.
     *
     * @param offset The offset to read from
     *
     * @return The short value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public abstract short getShort(@Unsigned long offset);
    /**
     * Gets a char at the supplied offset.
     *
     * @param offset The offset to read from
     *
     * @return The char value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public char getChar(@Unsigned long offset){
        return (char)getShort(offset);
    }
    /**
     * Gets a int at the supplied offset.
     *
     * @param offset The offset to read from
     *
     * @return The int value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public abstract int getInt(@Unsigned long offset);
    /**
     * Gets a float at the supplied offset.
     *
     * @param offset The offset to read from
     *
     * @return The float value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public float getFloat(@Unsigned long offset){
        return Float.intBitsToFloat(getInt(offset));
    }
    /**
     * Gets a long at the supplied offset.
     *
     * @param offset The offset to read from
     *
     * @return The long value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public abstract long getLong(@Unsigned long offset);
    /**
     * Gets a double at the supplied offset.
     *
     * @param offset The offset to read from
     *
     * @return The double value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public double getDouble(@Unsigned long offset){
        return Double.longBitsToDouble(getLong(offset));
    }
    
    /**
     * Puts a boolean at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public void putBoolean(boolean value, @Unsigned long offset){
        putByte((byte)(value ? 0 : 1), offset);
    }
    /**
     * Puts a byte at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public abstract void putByte(byte value, @Unsigned long offset);
    /**
     * Puts a short at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public abstract void putShort(short value, @Unsigned long offset);
    /**
     * Puts a char at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public void putChar(char value, @Unsigned long offset){
        putShort((short)value, offset);
    }
    /**
     * Puts a int at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public abstract void putInt(int value, @Unsigned long offset);
    /**
     * Puts a float at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public void putFloat(float value, @Unsigned long offset){
        putInt(Float.floatToRawIntBits(value), offset);
    }
    /**
     * Puts a long at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public abstract void putLong(long value, @Unsigned long offset);
    /**
     * Puts a double at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public void putDouble(double value, @Unsigned long offset){
        putLong(Double.doubleToRawLongBits(value), offset);
    }
    
    /**
     * Gets an array of boolean values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getBooleans(boolean[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        getBooleans(values, 0, values.length);
    }
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getBytes(byte[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        getBytes(values, 0, values.length);
    }
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getShorts(short[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        getShorts(values, 0, values.length);
    }
    /**
     * Gets an array of char values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getChars(char[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        getChars(values, 0, values.length);
    }
    /**
     * Gets an array of int values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getInts(int[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        getInts(values, 0, values.length);
    }
    /**
     * Gets an array of float values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getFloats(float[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        getFloats(values, 0, values.length);
    }
    /**
     * Gets an array of long values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getLongs(long[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        getLongs(values, 0, values.length);
    }
    /**
     * Gets an array of double values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getDoubles(double[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        getDoubles(values, 0, values.length);
    }
    
    /**
     * Puts an array of boolean values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putBooleans(boolean[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        putBooleans(values, 0, values.length);
    }
    /**
     * Puts an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putBytes(byte[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        putBytes(values, 0, values.length);
    }
    /**
     * Puts an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putShorts(short[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        putShorts(values, 0, values.length);
    }
    /**
     * Puts an array of char values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putChars(char[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        putChars(values, 0, values.length);
    }
    /**
     * Puts an array of int values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putInts(int[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        putInts(values, 0, values.length);
    }
    /**
     * Puts an array of float values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putFloats(float[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        putFloats(values, 0, values.length);
    }
    /**
     * Puts an array of long values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putLongs(long[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        putLongs(values, 0, values.length);
    }
    /**
     * Puts an array of double values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putDoubles(double[] values){
        if(values == null){
            throw new NullPointerException("values");
        }
        putDoubles(values, 0, values.length);
    }
    
    /**
     * Gets an array of boolean values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getBooleans(boolean[] values, int offset, int length){
        getBooleans(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getBytes(byte[] values, int offset, int length){
        getBytes(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getShorts(short[] values, int offset, int length){
        getShorts(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Gets an array of char values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getChars(char[] values, int offset, int length){
        getChars(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Gets an array of int values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getInts(int[] values, int offset, int length){
        getInts(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Gets an array of float values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getFloats(float[] values, int offset, int length){
        getFloats(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Gets an array of long values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getLongs(long[] values, int offset, int length){
        getLongs(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Gets an array of double values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getDoubles(double[] values, int offset, int length){
        getDoubles(values, offset, length, this.offset);
        this.offset += length;
    }
    
    /**
     * Puts an array of boolean values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putBooleans(boolean[] values, int offset, int length){
        putBooleans(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Puts an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putBytes(byte[] values, int offset, int length){
        putBytes(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Puts an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putShorts(short[] values, int offset, int length){
        putShorts(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Puts an array of char values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putChars(char[] values, int offset, int length){
        putChars(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Puts an array of int values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putInts(int[] values, int offset, int length){
        putInts(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Puts an array of float values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putFloats(float[] values, int offset, int length){
        putFloats(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Puts an array of long values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putLongs(long[] values, int offset, int length){
        putLongs(values, offset, length, this.offset);
        this.offset += length;
    }
    /**
     * Puts an array of double values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putDoubles(double[] values, int offset, int length){
        putDoubles(values, offset, length, this.offset);
        this.offset += length;
    }
    
    /**
     * Gets an array of boolean values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getBooleans(boolean[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        getBooleans(values, 0, values.length, pointer);
    }
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getBytes(byte[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        getBytes(values, 0, values.length, pointer);
    }
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getShorts(short[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        getShorts(values, 0, values.length, pointer);
    }
    /**
     * Gets an array of char values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getChars(char[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        getChars(values, 0, values.length, pointer);
    }
    /**
     * Gets an array of int values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getInts(int[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        getInts(values, 0, values.length, pointer);
    }
    /**
     * Gets an array of float values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getFloats(float[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        getFloats(values, 0, values.length, pointer);
    }
    /**
     * Gets an array of long values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getLongs(long[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        getLongs(values, 0, values.length, pointer);
    }
    /**
     * Gets an array of double values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void getDoubles(double[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        getDoubles(values, 0, values.length, pointer);
    }
    
    /**
     * Puts an array of boolean values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putBooleans(boolean[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        putBooleans(values, 0, values.length, pointer);
    }
    /**
     * Puts an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putBytes(byte[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        putBytes(values, 0, values.length, pointer);
    }
    /**
     * Puts an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putShorts(short[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        putShorts(values, 0, values.length, pointer);
    }
    /**
     * Puts an array of char values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putChars(char[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        putChars(values, 0, values.length, pointer);
    }
    /**
     * Puts an array of int values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putInts(int[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        putInts(values, 0, values.length, pointer);
    }
    /**
     * Puts an array of float values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putFloats(float[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        putFloats(values, 0, values.length, pointer);
    }
    /**
     * Puts an array of long values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putLongs(long[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        putLongs(values, 0, values.length, pointer);
    }
    /**
     * Puts an array of double values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final void putDoubles(double[] values, @Unsigned long pointer){
        if(values == null){
            throw new NullPointerException("values");
        }
        putDoubles(values, 0, values.length, pointer);
    }
    
    /**
     * Gets an array of boolean values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void getBooleans(boolean[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityRead(pointer, length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            values[offset + i] = getBoolean(pointer);
            pointer += 1;
        }
    }
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void getBytes(byte[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityRead(pointer, Byte.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            values[offset + i] = getByte(pointer);
            pointer += Byte.BYTES;
        }
    }
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void getShorts(short[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityRead(pointer, Short.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            values[offset + i] = getShort(pointer);
            pointer += Short.BYTES;
        }
    }
    /**
     * Gets an array of char values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void getChars(char[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityRead(pointer, Character.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            values[offset + i] = getChar(pointer);
            pointer += Character.BYTES;
        }
    }
    /**
     * Gets an array of int values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void getInts(int[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityRead(pointer, Integer.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            values[offset + i] = getInt(pointer);
            pointer += Integer.BYTES;
        }
    }
    /**
     * Gets an array of float values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void getFloats(float[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityRead(pointer, Float.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            values[offset + i] = getFloat(pointer);
            pointer += Float.BYTES;
        }
    }
    /**
     * Gets an array of long values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void getLongs(long[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityRead(pointer, Long.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            values[offset + i] = getLong(pointer);
            pointer += Long.BYTES;
        }
    }
    /**
     * Gets an array of double values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void getDoubles(double[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityRead(pointer, Double.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            values[offset + i] = getDouble(pointer);
            pointer += Double.BYTES;
        }
    }
    
    /**
     * Puts an array of boolean values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void putBooleans(boolean[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityWrite(pointer, length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            putBoolean(values[offset + i], pointer);
            pointer += 1;
        }
    }
    /**
     * Puts an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void putBytes(byte[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityWrite(pointer, Byte.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            putByte(values[offset + i], pointer);
            pointer += Byte.BYTES;
        }
    }
    /**
     * Puts an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void putShorts(short[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityWrite(pointer, Short.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            putShort(values[offset + i], pointer);
            pointer += Short.BYTES;
        }
    }
    /**
     * Puts an array of char values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void putChars(char[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityWrite(pointer, Character.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            putChar(values[offset + i], pointer);
            pointer += Character.BYTES;
        }
    }
    /**
     * Puts an array of int values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void putInts(int[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityWrite(pointer, Integer.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            putInt(values[offset + i], pointer);
            pointer += Integer.BYTES;
        }
    }
    /**
     * Puts an array of float values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void putFloats(float[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityWrite(pointer, Float.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            putFloat(values[offset + i], pointer);
            pointer += Float.BYTES;
        }
    }
    /**
     * Puts an array of long values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void putLongs(long[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityWrite(pointer, Long.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            putLong(values[offset + i], pointer);
            pointer += Long.BYTES;
        }
    }
    /**
     * Puts an array of double values from the buffer, incrementing
     * the offset by the count.
     *
     * @param values The values to put
     * @param offset The offset into the array
     * @param length The length of the data
     * @param pointer The offset into the buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public void putDoubles(double[] values, int offset, int length, @Unsigned long pointer){
        checkCapacityWrite(pointer, Double.BYTES * length);
        if(values == null){
            throw new NullPointerException("values");
        }
        if(values.length < offset + length){
            throw new IllegalArgumentException("offset and length overflow values");
        }
        for(int i = 0; i < length; i++){
            putDouble(values[offset + i], pointer);
            pointer += Double.BYTES;
        }
    }
}
