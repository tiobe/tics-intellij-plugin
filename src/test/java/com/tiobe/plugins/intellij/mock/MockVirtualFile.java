package com.tiobe.plugins.intellij.mock;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class MockVirtualFile extends VirtualFile {
    private final String name;
    private final String path;

    public MockVirtualFile(final String path) {
        final String[] splitPath = path.split("[/\\\\]");
        this.name = splitPath[splitPath.length - 1];
        this.path = path;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public VirtualFileSystem getFileSystem() {
        return null;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public VirtualFile getParent() {
        return null;
    }

    @Override
    public VirtualFile[] getChildren() {
        return new VirtualFile[0];
    }

    @Override
    public OutputStream getOutputStream(final Object requestor, final long newModificationStamp, final long newTimeStamp) throws IOException {
        return null;
    }

    @Override
    public byte[] contentsToByteArray() throws IOException {
        return new byte[0];
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }

    @Override
    public long getLength() {
        return 0;
    }

    @Override
    public void refresh(final boolean asynchronous, final boolean recursive, @Nullable final Runnable postRunnable) {

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
