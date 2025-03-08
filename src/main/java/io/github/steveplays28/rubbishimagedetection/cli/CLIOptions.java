// This file is part of Rubbish Image Detection, licensed under the GNU GPLv3 license.
// Copyright (C) 2024 Karim Keroum, Jaymi Krol and Darion Spaargaren
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.

package io.github.steveplays28.rubbishimagedetection.cli;

import java.nio.file.Path;

public record CLIOptions(Path inputDirectory, Path outputDirectory) {}
