package com.tiobe.plugins.intellij.mock;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.picocontainer.PicoContainer;

@SuppressWarnings("deprecation")
public class MockProject implements Project {
    private final String name;
    private final String path;

    public MockProject(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public VirtualFile getBaseDir() {
        return new MockVirtualFile(path);
    }

    @Nullable
    @Override
    public String getBasePath() {
        return null;
    }

    @Nullable
    @Override
    public VirtualFile getProjectFile() {
        return new MockVirtualFile(path + "/.idea/misc.xml");
    }

    @Nullable
    @Override
    public String getProjectFilePath() {
        return null;
    }

    @Nullable
    @Override
    public VirtualFile getWorkspaceFile() {
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public String getLocationHash() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public BaseComponent getComponent(@NotNull String name) {
        return null;
    }

    @Override
    public <T> T getComponent(@NotNull Class<T> interfaceClass) {
        return null;
    }

    @Override
    public <T> T getComponent(@NotNull Class<T> interfaceClass, T defaultImplementationIfAbsent) {
        return null;
    }

    @Override
    public boolean hasComponent(@NotNull Class interfaceClass) {
        return false;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public <T> T[] getComponents(@NotNull Class<T> baseClass) {
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public PicoContainer getPicoContainer() {
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public MessageBus getMessageBus() {
        return null;
    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public <T> T[] getExtensions(@NotNull ExtensionPointName<T> extensionPointName) {
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public Condition<?> getDisposed() {
        return null;
    }

    @Override
    public void dispose() {

    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }
}
