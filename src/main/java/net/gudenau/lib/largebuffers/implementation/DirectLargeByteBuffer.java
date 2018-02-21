package net.gudenau.lib.largebuffers.implementation;

import net.gudenau.lib.cleanup.Cleaner;
import net.gudenau.lib.largebuffers.ByteBufferGlueLogic;

/**
 * The default implementation of the {@link net.gudenau.lib.largebuffers.LargeByteBuffer}
 * abstract class.
 * */
public class DirectLargeByteBuffer extends ByteBufferGlueLogic<DirectLargeByteBuffer>{
    private final long pointer;
    
    public DirectLargeByteBuffer(long size){
        super(size);
        long pointer = NativeMethods.allocateMemory(size);
        NativeMethods.memset(pointer, (byte)0x00, size);
        
        this.pointer = pointer;
        
        Cleaner.addCleaner(this, ()->NativeMethods.freeMemory(pointer));
    }
    
    @Override
    public byte getByte(long offset){
        checkCapacityRead(offset, Byte.BYTES);
        return NativeMethods.getByte(pointer + offset);
    }
    
    @Override
    public short getShort(long offset){
        checkCapacityRead(offset, Short.BYTES);
        short value = NativeMethods.getShort(pointer + offset);
        return isByteOrderNative() ? value : Short.reverseBytes(value);
    }
    
    @Override
    public int getInt(long offset){
        checkCapacityRead(offset, Integer.BYTES);
        int value = NativeMethods.getInt(pointer + offset);
        return isByteOrderNative() ? value : Integer.reverseBytes(value);
    }
    
    @Override
    public long getLong(long offset){
        checkCapacityRead(offset, Long.BYTES);
        long value = NativeMethods.getLong(pointer + offset);
        return isByteOrderNative() ? value : Long.reverseBytes(value);
    }
    
    @Override
    public void putByte(byte value, long offset){
        checkCapacityWrite(offset, Byte.BYTES);
        NativeMethods.putByte(pointer + offset, value);
    }
    
    @Override
    public void putShort(short value, long offset){
        checkCapacityWrite(offset, Short.BYTES);
        NativeMethods.putShort(
            pointer + offset,
            isByteOrderNative() ? value : Short.reverseBytes(value)
        );
    }
    
    @Override
    public void putInt(int value, long offset){
        checkCapacityWrite(offset, Integer.BYTES);
        NativeMethods.putInt(
            pointer + offset,
            isByteOrderNative() ? value : Integer.reverseBytes(value)
        );
    }
    
    @Override
    public void putLong(long value, long offset){
        checkCapacityWrite(offset, Long.BYTES);
        NativeMethods.putLong(
            pointer + offset,
            isByteOrderNative() ? value : Long.reverseBytes(value)
        );
    }
}
