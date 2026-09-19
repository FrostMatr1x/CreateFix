package com.frostmatrix.createfix;

public class AssemblyState {
    private static final ThreadLocal<Boolean> ASSEMBLING = ThreadLocal.withInitial(() -> false);

    public static boolean isAssembling() {
        return ASSEMBLING.get();
    }

    public static void setAssembling(boolean value) {
        ASSEMBLING.set(value);
    }
}