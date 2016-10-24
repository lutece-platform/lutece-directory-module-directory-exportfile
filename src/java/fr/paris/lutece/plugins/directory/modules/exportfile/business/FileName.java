package fr.paris.lutece.plugins.directory.modules.exportfile.business;

public class FileName
{

    private int _nMappingEntry;

    private String _strAttribute;

    private int _nNumberChar;

    private int _nOrder;

    /**
     * Returns the MappingEntry
     * 
     * @return The MappingEntry
     */
    public int getMappingEntry( )
    {
        return _nMappingEntry;
    }

    /**
     * Sets the MappingEntry
     * 
     * @param nId
     *            The MappingEntry
     */
    public void setMappingEntry( int MappingEntry )
    {
        _nMappingEntry = MappingEntry;
    }

    /**
     * Returns the Attribute
     * 
     * @return The Attribute
     */
    public String getAttribute( )
    {
        return _strAttribute;
    }

    /**
     * Sets the Attribute
     * 
     * @param Attribute
     *            The Attribute
     */
    public void setAttribute( String strAttribute )
    {
        _strAttribute = strAttribute;
    }

    /**
     * Returns the NumberChar
     * 
     * @return The NumberChar
     */
    public int getNumberChar( )
    {
        return _nNumberChar;
    }

    /**
     * Sets the NumberChar
     * 
     * @param NumberChar
     *            The NumberChar
     */
    public void setNumberChar( int nNumberChar )
    {
        _nNumberChar = nNumberChar;
    }

    /**
     * Returns the Order
     * 
     * @return The Order
     */
    public int getOrder( )
    {
        return _nOrder;
    }

    /**
     * Sets the Order
     * 
     * @param Order
     *            The Order
     */
    public void setOrder( int nOrder )
    {
        _nOrder = nOrder;
    }

}
