package net.gudenau.lib.largebuffers;

import java.nio.ByteOrder;
import net.gudenau.lib.annotation.Unsigned;

/**
 * A glue class that allows you to use a
 * {@link net.gudenau.lib.largebuffers.LargeByteBuffer} almost as
 * though it was a {@link java.nio.ByteBuffer}.
 *
 * When extending make sure the generic type is that of the new
 * buffer implementation, if it is abstract just pass the type
 * down.
 * */
public abstract class ByteBufferGlueLogic<T extends ByteBufferGlueLogic> extends LargeByteBuffer{
    protected ByteBufferGlueLogic(long size){
        super(size);
    }
    
    /**
     * Sets the {@link java.nio.ByteOrder ByteOrder} of the buffer.
     *
     * @return The current {@link net.gudenau.lib.largebuffers.LargeByteBuffer LargeByteBuffer}
     *
     * @param order The order to use on future operations
     * */
    public final T order(ByteOrder order){
        setByteOrder(order);
        //noinspection unchecked
        return (T)this;
    }
    
    /**
     * Sets the current offset of the buffer.
     *
     * @param offset The offset to set
     *
     * @return The current {@link net.gudenau.lib.largebuffers.LargeByteBuffer LargeByteBuffer}
     *
     * @throws java.lang.IllegalArgumentException If the size is less
     *          than the offset to set
     */
    public final T position(@Unsigned long offset){
        setOffset(offset);
        //noinspection unchecked
        return (T)this;
    }
    
    /**
     * Gets a byte at the current offset, then increments the offset.
     *
     * @return The byte value
     *
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public final byte get(){
        return getByte();
    }
    
    /**
     * Puts a byte at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @return The current {@link net.gudenau.lib.largebuffers.LargeByteBuffer LargeByteBuffer}
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final T put(byte value){
        putByte(value);
        //noinspection unchecked
        return (T)this;
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
    public final byte get(long offset){
        return getByte(offset);
    }
    
    /**
     * Puts a byte at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset to write to
     *
     * @return The current {@link net.gudenau.lib.largebuffers.LargeByteBuffer LargeByteBuffer}
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final T put(byte value, long offset){
        putByte(value, offset);
        //noinspection unchecked
        return (T)this;
    }
    
    /**
     * Puts a byte at the current offset, then increments the offset.
     *
     * @param value The value to set
     *
     * @return The current {@link net.gudenau.lib.largebuffers.LargeByteBuffer LargeByteBuffer}
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final T put(byte[] value){
        putBytes(value);
        //noinspection unchecked
        return (T)this;
    }
    
    /**
     * Puts a byte at the current offset, then increments the offset.
     *
     * @param value The value to set
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @return The current {@link net.gudenau.lib.largebuffers.LargeByteBuffer LargeByteBuffer}
     *
     * @throws java.nio.BufferOverflowException If the buffer ran out of
     *          space
     * */
    public final T put(byte[] value, int offset, int length){
        putBytes(value, offset, length);
        //noinspection unchecked
        return (T)this;
    }
    
    /**
     * Gets the amount of remaining bytes.
     *
     * @return The remaining byte count
     * */
    @Unsigned
    public final long remaining(){
        return getRemaining();
    }
    
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param dst The values to get
     * @param offset The offset into the array
     * @param length The length of the data
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public T get(byte[] dst, int offset, int length) {
        getBytes(dst, offset, length);
        //noinspection unchecked
        return (T)this;
    }
    
    /**
     * Gets an array of byte values from the buffer, incrementing
     * the offset by the count.
     *
     * @param dst The values to get
     *
     * @return The current buffer
     *
     * @throws java.lang.NullPointerException If the supplied values
     *          are null
     * @throws java.nio.BufferUnderflowException If the buffer ran out of
     *          data
     * */
    public T get(byte[] dst) {
        getBytes(dst, 0, dst.length);
        //noinspection unchecked
        return (T)this;
    }
    
    /**
     * Gets the current {@link java.nio.ByteOrder ByteOrder} of the buffer.
     *
     * @return The current {@link java.nio.ByteOrder ByteOrder}
     * */
    public final ByteOrder order() {
        return getByteOrder();
    }
}
