package io.github.steveplays28.rubbishimagedetection.cv;

import org.jetbrains.annotations.NotNull;
import org.opencv.core.Mat;

import java.io.File;

public record Image(@NotNull File file, @NotNull Mat matrix, @NotNull String name, @NotNull String fileExtension) {}
