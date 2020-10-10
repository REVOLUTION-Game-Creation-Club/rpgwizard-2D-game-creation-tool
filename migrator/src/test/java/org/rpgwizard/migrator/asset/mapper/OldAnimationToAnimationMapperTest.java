/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.migrator.asset.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.rpgwizard.migrator.asset.version1.animation.OldAnimation;
import org.rpgwizard.migrator.asset.version2.animation.Animation;

/**
 *
 * @author Joshua Michael Daly
 */
public class OldAnimationToAnimationMapperTest {
    
    private final ObjectMapper objectMapper;
    
    public OldAnimationToAnimationMapperTest() {
        objectMapper = new ObjectMapper();
    }

    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of map method, of class OldGameToGameMapper.
     * @throws java.lang.Exception
     */
    @Test
    public void testMapping() throws Exception {
        // Given
        var inputPath = Paths.get("src/test/resources/input/south.animation");
        var inputJson = Files.readString(inputPath);
        var input = objectMapper.readValue(inputJson, OldAnimation.class);
        
        // When
        var mapper = Mappers.getMapper(OldAnimationToAnimationMapper.class);
        var actual = mapper.map(input);
        
        // Then
        var outputPath = Paths.get("src/test/resources/output/south.animation");
        var outputJson = Files.readString(outputPath);
        var expected = objectMapper.readValue(outputJson, Animation.class);
        
        Assertions.assertEquals(expected, actual);
    }

}
