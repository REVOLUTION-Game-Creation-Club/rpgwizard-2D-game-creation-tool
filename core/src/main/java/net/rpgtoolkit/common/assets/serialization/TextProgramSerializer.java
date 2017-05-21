/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rpgtoolkit.common.assets.serialization;

import java.io.File;
import java.io.IOException;
import net.rpgtoolkit.common.assets.AbstractAssetSerializer;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetException;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.Program;
import net.rpgtoolkit.common.io.Paths;
import net.rpgtoolkit.common.utilities.CoreProperties;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Joshua Michael Daly
 */
public class TextProgramSerializer extends AbstractAssetSerializer {

	@Override
	public boolean serializable(AssetDescriptor descriptor) {
		final String ext = Paths.extension(descriptor.getURI());
		return (ext.endsWith(CoreProperties
				.getFullExtension("toolkit.program.extension.default")));
	}

	@Override
	public boolean deserializable(AssetDescriptor descriptor) {
		return serializable(descriptor);
	}

	@Override
	public void serialize(AssetHandle handle) throws IOException,
			AssetException {
		final Program program = (Program) handle.getAsset();
		FileUtils.writeStringToFile(program.getFile(), program
				.getProgramBuffer().toString());
	}

	@Override
	public void deserialize(AssetHandle handle) throws IOException,
			AssetException {
		final Program program = new Program(handle.getDescriptor());
		File path = new File(handle.getDescriptor().getURI().getPath());
		program.update(FileUtils.readFileToString(path, "UTF-8"));

		handle.setAsset(program);
	}

}
