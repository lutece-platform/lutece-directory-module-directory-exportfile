/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *	 and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *	 and the following disclaimer in the documentation and/or other materials
 *	 provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *	 contributors may be used to endorse or promote products derived from
 *	 this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.directory.modules.exportfile.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.Collection;

/**
 * This class provides instances management methods (create, find, ...) for File objects
 */

public final class FileHome
{
    // Static variable pointed at the DAO instance

    private static IFileDAO _dao = SpringContextService.getBean( "exportfile.fileDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "exportfile" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private FileHome( )
    {
    }

    /**
     * Create an instance of the file class
     * 
     * @param file
     *            The instance of the File which contains the informations to store
     * @return The instance of file which has been created with its primary key.
     */
    public static File create( File file )
    {
        _dao.insert( file, _plugin );

        return file;
    }

    /**
     * Update of the file which is specified in parameter
     * 
     * @param file
     *            The instance of the File which contains the data to store
     * @return The instance of the file which has been updated
     */
    public static File update( File file )
    {
        _dao.store( file, _plugin );

        return file;
    }

    /**
     * Remove the file whose identifier is specified in parameter
     * 
     * @param nKey
     *            The file Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a file whose identifier is specified in parameter
     * 
     * @param nKey
     *            The file primary key
     * @return an instance of File
     */
    public static File findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns an instance of a file
     * 
     * @param nIdFile
     * @param nIdDirectory
     * @return an instance of File
     */
    public static File findFile( int nIdFile, int nIdDirectory )
    {
        return _dao.load( nIdFile, nIdDirectory, _plugin );
    }

    /**
     * Load the data of all the file objects and returns them in form of a collection
     * 
     * @return the collection which contains the data of all the file objects
     */
    public static Collection<File> getFilesList( )
    {
        return _dao.selectFilesList( _plugin );
    }

    /**
     * Load the id of all the file objects and returns them in form of a collection
     * 
     * @return the collection which contains the id of all the file objects
     */
    public static Collection<Integer> getIdFilesList( )
    {
        return _dao.selectIdFilesList( _plugin );
    }
}
