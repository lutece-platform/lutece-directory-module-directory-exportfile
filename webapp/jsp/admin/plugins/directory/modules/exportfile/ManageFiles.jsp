<jsp:useBean id="manageexportfileFile" scope="session" class="fr.paris.lutece.plugins.directory.modules.exportfile.web.FileJspBean" />
<% String strContent = manageexportfileFile.processController ( request , response ); %>

<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>

