/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
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
package fr.paris.lutece.plugins.directory.modules.exportfile.service;

import fr.paris.lutece.plugins.directory.business.PhysicalFile;
import fr.paris.lutece.plugins.directory.business.PhysicalFileHome;
import fr.paris.lutece.plugins.directory.business.Record;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.File;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.FileHome;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.MappingEntry;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.MappingEntryHome;
import fr.paris.lutece.plugins.directory.service.DirectoryPlugin;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.compress.utils.IOUtils;

/**
 * ExportPVNETDaemon
 */
public class ExportFileDaemon extends Daemon
{
    public static final String PATH_DIRECTORY_IMAGE = AppPropertiesService.getProperty( "module.directory.exportfile.path_directory_save_image", "image" );
    private DirectoryService _directorySrvc = DirectoryService.getService( );
    public final Plugin _pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );

    /**
     * {@inheritDoc}
     */
    @Override
    public void run( )
    {

        Collection<MappingEntry> listMappingEntry = MappingEntryHome.getMappingEntrysList( );

        for ( MappingEntry ent : listMappingEntry )
        {

            if ( ent.getIsActive( ) )
            {
                for ( Record record : _directorySrvc.getListRecord( ent.getIdDirectory( ), ent.getIdEntry( ) ) )
                {
                    try
                    {

                        doProcessAction( ent.getIdEntry( ), record.getIdRecord( ), ent.getIdDirectory( ) );

                    }
                    catch( Exception e )
                    {

                        AppLogService.error( "Error de creation du fichier", e );
                    }
                }
            }

        }

    }

    /**
     * Create file in directory
     * 
     * @param nIdEntry
     * @param idRecord
     * @param idDirectory
     */
    private void doProcessAction( int nIdEntry, int idRecord, int idDirectory )
    {
        File fileSaved = null;
        PhysicalFile phFile = null;

        fr.paris.lutece.plugins.directory.business.File file = _directorySrvc.getRecordFieldValue( nIdEntry, idRecord, idDirectory );
        if ( file != null )
        {
            fileSaved = FileHome.findFile( file.getIdFile( ), idDirectory );
            phFile = PhysicalFileHome.findByPrimaryKey( file.getPhysicalFile( ).getIdPhysicalFile( ), _pluginDirectory );
        }
        if ( file != null && phFile != null && ( fileSaved == null || !fileSaved.getIsCreated( ) ) )
        {

            byte [ ] bytes = phFile.getValue( );
            ByteArrayInputStream in = new ByteArrayInputStream( bytes );
            FileOutputStream out;
            String fileName = file.getTitle( );
            try
            {

                out = new FileOutputStream( new java.io.File( "/home/rafik/image/" + fileName ) );
                IOUtils.copy( in, out );
                IOUtils.closeQuietly( in );
                IOUtils.closeQuietly( out );

            }
            catch( FileNotFoundException e )
            {
                File fileExport = new File( );
                fileExport.setIdDirectory( idDirectory );
                fileExport.setIsCreated( false );
                fileExport.setIdFile( file.getIdFile( ) );
                AppLogService.error( "Error de creation du fichier", e );
            }
            catch( IOException e )
            {
                File fileExport = new File( );
                fileExport.setIdDirectory( idDirectory );
                fileExport.setIsCreated( false );
                fileExport.setIdFile( file.getIdFile( ) );
                AppLogService.error( "Error de creation du fichier", e );
            }

            File fileExport = new File( );
            fileExport.setIdDirectory( idDirectory );
            fileExport.setIsCreated( true );
            fileExport.setIdFile( file.getIdFile( ) );

            FileHome.create( fileExport );
        }
    }
}
