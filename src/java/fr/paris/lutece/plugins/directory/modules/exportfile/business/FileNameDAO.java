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
 * This class provides Data Access methods for FileName objects
 */

public final class FileNameDAO implements IFileNameDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT mapping_entry, attribute, number_char, order_name FROM exportfile_fileName WHERE mapping_entry = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO exportfile_fileName ( mapping_entry, attribute, number_char, order_name ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM exportfile_fileName WHERE mapping_entry=?  AND attribute = ?";
    private static final String SQL_QUERY_DELETE_BY_IDMAPPING = "DELETE FROM exportfile_fileName WHERE mapping_entry=? ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( FileName fileName, Plugin plugin )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        daoUtil.setInt( 1, fileName.getMappingEntry( ) );
        daoUtil.setString( 2, fileName.getAttribute( ) );
        daoUtil.setInt( 3, fileName.getNumberChar( ) );
        daoUtil.setInt( 4, fileName.getOrder( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( FileName fileName, Plugin plugin )
    {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( FileName fileName, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, fileName.getMappingEntry( ) );
        daoUtil.setString( 1, fileName.getAttribute( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_IDMAPPING, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public Collection<FileName> selectFilesNameList( int nMappingEntry, Plugin plugin )
    {
        Collection<FileName> fileNameList = new ArrayList<FileName>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nMappingEntry );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            FileName fileName = new FileName( );

            fileName.setMappingEntry( daoUtil.getInt( 1 ) );
            fileName.setAttribute( daoUtil.getString( 2 ) );
            fileName.setNumberChar( daoUtil.getInt( 3 ) );
            fileName.setOrder( daoUtil.getInt( 4 ) );

            fileNameList.add( fileName );
        }

        daoUtil.free( );
        return fileNameList;
    }
}
