package fr.paris.lutece.plugins.directory.modules.exportfile.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;






import fr.paris.lutece.plugins.directory.modules.exportfile.business.FileName;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.FileNameHome;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.MappingEntry;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.MappingEntryHome;


public class FileNameService {
	
    private static Map<String, List<FileName>> _mapFileName = new ConcurrentHashMap<String, List<FileName>>(  );
    public static FileNameService _singleton;
   
    public static FileNameService getService(){
    	
    	if( _singleton == null ){
    		
    		_singleton= new FileNameService( );
    		return _singleton;
    		
    	}
    	
    	return _singleton;
    }
    public void addFileNameToUploadedList( FileName fileName, HttpServletRequest request )
    {
    	if(fileName.getAttribute( ) != null ){
	        initMap( request.getSession(  ).getId(  ) );
	        List<FileName> uploadedFilesName = getListUploadedFiles( request.getSession(  ) );  
	        fileName.setOrder(uploadedFilesName.size( ) + 1);
	        uploadedFilesName.add( fileName );
    	}   
        
    }
    
   
    private void initMap( String strSessionId )
    {
        // find session-related filesName in the map
        List<FileName> mapFileNameSession = _mapFileName.get( strSessionId );

        // create map if not exists
        if ( mapFileNameSession == null )
        {
            synchronized ( this )
            {
                // Ignore double check locking error : assignation and instanciation of objects are separated.
            	mapFileNameSession = _mapFileName.get( strSessionId );

                if ( mapFileNameSession == null )
                {
                	mapFileNameSession = new Vector<FileName>(  );
                	_mapFileName.put( strSessionId, mapFileNameSession );
                }
            }
        }

       
    }
    
    public List<FileName> getListUploadedFiles( HttpSession session )
    {
       

        initMap( session.getId(  ) );

        // find session-related files in the map
        List<FileName> mapFileNameSession = _mapFileName.get( session.getId(  ) );

        return mapFileNameSession;
    }

    
    public void removeFileName(  HttpSession session, int nIndex)
    {
        List<FileName> uploadedFileName = getListUploadedFiles(  session );

        if ( ( uploadedFileName != null ) && !uploadedFileName.isEmpty(  )  )
        {
           uploadedFileName.remove( nIndex );
           
        }
    }

    public void removeFilesName(  HttpSession session)
    {
        List<FileName> uploadedFileName = getListUploadedFiles(  session );

        if ( ( uploadedFileName != null ) && !uploadedFileName.isEmpty(  )  )
        {
           uploadedFileName.clear( );
           
        }
    }
    
    public void createEntry(MappingEntry mappingEntry, List<FileName> ListfileName){
    	
    	MappingEntry mappingEnt= MappingEntryHome.create(mappingEntry);
    	for(FileName fileName: ListfileName){
    		fileName.setMappingEntry(mappingEnt.getId( ));
    		FileNameHome.create(fileName);
    	}
    	
    }

    public void updateEntry(MappingEntry mappingEntry, List<FileName> ListfileName){
    	
    	MappingEntry mappingEnt= MappingEntryHome.update(mappingEntry);
    	FileNameHome.remove(mappingEntry.getId( ));
    	for(FileName fileName: ListfileName){
    		fileName.setMappingEntry(mappingEnt.getId( ));
    		FileNameHome.create(fileName);
    		
    	}
    	
    }
    
    public void removeEntry(int idEntry){
    	
    	MappingEntryHome.remove(idEntry);
    	FileNameHome.remove(idEntry);
    	
    }


}
