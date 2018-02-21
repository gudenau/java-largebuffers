package net.gudenau.lib.largebuffers.implementation;

import net.gudenau.lib.annotation.NonNull;
import net.gudenau.lib.annotation.Pointer;
import net.gudenau.lib.annotation.Unsigned;

public final class NativeMethods{
    static{
        Library.loadLibrary();
    }
    
    private static final int ADDRESS_SIZE = doGetAddressSize();
    public static final long NULL = 0;
    
    /**
     * Gets the size of the native pointer, 4 on 32 bit and 8
     * on 64.
     *
     * @return The size of native pointers
     * */
    @Unsigned
    public static int getAddressSize(){
        return ADDRESS_SIZE;
    }
    
    /**
     * Allocates native memory.
     *
     * @return The native pointer to the allocated memory
     *
     * @throws java.lang.IllegalArgumentException If the size
     *          is too large for the native pointer
     *
     * @throws java.lang.OutOfMemoryError If the memory could
     *          not be allocated
     * */
    @Pointer
    public static long allocateMemory(@Unsigned long size){
        // For some reason 0xFFFFFFFFFFFFFFFFL << 64 equals 0xFFFFFFFFFFFFFFFFL
        //  and not 0. How odd.
        if(ADDRESS_SIZE < 8 && (size & (-1L << (ADDRESS_SIZE * 8))) != 0){
            throw new IllegalArgumentException(
                "size does not fit inside native size"
            );
        }
        
        long pointer = doAllocateMemory(size);
        if(pointer == 0){
            throw new OutOfMemoryError("Failed to allocate memory");
        }
        return pointer;
    }
    
    /**
     * Frees native memory.
     *
     * @throws java.lang.NullPointerException If the pointer was
     *          null.
     * */
    public static void freeMemory(@NonNull @Pointer long pointer){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        doFreeMemory(pointer);
    }
    
    /**
     * Sets an area of memory pointed to by pointer to value.
     *
     * @param pointer The pointer of the memory to set
     * @param value The value to set
     * @param size The size of the memory to set
     *
     * @throws java.lang.NullPointerException If the pointer was
     *          null
     * */
    public static void memset(@NonNull @Pointer long pointer, byte value, @Unsigned long size){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        domemset(pointer, value, size);
    }
    
    /**
     * Copies an area of memory from one location to another.
     *
     * @param source The source location to copy
     * @param dest The destination of the copy
     * @param size The size of the copy
     *
     * @throws java.lang.NullPointerException If either pointer
     *          was null
     * */
    public static void memcpy(@NonNull @Pointer long source, @NonNull @Pointer long dest, @Unsigned long size){
        if(source == NULL){
            throw new NullPointerException("source");
        }
        if(dest == NULL){
            throw new NullPointerException("dest");
        }
        domemcpy(source, dest, size);
    }
    
    /**
     * Gets the boolean at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static boolean getBoolean(@NonNull @Pointer long pointer){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        return doGetBoolean(pointer);
    }
    
    /**
     * Gets the byte at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static byte getByte(@NonNull @Pointer long pointer){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        return doGetByte(pointer);
    }
    
    /**
     * Gets the short at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static short getShort(@NonNull @Pointer long pointer){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        return doGetShort(pointer);
    }
    
    /**
     * Gets the int at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static int getInt(@NonNull @Pointer long pointer){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        return doGetInt(pointer);
    }
    
    /**
     * Gets the long at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static long getLong(@NonNull @Pointer long pointer){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        return doGetLong(pointer);
    }
    
    /**
     * Puts the boolean at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static void putBoolean(@NonNull @Pointer long pointer, boolean value){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        doPutBoolean(pointer, value);
    }
    
    /**
     * Puts the byte at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static void putByte(@NonNull @Pointer long pointer, byte value){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        doPutByte(pointer, value);
    }
    
    /**
     * Puts the short at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static void putShort(@NonNull @Pointer long pointer, short value){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        doPutShort(pointer, value);
    }
    
    /**
     * Puts the int at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static void putInt(@NonNull @Pointer long pointer, int value){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        doPutInt(pointer, value);
    }
    
    /**
     * Puts the long at the supplied pointer.
     *
     * @param pointer The pointer
     *
     * @throws java.lang.NullPointerException If the pointer
     *          was null
     * */
    public static void putLong(@NonNull @Pointer long pointer, long value){
        if(pointer == NULL){
            throw new NullPointerException("pointer");
        }
        doPutLong(pointer, value);
    }
    
    // Misc functions
    
    private native static int doGetAddressSize();
    
    // Memory allocation functions
    
    private native static long doAllocateMemory(long size);
    private native static void doFreeMemory(long pointer);
    
    // Mass memory manipulation functions
    
    private native static void domemset(long pointer, byte value, long size);
    private native static void domemcpy(long source, long dest, long size);
    
    // Memory get functions
    
    private native static boolean doGetBoolean(long pointer);
    private native static byte doGetByte(long pointer);
    private native static short doGetShort(long pointer);
    private native static int doGetInt(long pointer);
    private native static long doGetLong(long pointer);
    
    // Memory put functions
    private native static void doPutBoolean(long pointer, boolean value);
    private native static void doPutByte(long pointer, byte value);
    private native static void doPutShort(long pointer, short value);
    private native static void doPutInt(long pointer, int value);
    private native static void doPutLong(long pointer, long value);
}
