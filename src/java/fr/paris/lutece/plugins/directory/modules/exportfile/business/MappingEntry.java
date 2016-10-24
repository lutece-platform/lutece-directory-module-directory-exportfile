/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
package fr.paris.lutece.plugins.directory.modules.exportfile.business;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * This is the business class for the object MappingEntry
 */
public class MappingEntry
{
    // Variables declarations
    private int _nId;
 
    @Min( value = 1, message = "#i18n{module.directory.exportfile.validation.mappingEntry.idEntry.notEmpty}" )
    private int _nIdEntry;

    @Min( value = 1, message = "#i18n{module.directory.exportfile.validation.mappingEntry.idDirectory.notEmpty}" )
    private int _nIdDirectory;

    @NotEmpty( message = "#i18n{module.directory.exportfile.validation.mappingEntry.path.notEmpty}" )
    @Size( max = 255, message = "#i18n{module.directory.exportfile.validation.mappingEntry.path.size}" )
    private String _strPath;

    private boolean _bIsActive;

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the IdEntry
     * 
     * @return The IdEntry
     */
    public int getIdEntry( )
    {
        return _nIdEntry;
    }

    /**
     * Sets the IdEntry
     * 
     * @param nIdEntry
     *            The IdEntry
     */
    public void setIdEntry( int nIdEntry )
    {
        _nIdEntry = nIdEntry;
    }

    /**
     * Returns the IdDirectory
     * 
     * @return The IdDirectory
     */
    public int getIdDirectory( )
    {
        return _nIdDirectory;
    }

    /**
     * Sets the IdDirectory
     * 
     * @param nIdDirectory
     *            The IdDirectory
     */
    public void setIdDirectory( int nIdDirectory )
    {
        _nIdDirectory = nIdDirectory;
    }

    /**
     * Returns the Path
     * 
     * @return The Path
     */
    public String getPath( )
    {
        return _strPath;
    }

    /**
     * Sets the Path
     * 
     * @param strPath
     *            The Path
     */
    public void setPath( String strPath )
    {
        _strPath = strPath;
    }

    /**
     * Returns the IsActive
     * 
     * @return The IsActive
     */
    public boolean getIsActive( )
    {
        return _bIsActive;
    }

    /**
     * Sets the IsActive
     * 
     * @param bIsActive
     *            The IsActive
     */
    public void setIsActive( boolean bIsActive )
    {
        _bIsActive = bIsActive;
    }
}
