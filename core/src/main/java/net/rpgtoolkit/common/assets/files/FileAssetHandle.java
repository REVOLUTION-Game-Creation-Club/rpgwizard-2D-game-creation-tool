/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets.files;


import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public class FileAssetHandle extends AssetHandle {

  public FileAssetHandle(AssetDescriptor descriptor) {
    super(descriptor);
  }

  public File getFile() {
    final Path path = Paths.get(descriptor.getURI());
    final File file = path.toFile();
    return file;
  }

  @Override
  public ReadableByteChannel read() throws IOException {
    final File file = getFile();
    return new FileInputStream(file).getChannel();
  }

  @Override
  public WritableByteChannel write() throws IOException {
    final File file = getFile();
    return new FileOutputStream(file).getChannel();
  }

  @Override
  public long size() throws IOException {
    final File file = getFile();
    if (file.exists()) {
      return file.length();
    }
    return -1;
  }

}
