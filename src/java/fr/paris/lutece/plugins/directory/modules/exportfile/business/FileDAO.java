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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class provides Data Access methods for File objects
 */

public final class FileDAO implements IFileDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_file ) FROM exportfile_file";
    private static final String SQL_QUERY_SELECT = "SELECT id_file, idDirectory, idFile, isCreated FROM exportfile_file WHERE id_file = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO exportfile_file ( id_file, idDirectory, idFile, isCreated ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM exportfile_file WHERE id_file = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE exportfile_file SET id_file = ?, idDirectory = ?, idFile = ?, isCreated = ? WHERE id_file = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_file, idDirectory, idFile, isCreated FROM exportfile_file";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_file FROM exportfile_file";
    private static final String SQL_QUERY_SELECT_BY_ID_FILE = "SELECT id_file, idDirectory, idFile, isCreated FROM exportfile_file WHERE idFile = ? AND idDirectory = ?";

    /**
     * Generates a new primary key
     * 
     * @param plugin
     *            The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );

        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );

        return nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( File file, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        file.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, file.getId( ) );
        daoUtil.setInt( 2, file.getIdDirectory( ) );
        daoUtil.setInt( 3, file.getIdFile( ) );
        daoUtil.setBoolean( 4, file.getIsCreated( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public File load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );

        File file = null;

        if ( daoUtil.next( ) )
        {
            file = new File( );
            file.setId( daoUtil.getInt( 1 ) );
            file.setIdDirectory( daoUtil.getInt( 2 ) );
            file.setIdFile( daoUtil.getInt( 3 ) );
            file.setIsCreated( daoUtil.getBoolean( 4 ) );
        }

        daoUtil.free( );
        return file;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public File load( int nIdFile, int nIdDirectory, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_FILE, plugin );
        daoUtil.setInt( 1, nIdFile );
        daoUtil.setInt( 2, nIdDirectory );
        daoUtil.executeQuery( );

        File file = null;

        if ( daoUtil.next( ) )
        {
            file = new File( );
            file.setId( daoUtil.getInt( 1 ) );
            file.setIdDirectory( daoUtil.getInt( 2 ) );
            file.setIdFile( daoUtil.getInt( 3 ) );
            file.setIsCreated( daoUtil.getBoolean( 4 ) );
        }

        daoUtil.free( );
        return file;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( File file, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, file.getId( ) );
        daoUtil.setInt( 2, file.getIdDirectory( ) );
        daoUtil.setInt( 3, file.getIdFile( ) );
        daoUtil.setBoolean( 4, file.getIsCreated( ) );
        daoUtil.setInt( 5, file.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<File> selectFilesList( Plugin plugin )
    {
        Collection<File> fileList = new ArrayList<File>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            File file = new File( );

            file.setId( daoUtil.getInt( 1 ) );
            file.setIdDirectory( daoUtil.getInt( 2 ) );
            file.setIdFile( daoUtil.getInt( 3 ) );
            file.setIsCreated( daoUtil.getBoolean( 4 ) );

            fileList.add( file );
        }

        daoUtil.free( );
        return fileList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<Integer> selectIdFilesList( Plugin plugin )
    {
        Collection<Integer> fileList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            fileList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return fileList;
    }
}
