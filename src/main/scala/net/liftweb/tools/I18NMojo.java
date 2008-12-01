/*
 * Copyright 2008 WorldWide Conferencing, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */

package net.liftweb.tools;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Goal which runs the I18N LocKeyScanner.
 *
 * @goal i18ngen
 * 
 * @phase compile
 */
public class I18NMojo
    extends AbstractMojo
{
    /**
     * Location of the sources to scan.
     * @parameter expression="${basedir}/src"
     * @required
     */
    private File sourceDirectory;

    /**
     * Location for output of properties.
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

    public void execute()
        throws MojoExecutionException
    {
        if ( !sourceDirectory.exists() )
        {
            throw new MojoExecutionException( "No source directory exists for scanning!" );
        }

        File f = outputDirectory;

	if ( !f.exists() )
	{
	    f.mkdir();
	}

        File template = new File( f, "i18n-template.properties" );

        try
        {
	    LocKeyGrabber scanner = new LocKeyGrabber();
	    scanner.scanDir(sourceDirectory, template);
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Error scanning sources", e );
        }
    }
}
